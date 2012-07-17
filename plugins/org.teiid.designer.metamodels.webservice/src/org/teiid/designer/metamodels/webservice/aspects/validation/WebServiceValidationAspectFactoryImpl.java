/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.metamodels.webservice.aspects.validation;

import org.eclipse.emf.ecore.EClassifier;
import org.teiid.designer.core.metamodel.aspect.MetamodelAspect;
import org.teiid.designer.core.metamodel.aspect.MetamodelAspectFactory;
import org.teiid.designer.core.metamodel.aspect.MetamodelEntity;
import org.teiid.designer.metamodels.webservice.WebServicePackage;



/** 
 * WebServiceValidationAspectFactoryImpl
 */
public class WebServiceValidationAspectFactoryImpl implements MetamodelAspectFactory {


    /** 
     * @see org.teiid.designer.core.metamodel.aspect.MetamodelAspectFactory#create(org.eclipse.emf.ecore.EClassifier, org.teiid.designer.core.metamodel.aspect.MetamodelEntity)
     * @since 4.2
     */
    @Override
	public MetamodelAspect create(final EClassifier classifier, final MetamodelEntity entity) {
        switch (classifier.getClassifierID()) {
            case WebServicePackage.INTERFACE: return new InterfaceAspect(entity);
            case WebServicePackage.OPERATION: return new OperationAspect(entity);
            case WebServicePackage.INPUT: return new InputAspect(entity);
            case WebServicePackage.OUTPUT: return new OutputAspect(entity);
            case WebServicePackage.SAMPLE_FILE: return new SampleFileAspect(entity);
            case WebServicePackage.OPERATION_UPDATE_COUNT: return null; // enumeration
            default: return null;
        }
    }
}
