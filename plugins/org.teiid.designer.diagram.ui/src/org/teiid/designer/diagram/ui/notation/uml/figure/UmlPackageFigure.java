/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.diagram.ui.notation.uml.figure;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.ImageFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.Polygon;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.teiid.designer.diagram.ui.DiagramUiPlugin;
import org.teiid.designer.diagram.ui.PluginConstants;
import org.teiid.designer.diagram.ui.actions.ScaledFont;
import org.teiid.designer.diagram.ui.actions.ScaledFontManager;
import org.teiid.designer.diagram.ui.figure.AbstractDiagramFigure;
import org.teiid.designer.diagram.ui.figure.LabeledRectangleFigure;
import org.teiid.designer.diagram.ui.util.colors.ColorPalette;
import org.teiid.designer.diagram.ui.util.directedit.DirectEditFigure;

/**
 * @author blafond
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class UmlPackageFigure extends AbstractDiagramFigure implements DirectEditFigure {
    private ImageFigure errorIcon;
    private ImageFigure warningIcon;
    
    private Polygon packageOutline;
    private LabeledRectangleFigure stereotypeLabel;
    private LabeledRectangleFigure nameLabel;
    private LabeledRectangleFigure locationLabel;
    private int tabHeight = 15;
    private int tabWidth = 40;
    private static final int MIN_TAB_WIDTH = 40;
    private static final int ySpacing = 2;
    private static final int xInset = 6;
    
    private PointList packagePoints = new PointList();


    /**
     * 
     */
    public UmlPackageFigure(ColorPalette colorPalette) {
        super(colorPalette);
                
        init("<<stereotype>>", "package", "location", null, null); //$NON-NLS-3$ //$NON-NLS-2$ //$NON-NLS-1$
        
        createComponent();
    }
    /**
     * 
     */
    public UmlPackageFigure(String stereotype, String name, String location, Image icon, ColorPalette colorPalette) {
        super(colorPalette);
        
        init(stereotype, name, location, icon, colorPalette);
        
        createComponent();
    }
    
    private void init(String stereotype, String name, String location, Image icon, ColorPalette colorPalette) {

        packageOutline = new Polygon();
        packagePoints.addPoint(0, 0);
        packagePoints.addPoint(40, 0);
        packagePoints.addPoint(40, 15);
        packagePoints.addPoint(120, 15);
        packagePoints.addPoint(120, 80);
        packagePoints.addPoint(0, 80);
        packagePoints.addPoint(0, 0);
        packageOutline.setPoints(packagePoints);
        this.add(packageOutline);

        packageOutline.setForegroundColor(ColorConstants.darkBlue);
        packageOutline.setBackgroundColor(getColor(ColorPalette.SECONDARY_BKGD_COLOR_ID));
        packageOutline.setFill(true);
        packageOutline.setLineWidth(0);
        
        if( stereotype != null ) {
            if (stereotypeLabel == null) {
                stereotypeLabel = new LabeledRectangleFigure(stereotype, true, colorPalette);
                this.add(stereotypeLabel);
            } else {
                stereotypeLabel.getLabel().setText(stereotype);
            }
            stereotypeLabel.setTextColor(ColorConstants.darkGray);

        }
        if( name != null ) {
            if (nameLabel == null) {
                if( icon != null )
                    nameLabel = new LabeledRectangleFigure(name, icon, true, colorPalette);
                else
                    nameLabel = new LabeledRectangleFigure(name, true, null);
                this.add(nameLabel);
            } else {
                nameLabel.getLabel().setText(name);
            }
            nameLabel.layoutFigure();
        }
        if (location != null) {
            if (locationLabel == null) {
                locationLabel = new LabeledRectangleFigure(location, true, colorPalette);
                this.add(locationLabel);
            } else {
                locationLabel.getLabel().setText(name);
            }
            locationLabel.setTextColor(ColorConstants.darkGray);
        }
        refreshFont();
    }
    
    private void createComponent() {
        setInitialSize();
        if (stereotypeLabel != null)
            stereotypeLabel.setBackgroundColor(packageOutline.getLocalBackgroundColor());
        if (nameLabel != null)
            nameLabel.setBackgroundColor(packageOutline.getLocalBackgroundColor());
        if (locationLabel != null)
            locationLabel.setBackgroundColor(packageOutline.getLocalBackgroundColor());
    }
    
//    private void setLabelSize( Label label ) {
//
//        Font theFont = label.getFont();
//
//        int labelWidth = FigureUtilities.getTextExtents(label.getText(), theFont).width;
//        if (label.getIcon() != null)
//            labelWidth += label.getIcon().getBounds().width;
//        int labelHeight = FigureUtilities.getTextExtents(label.getText(), theFont).height;
//    
//        label.setSize(labelWidth, labelHeight);
//    }
     
    private void setInitialSize() {
        setTab();
        int maxWidth = tabWidth;
        int maxHeight = tabHeight + ySpacing*2;
        if( nameLabel != null ) {
            maxHeight += nameLabel.getBounds().height + ySpacing;
            maxWidth = Math.max( maxWidth, nameLabel.getSize().width );
        }
        
        if( locationLabel != null ) {
            maxHeight += locationLabel.getBounds().height + ySpacing;
            maxWidth = Math.max( maxWidth, locationLabel.getSize().width );
        }
        
        maxWidth += xInset*4;
        maxHeight += ySpacing*2;
        replacePoint( 1, tabWidth, 0);
        replacePoint( 2, tabWidth, tabHeight);
        replacePoint( 3, maxWidth, tabHeight);
        replacePoint( 4, maxWidth, maxHeight);
        replacePoint( 5, 0, maxHeight);
        
        packageOutline.setPoints(packagePoints);
        
        this.setSize(new Dimension(maxWidth, maxHeight));
    }
    
    public void setTab() {
        if( stereotypeLabel != null && stereotypeLabel.getSize().width > MIN_TAB_WIDTH ) {
            tabHeight = stereotypeLabel.getSize().height + 2*ySpacing;
            tabWidth = stereotypeLabel.getSize().width + xInset*4;
        }
    }

    @Override
    protected boolean useLocalCoordinates(){
        return true;
    }
    
    private void replacePoint(int index, int newX, int newY ) {
        packagePoints.setPoint(new Point(newX, newY), index);
    }
    
    @Override
    public void layoutFigure() {
        // Need to resize package to fit header
        setTab();
        
        if( stereotypeLabel != null )
            stereotypeLabel.setLocation( new Point(xInset, ySpacing));
            
        int centerX = getSize().width/2;
        int currentY = tabHeight + ySpacing*2;
        
        if( nameLabel != null ) {
            nameLabel.setLocation( 
                new Point(centerX - nameLabel.getBounds().width/2,
                          currentY) );
            currentY += nameLabel.getBounds().height + ySpacing;
        }

        if( locationLabel != null ) {
            locationLabel.setLocation( 
                new Point(centerX - locationLabel.getBounds().width/2,
                          currentY) );
        }
        resetIconLocations();
    }
    
    private void resetIconLocations() {
        if( errorIcon != null ) {
            errorIcon.setLocation(
                new Point( nameLabel.getLocation().x, 
                           nameLabel.getLocation().y + errorIcon.getBounds().height));
        }
        if( warningIcon != null ) {
            warningIcon.setLocation(
                                  new Point( nameLabel.getLocation().x, 
                                             nameLabel.getLocation().y + warningIcon.getBounds().height));
        }
    }
    
    @Override
    public void updateForSize(Dimension newSize) {
        replacePoint( 3, newSize.width, tabHeight);
        replacePoint( 4, newSize.width, newSize.height);
        replacePoint( 5, 0, newSize.height);
        packageOutline.setPoints(packagePoints);
        resetIconLocations();
//        layoutFigure();
    }
    
    
    /* (non-Javadoc)
     * @See org.teiid.designer.diagram.ui.figure.DiagramFigure#updateForName(java.lang.String)
     */
    @Override
    public void updateForName(String newName) {
        nameLabel.updateForName(newName);
        setInitialSize();
    }
    
    
    public void updateForPath(String newPath) {
        if (locationLabel != null)
            locationLabel.updateForName(newPath);
    }

    /* (non-Javadoc)
     * @See org.teiid.designer.diagram.ui.figure.DiagramFigure#refreshFont()
     */
    @Override
    public void refreshFont() {
        Font baseFont = ScaledFontManager.getFont();
        Font smallerFont = getSmallerFont();
        
        if (stereotypeLabel != null)
            stereotypeLabel.updateForFont(smallerFont);
        if (nameLabel != null)
            nameLabel.updateForFont(baseFont);
        if (locationLabel != null)
            locationLabel.updateForFont(smallerFont);
        layoutFigure();
        setInitialSize();
        updateForSize(this.getSize());
    }
    
    private Font getSmallerFont() {
		return ScaledFontManager.getFont(ScaledFont.SMALLER_PLAIN_STYLE);
//        int iCurrGeneralFontSize = ScaledFontManager.getSize();
//        int iNewLabelFontSize = 0;
//
////        if (ScaledFontManager.canDecrease(iCurrGeneralFontSize - 2)) {
////            // we can decrease by 3
////            iNewLabelFontSize = iCurrGeneralFontSize - 3;
////        } else 
//        if (ScaledFontManager.canDecrease(iCurrGeneralFontSize - 1)) {
//            // we can decrease by 2
//            iNewLabelFontSize = iCurrGeneralFontSize - 2;
//        } else if (ScaledFontManager.canDecrease(iCurrGeneralFontSize)) {
//            // we can only decrease by 1
//            iNewLabelFontSize = iCurrGeneralFontSize - 1;
//        } else {
//            // no room to decrease at all
//            iNewLabelFontSize = iCurrGeneralFontSize;
//        }
//
//        // construct the new font
//        Font smallerFont =
//            new Font(null, ScaledFontManager.getName(), iNewLabelFontSize, ScaledFontManager.getStyle());
//
//        return smallerFont;
    }
    
    @Override
    public void updateForError(boolean hasErrors) {
        if( hasErrors ) {
            if( errorIcon == null ) {
                errorIcon = new ImageFigure(DiagramUiPlugin.getDefault().getImage(PluginConstants.Images.ERROR_ICON));
                if( errorIcon != null ) {
                    this.add(errorIcon);
                    errorIcon.setLocation(new Point(nameLabel.getLocation().x, nameLabel.getLocation().y));
                    errorIcon.setSize(errorIcon.getPreferredSize());
                }
            }
        } else if( errorIcon != null ) {
            this.remove(errorIcon);
            errorIcon = null;
        }
    }
    
    
    @Override
    public void updateForWarning(boolean hasWarnings) {
        if( hasWarnings ) {
            if( warningIcon == null ) {
                warningIcon = new ImageFigure(DiagramUiPlugin.getDefault().getImage(PluginConstants.Images.WARNING_ICON));
                if( warningIcon != null ) {
                    this.add(warningIcon);
                    warningIcon.setLocation(new Point(nameLabel.getLocation().x, nameLabel.getLocation().y));
                    warningIcon.setSize(warningIcon.getPreferredSize());
                }
            }
        } else if( warningIcon != null ) {
            this.remove(warningIcon);
            warningIcon = null;
        }
    }
    
	@Override
	public Label getLabelFigure() {
		return nameLabel.getLabel();
	}
    
    public IFigure getNameFigure() {
        return nameLabel;
    }
    
    private void paintOutlineRaisedBorder(Graphics g) {
        int orgX = this.getBounds().x;
        int orgY = this.getBounds().y;
        PointList pts = packageOutline.getPoints();
        g.setLineStyle(Graphics.LINE_SOLID);
        g.setLineWidth(2);
        g.setXORMode(false);
        
        // Draw segements 1-2, 3-4, 4-5
        g.setForegroundColor(ColorConstants.buttonDarkest);
        g.drawLine(orgX + pts.getPoint(1).x, orgY + pts.getPoint(1).y, 
                   orgX + pts.getPoint(2).x, orgY + pts.getPoint(2).y);
        g.drawLine(orgX + pts.getPoint(3).x-1, orgY + pts.getPoint(3).y, 
                   orgX + pts.getPoint(4).x-1, orgY + pts.getPoint(4).y);
        g.drawLine(orgX + pts.getPoint(4).x, orgY + pts.getPoint(4).y-1, 
                   orgX + pts.getPoint(5).x-1, orgY + pts.getPoint(5).y-1);
        
        // Draw segements 0-1, 2-3, 5-6
        g.setForegroundColor(ColorConstants.buttonLightest);
        g.drawLine(orgX + pts.getPoint(0).x, orgY + pts.getPoint(0).y+1, 
                   orgX + pts.getPoint(1).x, orgY + pts.getPoint(1).y+1);
        g.drawLine(orgX + pts.getPoint(2).x, orgY + pts.getPoint(2).y, 
                   orgX + pts.getPoint(3).x-1, orgY + pts.getPoint(3).y);
        g.drawLine(orgX + pts.getPoint(5).x+1, orgY + pts.getPoint(5).y-1, 
                   orgX + pts.getPoint(6).x+1, orgY + pts.getPoint(6).y);
    }
    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        paintOutlineRaisedBorder(g);
    }
}


