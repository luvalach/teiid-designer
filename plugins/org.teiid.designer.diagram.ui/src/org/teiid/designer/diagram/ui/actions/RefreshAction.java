/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.diagram.ui.actions;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.geometry.Point;
//import org.eclipse.gef.EditPart;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPart;
import org.teiid.designer.diagram.ui.DiagramUiConstants;
import org.teiid.designer.diagram.ui.DiagramUiPlugin;
import org.teiid.designer.diagram.ui.editor.DiagramViewer;
import org.teiid.designer.diagram.ui.model.DiagramModelNode;


/**
 * RefreshAction
 *
 * @since 8.0
 */
public class RefreshAction extends DiagramEditorAction {
    /**
     * Construct an instance of NewEllipseAction.
     * 
     */
    public RefreshAction() {
        super();
        setImageDescriptor(DiagramUiPlugin.getDefault().getImageDescriptor(DiagramUiConstants.Images.REFRESH_DIAGRAM));
    }

    /* (non-Javadoc)
     * see org.teiid.designer.ui.common.actions.AbstractAction#doRun()
     */
    @Override
    protected void doRun() {
        // Let's get ahold of the current diagram...
        DiagramModelNode diagramNode = editor.getCurrentModel();
        if( diagramNode != null ) {
			final DiagramViewer scrolledViewer = editor.getDiagramViewer();
			final FigureCanvas scrolledCanvas = (FigureCanvas)scrolledViewer.getControl();
			final Point viewportPt = scrolledCanvas.getViewport().getViewLocation();
//			final List selectedEPs = new ArrayList(scrolledViewer.getSelectedEditParts());
			final int vValue = scrolledCanvas.getVerticalBar().getSelection();
			final int hValue = scrolledCanvas.getHorizontalBar().getSelection();
			
            editor.openContext(diagramNode.getModelObject(), true);

			Display.getDefault().asyncExec(new Runnable() {
				@Override
				public void run() {
					scrolledCanvas.getViewport().getContents().setVisible(false);
//					if( selectedEPs != null && !selectedEPs.isEmpty() ) { 
//						scrolledViewer.select((EditPart)selectedEPs.get(0));
//					}
					scrolledCanvas.getViewport().setViewLocation(viewportPt);
					scrolledCanvas.getVerticalBar().setSelection(vValue);
					scrolledCanvas.getHorizontalBar().setSelection(hValue);
					scrolledCanvas.getViewport().getContents().setVisible(true);
				}                    
			});

            
        }
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.ISelectionListener#selectionChanged(org.eclipse.ui.IWorkbenchPart, org.eclipse.jface.viewers.ISelection)
     */
    @Override
    public void selectionChanged(IWorkbenchPart thePart, ISelection theSelection) {
        super.selectionChanged(thePart, theSelection);
        setEnabled(true);
    }


}
