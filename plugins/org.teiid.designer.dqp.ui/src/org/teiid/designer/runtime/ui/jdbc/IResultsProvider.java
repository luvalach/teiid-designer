/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.runtime.ui.jdbc;


/** 
 * @since 8.0
 */
public interface IResultsProvider {

    /** 
     * @return the current results
     * @since 5.5.3
     */
    IResults getResults();
}
