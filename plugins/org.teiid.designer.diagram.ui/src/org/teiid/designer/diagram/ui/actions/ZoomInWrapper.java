/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.diagram.ui.actions;

import org.eclipse.gef.editparts.ZoomListener;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.teiid.designer.diagram.ui.DiagramUiConstants;
import org.teiid.designer.diagram.ui.editor.DiagramEditor;
import org.teiid.designer.diagram.ui.editor.ZoomableEditor;
import org.teiid.designer.ui.editors.ModelEditor;


/**
 * ZoomInWrapper
 */
public class ZoomInWrapper
    extends DiagramAction
    implements DiagramUiConstants, IDiagramActionConstants, IPartListener, ActionWrapper, ZoomListener {

    private static final double[] zoomValues =
        { 0.1, 0.25, 0.50, 0.75, 0.9, 1.0, 1.10, 1.25, 1.50, 2.0, 3.0, 4.0, 10.0 };
    private boolean wasInitialized = false;
    private ZoomManager zoomManager;
    
    /**
     * Construct an instance of ZoomInWrapper.
     * 
     */
    public ZoomInWrapper() {
        super();
    }

    /**
     * Construct an instance of ZoomInWrapper.
     * @param theStyle
     */
    public ZoomInWrapper(int theStyle) {
        super(theStyle);
    }

    @Override
	public void initialize() {
        ModelEditor editor = getActiveEditor();
        
        if ( editor != null && editor.getCurrentPage() instanceof ZoomableEditor) {

            DiagramEditor deEditorPage = ((ZoomableEditor)editor.getCurrentPage()).getDiagramEditor();
            if ( deEditorPage != null ) {             
                zoomManager = (ZoomManager)deEditorPage.getAdapter(ZoomManager.class);
            } 
        }
		if (this.zoomManager != null) {
			this.zoomManager.setZoomLevels(zoomValues);
			if( !wasInitialized ) {
				wasInitialized = true;
				this.zoomManager.setZoom(getEditorZoomLevel());
			}  
			this.zoomManager.addZoomListener(this);
		}
		setEnableState();
		addAsPartListener(); 
    }

    @Override
    protected void doRun() {
        // zoom in
        ZoomManager zoomMgr = getZoomManager();

        if (zoomMgr != null) {

			ZoomInAction action = new ZoomInAction(zoomMgr);

            action.run();
            
            DiagramEditor de = getDiagramEditor();
            if( de != null ) {
                de.handleZoomChanged();
            }
            
            zoomMgr.removeZoomListener(action);

            // after ZoomInWrapper operation, reset enable state from the current zoommanager
            setEnabled(zoomMgr.canZoomIn());
        }
    }

    private DiagramEditor getDiagramEditor() {
        ModelEditor editor = getActiveEditor();
        
        if (editor != null && editor.getCurrentPage() instanceof ZoomableEditor) {

            DiagramEditor deEditorPage = ((ZoomableEditor)editor.getCurrentPage()).getDiagramEditor();
            if (deEditorPage != null) {
                return deEditorPage;
            }
        }
        
        return null;
    }

	public void closeZoomManager() {
		if( zoomManager != null ) {
			zoomManager.removeZoomListener(this);
			zoomManager = null;
		}
	}

    private double getEditorZoomLevel() {
        double currentZoom = 1.0;
        DiagramEditor de = getDiagramEditor();
        if( de != null ) {
            currentZoom = de.getCurrentZoomFactor();
        }
        return currentZoom;
    }
    
    private ZoomManager getZoomManager() {
    	if( zoomManager == null ) {
	        initialize();
		}
    	return zoomManager;
    }

    private void addAsPartListener() {
        getPlugin().getCurrentWorkbenchWindow().getPartService().addPartListener(this);
    }

    /**
     *  
     * @see org.teiid.designer.ui.common.actions.AbstractAction#dispose()
     * @since 5.0
     */
    @Override
    public void dispose() {
        getPlugin().getCurrentWorkbenchWindow().getPartService().removePartListener( this );
        super.dispose();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.ui.ISelectionListener#selectionChanged(IWorkbenchPart, ISelection)
     */
    @Override
    public void selectionChanged(IWorkbenchPart thePart, ISelection theSelection) {
        // no action here...zooms do not care about selection        
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.ISelectionListener#selectionChanged(IWorkbenchPart, ISelection)
     */
    public void setEnableState() {

        ModelEditor editor = getActiveEditor();

        if (editor != null && editor.getCurrentPage() instanceof DiagramEditor) {
            ZoomManager zoomMgr = getZoomManager();

            if (zoomMgr != null) {                         
                setEnabled(zoomMgr.canZoomIn());
            } else {
                setEnabled(false);
            }
        } else {
            setEnabled(false);
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IPartListener#partActivated(org.eclipse.ui.IWorkbenchPart)
     */
    @Override
	public void partActivated(IWorkbenchPart part) {
        setEnableState();
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IPartListener#partBroughtToTop(org.eclipse.ui.IWorkbenchPart)
     */
    @Override
	public void partBroughtToTop(IWorkbenchPart part) {
        setEnableState();
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IPartListener#partClosed(org.eclipse.ui.IWorkbenchPart)
     */
    @Override
	public void partClosed(IWorkbenchPart part) {
        setEnabled(false);
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IPartListener#partDeactivated(org.eclipse.ui.IWorkbenchPart)
     */
    @Override
	public void partDeactivated(IWorkbenchPart part) {
        setEnabled(false);
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.IPartListener#partOpened(org.eclipse.ui.IWorkbenchPart)
     */
    @Override
	public void partOpened(IWorkbenchPart part) {
        setEnableState();
    }

    @Override
	public void zoomChanged(double d) {
        // reset enable state; a change in zoom OUT may affect us here in zoom IN
        //        System.out.println("[ZoomOutWrapper.zoomChanged] !!!" ); //$NON-NLS-1$
        setEnableState();
    }

}
