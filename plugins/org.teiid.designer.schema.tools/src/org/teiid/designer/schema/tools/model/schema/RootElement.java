/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.schema.tools.model.schema;


public interface RootElement {

	@Override
	public abstract String toString();

	public abstract boolean isUseAsRoot();

	public abstract SchemaObjectKey getKey();

	public abstract String getName();
	
	public abstract String getNamespace();
	
}
