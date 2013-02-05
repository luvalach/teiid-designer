/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.komodo.repository.sramp.teiid;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.Map;
import org.komodo.common.util.HashCode;
import org.komodo.common.util.Precondition;
import org.komodo.common.util.StringUtil;
import org.komodo.repository.artifact.Artifact;
import org.komodo.repository.artifact.teiid.SchemaArtifact;
import org.komodo.repository.sramp.SrampArtifact;
import org.komodo.repository.sramp.SrampArtifactProperties;
import org.komodo.repository.sramp.SrampRepository;
import org.komodo.teiid.model.vdb.Schema;
import org.komodo.teiid.model.vdb.Source;
import org.s_ramp.xmlns._2010.s_ramp.BaseArtifactType;
import org.s_ramp.xmlns._2010.s_ramp.ExtendedArtifactType;

/**
 * An S-RAMP schema/model artifact.
 */
final class SrampSchemaArtifact extends Schema implements SchemaArtifact, SrampArtifact {

    private final ExtendedArtifactType delegate;
    private SrampArtifactProperties props;
    private final SrampRepository repository;

    /**
     * @param delegate the S-RAMP schema artifact (cannot be <code>null</code>)
     * @param repository the S-RAMP repository where the artifact is found (cannot be <code>null</code>)
     */
    public SrampSchemaArtifact(final ExtendedArtifactType delegate,
                               final SrampRepository repository) {
        Precondition.notNull(delegate, "delegate"); //$NON-NLS-1$
        Precondition.matchesExactly(delegate.getExtendedType(), SchemaArtifact.TYPE.getId(), "schema artifact type"); //$NON-NLS-1$
        Precondition.notNull(repository, "repository"); //$NON-NLS-1$

        this.delegate = delegate;
        this.repository = repository;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.ModelObject#addListener(java.beans.PropertyChangeListener)
     */
    @Override
    public boolean addListener(final PropertyChangeListener newListener) {
        throw new UnsupportedOperationException(); // TODO add message
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.vdb.Schema#addSource(org.komodo.teiid.model.vdb.Source)
     */
    @Override
    public void addSource(final Source newSource) {
        throw new UnsupportedOperationException(); // TODO add message
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.ModelObject#createPropertyChangeEvent(java.lang.String, java.lang.Object, java.lang.Object)
     */
    @Override
    protected PropertyChangeEvent createPropertyChangeEvent(final String name,
                                                            final Object oldValue,
                                                            final Object newValue) {
        throw new UnsupportedOperationException(); // TODO add message
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.vdb.Permission#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object that) {
        if ((that == null) || !getClass().equals(that.getClass())) {
            return false;
        }

        return getArtifactUuid().equals(((Artifact)that).getArtifactUuid());
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.ModelObject#firePropertyChangeEvent(java.lang.String, java.lang.Object, java.lang.Object)
     */
    @Override
    protected void firePropertyChangeEvent(final String name,
                                           final Object oldValue,
                                           final Object newValue) {
        throw new UnsupportedOperationException(); // TODO add message
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.repository.artifact.Artifact#getArtifactName()
     */
    @Override
    public String getArtifactName() {
        return this.delegate.getName();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.repository.artifact.Artifact#getArtifactType()
     */
    @Override
    public org.komodo.repository.artifact.Artifact.Type getArtifactType() {
        return SchemaArtifact.TYPE;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.repository.artifact.Artifact#getArtifactUuid()
     */
    @Override
    public String getArtifactUuid() {
        return this.delegate.getUuid();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.repository.artifact.Artifact#getArtifactVersion()
     */
    @Override
    public String getArtifactVersion() {
        return this.delegate.getVersion();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.repository.sramp.SrampArtifact#getDelegate()
     */
    @Override
    public BaseArtifactType getDelegate() {
        return this.delegate;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.Describable#getDescription()
     */
    @Override
    public String getDescription() {
        return this.delegate.getDescription();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.vdb.VdbObject#getId()
     */
    @Override
    public String getId() {
        return getArtifactName();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.vdb.Schema#getMetadata()
     */
    @Override
    public String getMetadata() {
        return getProperty(Schema.PropertyName.METADATA);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.vdb.Schema#getMetadataType()
     */
    @Override
    public String getMetadataType() {
        final String metadataType = getProperty(Schema.PropertyName.METADATA_TYPE);
        return (StringUtil.isEmpty(metadataType) ? Schema.DEFAULT_METADATA_TYPE.name() : metadataType);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.Propertied#getProperties()
     */
    @Override
    public Map<String, String> getProperties() {
        if (this.props == null) {
            this.props = new SrampArtifactProperties(this.delegate.getProperty());
        }

        return this.props;
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.Propertied#getProperty(java.lang.String)
     */
    @Override
    public String getProperty(final String name) {
        Precondition.notEmpty(name, "name"); //$NON-NLS-1$
        return getProperties().get(name);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.vdb.Schema#getSchemaType()
     */
    @Override
    public String getSchemaType() {
        return getProperty(Schema.PropertyName.TYPE);
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.vdb.Schema#getSources()
     */
    @Override
    public List<Source> getSources() {
        // TODO Auto-generated method stub
        return super.getSources();
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.vdb.Vdb#hashCode()
     */
    @Override
    public int hashCode() {
        return HashCode.compute(getArtifactUuid());
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.vdb.Schema#isVisible()
     */
    @Override
    public boolean isVisible() {
        final String visible = getProperty(Schema.PropertyName.VISIBLE);
        return (StringUtil.isEmpty(visible) ? Schema.DEFAULT_VISIBLE : Boolean.parseBoolean(visible));
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.ModelObject#removeListener(java.beans.PropertyChangeListener)
     */
    @Override
    public boolean removeListener(final PropertyChangeListener registeredListener) {
        throw new UnsupportedOperationException(); // TODO add message
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.Propertied#removeProperty(java.lang.String)
     */
    @Override
    public void removeProperty(final String name) {
        throw new UnsupportedOperationException(); // TODO add message
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.vdb.Schema#removeSource(org.komodo.teiid.model.vdb.Source)
     */
    @Override
    public void removeSource(final Source sourceToDelete) {
        throw new UnsupportedOperationException(); // TODO add message
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.Describable#setDescription(java.lang.String)
     */
    @Override
    public void setDescription(final String newDescription) {
        throw new UnsupportedOperationException(); // TODO add message
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.vdb.VdbObject#setId(java.lang.String)
     */
    @Override
    public void setId(final String newId) {
        throw new UnsupportedOperationException(); // TODO add message
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.vdb.Schema#setMetadata(java.lang.String)
     */
    @Override
    public void setMetadata(final String newMetadata) {
        throw new UnsupportedOperationException(); // TODO add message
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.vdb.Schema#setMetadataType(java.lang.String)
     */
    @Override
    public void setMetadataType(final String newMetadataType) {
        throw new UnsupportedOperationException(); // TODO add message
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.Propertied#setProperty(java.lang.String, java.lang.String)
     */
    @Override
    public void setProperty(final String name,
                            final String newValue) {
        throw new UnsupportedOperationException(); // TODO add message
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.vdb.Schema#setType(java.lang.String)
     */
    @Override
    public void setType(final String newType) {
        throw new UnsupportedOperationException(); // TODO add message
    }

    /**
     * {@inheritDoc}
     *
     * @see org.komodo.teiid.model.vdb.Schema#setVisible(boolean)
     */
    @Override
    public void setVisible(final boolean newVisible) {
        throw new UnsupportedOperationException(); // TODO add message
    }

}
