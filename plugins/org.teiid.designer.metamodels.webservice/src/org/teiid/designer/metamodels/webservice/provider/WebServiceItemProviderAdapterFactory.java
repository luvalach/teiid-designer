/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.metamodels.webservice.provider;

import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.teiid.designer.metamodels.webservice.util.WebServiceAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 *
 * @since 8.0
 */
public class WebServiceItemProviderAdapterFactory extends WebServiceAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public static final String copyright = "See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing."; //$NON-NLS-1$

    /**
     * This keeps track of the root adapter factory that delegates to this adapter factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected ComposedAdapterFactory parentAdapterFactory;

    /**
     * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected IChangeNotifier changeNotifier = new ChangeNotifier();

    /**
     * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected Collection supportedTypes = new ArrayList();

    /**
     * This constructs an instance.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	public WebServiceItemProviderAdapterFactory() {
        supportedTypes.add(IStructuredItemContentProvider.class);
        supportedTypes.add(ITreeItemContentProvider.class);
        supportedTypes.add(IItemPropertySource.class);
        supportedTypes.add(IEditingDomainItemProvider.class);
        supportedTypes.add(IItemLabelProvider.class);
    }

    /**
     * This keeps track of the one adapter used for all {@link org.teiid.designer.metamodels.webservice.Operation} instances.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected OperationItemProvider operationItemProvider;

    /**
     * This creates an adapter for a {@link org.teiid.designer.metamodels.webservice.Operation}.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public Adapter createOperationAdapter() {
        if (operationItemProvider == null) {
            operationItemProvider = new OperationItemProvider(this);
        }

        return operationItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.teiid.designer.metamodels.webservice.Input} instances.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected InputItemProvider inputItemProvider;

    /**
     * This creates an adapter for a {@link org.teiid.designer.metamodels.webservice.Input}.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public Adapter createInputAdapter() {
        if (inputItemProvider == null) {
            inputItemProvider = new InputItemProvider(this);
        }

        return inputItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.teiid.designer.metamodels.webservice.Output} instances.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected OutputItemProvider outputItemProvider;

    /**
     * This creates an adapter for a {@link org.teiid.designer.metamodels.webservice.Output}.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public Adapter createOutputAdapter() {
        if (outputItemProvider == null) {
            outputItemProvider = new OutputItemProvider(this);
        }

        return outputItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.teiid.designer.metamodels.webservice.Interface} instances.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected InterfaceItemProvider interfaceItemProvider;

    /**
     * This creates an adapter for a {@link org.teiid.designer.metamodels.webservice.Interface}.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public Adapter createInterfaceAdapter() {
        if (interfaceItemProvider == null) {
            interfaceItemProvider = new InterfaceItemProvider(this);
        }

        return interfaceItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.teiid.designer.metamodels.webservice.SampleMessages} instances.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected SampleMessagesItemProvider sampleMessagesItemProvider;

    /**
     * This creates an adapter for a {@link org.teiid.designer.metamodels.webservice.SampleMessages}.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public Adapter createSampleMessagesAdapter() {
        if (sampleMessagesItemProvider == null) {
            sampleMessagesItemProvider = new SampleMessagesItemProvider(this);
        }

        return sampleMessagesItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.teiid.designer.metamodels.webservice.SampleFile} instances.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected SampleFileItemProvider sampleFileItemProvider;

    /**
     * This creates an adapter for a {@link org.teiid.designer.metamodels.webservice.SampleFile}.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public Adapter createSampleFileAdapter() {
        if (sampleFileItemProvider == null) {
            sampleFileItemProvider = new SampleFileItemProvider(this);
        }

        return sampleFileItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.teiid.designer.metamodels.webservice.SampleFromXsd} instances.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	protected SampleFromXsdItemProvider sampleFromXsdItemProvider;

    /**
     * This creates an adapter for a {@link org.teiid.designer.metamodels.webservice.SampleFromXsd}.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public Adapter createSampleFromXsdAdapter() {
        if (sampleFromXsdItemProvider == null) {
            sampleFromXsdItemProvider = new SampleFromXsdItemProvider(this);
        }

        return sampleFromXsdItemProvider;
    }

    /**
     * This returns the root adapter factory that contains this factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public ComposeableAdapterFactory getRootAdapterFactory() {
        return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
    }

    /**
     * This sets the composed adapter factory that contains this factory.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
        this.parentAdapterFactory = parentAdapterFactory;
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public boolean isFactoryForType(Object type) {
        return supportedTypes.contains(type) || super.isFactoryForType(type);
    }

    /**
     * This implementation substitutes the factory itself as the key for the adapter.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public Adapter adapt(Notifier notifier, Object type) {
        return super.adapt(notifier, this);
    }

    /**
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
    public Object adapt(Object object, Object type) {
        if (isFactoryForType(type)) {
            Object adapter = super.adapt(object, type);
            if (!(type instanceof Class) || (((Class)type).isInstance(adapter))) {
                return adapter;
            }
        }

        return null;
    }

    /**
     * This adds a listener.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public void addListener(INotifyChangedListener notifyChangedListener) {
        changeNotifier.addListener(notifyChangedListener);
    }

    /**
     * This removes a listener.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public void removeListener(INotifyChangedListener notifyChangedListener) {
        changeNotifier.removeListener(notifyChangedListener);
    }

    /**
     * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
     * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
     * @generated
     */
	@Override
	public void fireNotifyChanged(Notification notification) {
        changeNotifier.fireNotifyChanged(notification);

        if (parentAdapterFactory != null) {
            parentAdapterFactory.fireNotifyChanged(notification);
        }
    }

    /**
     * This disposes all of the item providers created by this factory. 
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
	public void dispose() {
        if (operationItemProvider != null) operationItemProvider.dispose();
        if (inputItemProvider != null) inputItemProvider.dispose();
        if (outputItemProvider != null) outputItemProvider.dispose();
        if (interfaceItemProvider != null) interfaceItemProvider.dispose();
        if (sampleMessagesItemProvider != null) sampleMessagesItemProvider.dispose();
        if (sampleFileItemProvider != null) sampleFileItemProvider.dispose();
        if (sampleFromXsdItemProvider != null) sampleFromXsdItemProvider.dispose();
    }

}
