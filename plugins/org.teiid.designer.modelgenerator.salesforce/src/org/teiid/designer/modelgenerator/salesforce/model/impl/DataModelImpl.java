/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.modelgenerator.salesforce.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.eclipse.core.runtime.IProgressMonitor;
import org.teiid.designer.modelgenerator.salesforce.connection.SalesforceConnection;
import org.teiid.designer.modelgenerator.salesforce.model.DataModel;
import org.teiid.designer.modelgenerator.salesforce.model.SalesforceObject;

import com.sforce.soap.partner.DescribeGlobalResult;
import com.sforce.soap.partner.DescribeGlobalSObjectResult;
import com.sforce.soap.partner.DescribeSObjectResult;
import com.sforce.soap.partner.Soap;
import com.sforce.soap.partner.UnexpectedErrorFault;

/**
 * @since 8.0
 */
public class DataModelImpl implements DataModel {

    Integer maxBatchSize;
    String encoding;
    private Map<String, SalesforceObject> salesforceObjects = new TreeMap<String, SalesforceObject>();
    private int selectedCount = 0;

    public DataModelImpl() {
    }

    /* (non-Javadoc)
     * @See org.teiid.designer.modelgenerator.salesforce.model.impl.DataModel#load(org.teiid.designer.modelgenerator.salesforce.connection.Connection)
     */
    @Override
    public void load( SalesforceConnection conn,
                      IProgressMonitor monitor ) throws Exception {
        monitor.beginTask(Messages.getString("DataModelImpl.gathering.metadata"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
        Soap binding = conn.getBinding();
        DescribeGlobalResult describeGlobalResult;
        try {
            describeGlobalResult = binding.describeGlobal();
        } catch (UnexpectedErrorFault e) {
            Exception ce = new Exception(e.getCause().getMessage());
            ce.initCause(e.getCause());
            throw ce;
        }

        List<DescribeGlobalSObjectResult> sObjects = describeGlobalResult.getSobjects();
        String[] types = new String[(sObjects.size())];
        for (int i = 0; i < sObjects.size(); i++) {
            types[i] = sObjects.get(i).getName();
        }

        int i = 0;
        while (i < types.length) {
            int arrayLength = i + 100 > types.length ? types.length - i : 100;
            List<String> typesBatch = new ArrayList(arrayLength);
            for (int j = 0; j < arrayLength; j++) {
                typesBatch.add(types[i + j]);
            }

            List<DescribeSObjectResult> describeSObjectResults;
            try {
                describeSObjectResults = binding.describeSObjects(typesBatch);
            } catch (Exception e) {
                Exception ce = new Exception(e.getCause().getMessage());
                ce.initCause(e.getCause());
                throw ce;
            }

            for (int x = 0; x < describeSObjectResults.size(); x++) {
                DescribeSObjectResult describeSObjectResult = describeSObjectResults.get(x);
                SalesforceObjectImpl object = new SalesforceObjectImpl();
                monitor.subTask(Messages.getString("DataModelImpl.gathering.metadata.table") + describeSObjectResult.getLabel()); //$NON-NLS-1$
                object.setObjectMetadata(describeSObjectResult, this);
                addSalesforceObject(typesBatch.get(x), object);
                monitor.worked(1);
            }
            i = i + arrayLength;
        }
    }

    public Integer getMaxBatchSize() {
        return maxBatchSize;
    }

    public void setMaxBatchSize( Integer maxBatchSize ) {
        this.maxBatchSize = maxBatchSize;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding( String encoding ) {
        this.encoding = encoding;
    }

    /* (non-Javadoc)
     * @See org.teiid.designer.modelgenerator.salesforce.model.impl.DataModel#addDataObject(java.lang.String, org.teiid.designer.modelgenerator.salesforce.model.impl.DataModelObject)
     */
    public void addSalesforceObject( String objectName,
                                     SalesforceObject sObject ) throws Exception {
        Object result = salesforceObjects.put(objectName, sObject);
        if (null != result) {
            throw new Exception(Messages.getString("DataModelImpl.duplicate.found") + objectName); //$NON-NLS-1$
        }
    }

    /* (non-Javadoc)
     * @See org.teiid.designer.modelgenerator.salesforce.model.impl.DataModel#getDataObjects()
     */
    @Override
    public Object[] getSalesforceObjects() {
        return salesforceObjects.values().toArray();
    }

    @Override
    public SalesforceObject getSalesforceObject( String name ) {
        return salesforceObjects.get(name);
    }

    @Override
    public boolean hasSelectedObjects() {
        return selectedCount > 0;
    }

    @Override
    public void incrementSelectionCount() {
        ++selectedCount;
    }

    @Override
    public void decrementSelectionCount() {
        --selectedCount;
    }

}
