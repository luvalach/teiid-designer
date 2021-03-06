/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.metamodels.wsdl.mime.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.InternalEList;
import org.teiid.designer.metamodels.wsdl.mime.MimeElement;
import org.teiid.designer.metamodels.wsdl.mime.MimeMultipartRelated;
import org.teiid.designer.metamodels.wsdl.mime.MimePackage;
import org.teiid.designer.metamodels.wsdl.mime.MimePart;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Part</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.teiid.designer.metamodels.wsdl.mime.impl.MimePartImpl#getMimeElements <em>Mime Elements</em>}</li>
 * <li>{@link org.teiid.designer.metamodels.wsdl.mime.impl.MimePartImpl#getMimeMultipartRelated <em>Mime Multipart Related</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 *
 * @since 8.0
 */
public class MimePartImpl extends EObjectImpl implements MimePart {

    /**
     * The cached value of the '{@link #getMimeElements() <em>Mime Elements</em>}' containment reference list. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @see #getMimeElements()
     * @generated
     * @ordered
     */
    protected EList mimeElements = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected MimePartImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return MimePackage.eINSTANCE.getMimePart();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
	public EList getMimeElements() {
        if (mimeElements == null) {
            mimeElements = new EObjectContainmentWithInverseEList(MimeElement.class, this, MimePackage.MIME_PART__MIME_ELEMENTS,
                                                                  MimePackage.MIME_ELEMENT__MIME_ELEMENT_OWNER);
        }
        return mimeElements;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
	public MimeMultipartRelated getMimeMultipartRelated() {
        if (eContainerFeatureID != MimePackage.MIME_PART__MIME_MULTIPART_RELATED) return null;
        return (MimeMultipartRelated)eContainer;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
	public void setMimeMultipartRelated( MimeMultipartRelated newMimeMultipartRelated ) {
        if (newMimeMultipartRelated != eContainer
            || (eContainerFeatureID != MimePackage.MIME_PART__MIME_MULTIPART_RELATED && newMimeMultipartRelated != null)) {
            if (EcoreUtil.isAncestor(this, newMimeMultipartRelated)) throw new IllegalArgumentException(
                                                                                                        "Recursive containment not allowed for " + toString()); //$NON-NLS-1$
            NotificationChain msgs = null;
            if (eContainer != null) msgs = eBasicRemoveFromContainer(msgs);
            if (newMimeMultipartRelated != null) msgs = ((InternalEObject)newMimeMultipartRelated).eInverseAdd(this,
                                                                                                               MimePackage.MIME_MULTIPART_RELATED__MIME_PARTS,
                                                                                                               MimeMultipartRelated.class,
                                                                                                               msgs);
            msgs = eBasicSetContainer((InternalEObject)newMimeMultipartRelated,
                                      MimePackage.MIME_PART__MIME_MULTIPART_RELATED,
                                      msgs);
            if (msgs != null) msgs.dispatch();
        } else if (eNotificationRequired()) eNotify(new ENotificationImpl(this, Notification.SET,
                                                                          MimePackage.MIME_PART__MIME_MULTIPART_RELATED,
                                                                          newMimeMultipartRelated, newMimeMultipartRelated));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd( InternalEObject otherEnd,
                                          int featureID,
                                          Class baseClass,
                                          NotificationChain msgs ) {
        if (featureID >= 0) {
            switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
                case MimePackage.MIME_PART__MIME_ELEMENTS:
                    return ((InternalEList)getMimeElements()).basicAdd(otherEnd, msgs);
                case MimePackage.MIME_PART__MIME_MULTIPART_RELATED:
                    if (eContainer != null) msgs = eBasicRemoveFromContainer(msgs);
                    return eBasicSetContainer(otherEnd, MimePackage.MIME_PART__MIME_MULTIPART_RELATED, msgs);
                default:
                    return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
            }
        }
        if (eContainer != null) msgs = eBasicRemoveFromContainer(msgs);
        return eBasicSetContainer(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove( InternalEObject otherEnd,
                                             int featureID,
                                             Class baseClass,
                                             NotificationChain msgs ) {
        if (featureID >= 0) {
            switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
                case MimePackage.MIME_PART__MIME_ELEMENTS:
                    return ((InternalEList)getMimeElements()).basicRemove(otherEnd, msgs);
                case MimePackage.MIME_PART__MIME_MULTIPART_RELATED:
                    return eBasicSetContainer(null, MimePackage.MIME_PART__MIME_MULTIPART_RELATED, msgs);
                default:
                    return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
            }
        }
        return eBasicSetContainer(null, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainer( NotificationChain msgs ) {
        if (eContainerFeatureID >= 0) {
            switch (eContainerFeatureID) {
                case MimePackage.MIME_PART__MIME_MULTIPART_RELATED:
                    return eContainer.eInverseRemove(this,
                                                     MimePackage.MIME_MULTIPART_RELATED__MIME_PARTS,
                                                     MimeMultipartRelated.class,
                                                     msgs);
                default:
                    return eDynamicBasicRemoveFromContainer(msgs);
            }
        }
        return eContainer.eInverseRemove(this, EOPPOSITE_FEATURE_BASE - eContainerFeatureID, null, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet( EStructuralFeature eFeature,
                        boolean resolve ) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case MimePackage.MIME_PART__MIME_ELEMENTS:
                return getMimeElements();
            case MimePackage.MIME_PART__MIME_MULTIPART_RELATED:
                return getMimeMultipartRelated();
        }
        return eDynamicGet(eFeature, resolve);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eSet( EStructuralFeature eFeature,
                      Object newValue ) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case MimePackage.MIME_PART__MIME_ELEMENTS:
                getMimeElements().clear();
                getMimeElements().addAll((Collection)newValue);
                return;
            case MimePackage.MIME_PART__MIME_MULTIPART_RELATED:
                setMimeMultipartRelated((MimeMultipartRelated)newValue);
                return;
        }
        eDynamicSet(eFeature, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eUnset( EStructuralFeature eFeature ) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case MimePackage.MIME_PART__MIME_ELEMENTS:
                getMimeElements().clear();
                return;
            case MimePackage.MIME_PART__MIME_MULTIPART_RELATED:
                setMimeMultipartRelated((MimeMultipartRelated)null);
                return;
        }
        eDynamicUnset(eFeature);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean eIsSet( EStructuralFeature eFeature ) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case MimePackage.MIME_PART__MIME_ELEMENTS:
                return mimeElements != null && !mimeElements.isEmpty();
            case MimePackage.MIME_PART__MIME_MULTIPART_RELATED:
                return getMimeMultipartRelated() != null;
        }
        return eDynamicIsSet(eFeature);
    }

} // MimePartImpl
