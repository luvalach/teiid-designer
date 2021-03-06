/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.transformation.ui.textimport;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.teiid.designer.core.ModelerCore;
import org.teiid.designer.core.metamodel.aspect.AspectManager;
import org.teiid.designer.core.metamodel.aspect.MetamodelAspect;
import org.teiid.designer.core.metamodel.aspect.uml.UmlPackage;
import org.teiid.designer.core.workspace.ModelResource;
import org.teiid.designer.core.workspace.ModelUtil;
import org.teiid.designer.core.workspace.ModelWorkspaceException;
import org.teiid.designer.metamodels.core.ModelType;
import org.teiid.designer.relational.ui.wizards.RelationalObjectProcessor;
import org.teiid.designer.transformation.ui.UiConstants;
import org.teiid.designer.ui.common.util.WidgetFactory;
import org.teiid.designer.ui.common.util.WidgetUtil;
import org.teiid.designer.ui.common.viewsupport.StatusInfo;
import org.teiid.designer.ui.explorer.ModelExplorerContentProvider;
import org.teiid.designer.ui.explorer.ModelExplorerLabelProvider;
import org.teiid.designer.ui.viewsupport.ModelIdentifier;
import org.teiid.designer.ui.viewsupport.ModelNameUtil;
import org.teiid.designer.ui.viewsupport.ModelUtilities;
import org.teiid.designer.ui.viewsupport.ModelWorkspaceDialog;


/**
 * @since 8.0
 */
public class VirtualModelSelectorDialog extends ModelWorkspaceDialog implements UiConstants {
    private Label lblVirtualModelName;
    private Text txtVirtualModelName;
    private IContainer newModelParent;
    private IPath targetVirtualFilePath;
    private ModelResource mrVirtualModel;
    private EObject selectedEObject;
    private SelectionAdapter saCreateCbxAdapter;
    private String sNewVirtualModelName;
    boolean createNewModel = false;

    private final static String FILE_EXT = ".xmi"; //$NON-NLS-1$
    private final static int MODEL_NAME_TEXT_WIDTH = (int)(Display.getCurrent().getBounds().width * .25);

    private static final String I18N_PREFIX = "VirtualModelSelectorDialog"; //$NON-NLS-1$
    private static final String SEPARATOR = "."; //$NON-NLS-1$

    private static final String TITLE = getString("title"); //$NON-NLS-1$
    private final static String MODEL_CREATE_ERROR_IS_VALID = getString("isValid.message"); //$NON-NLS-1$
    private static String MODEL_CREATE_INSTRUCTION = getString("modelCreateInstruction.message"); //$NON-NLS-1$  
    private static String EXISTING_MODEL_FOLDER_SELECTED = getString("existingModelFolderLocationSelected.message"); //$NON-NLS-1$
    private final static String VIRTUAL_LABEL = getString("virtualModelName.text"); //$NON-NLS-1$
    private final static String SELECTED_MODEL_NOT_VIRTUAL_MODEL = getString("selModelNotVirtual.message"); //$NON-NLS-1$
    private final static String SELECTED_RESOURCE_NOT_VIRTUAL_MODEL = getString("selResrcNotVirtual.message"); //$NON-NLS-1$

    private static String getString( final String id ) {
        return Util.getString(I18N_PREFIX + SEPARATOR + id);
    }

    /**
     * Construct an instance of ModelWorkspaceDialog. This constructor defaults to the resource root.
     * 
     * @param parent
     */
    public VirtualModelSelectorDialog( Shell parent ) {
        this(parent, new ModelExplorerLabelProvider(), new ModelExplorerContentProvider());
    }

    /**
     * Construct an instance of ModelWorkspaceDialog. This constructor defaults to the resource root.
     * 
     * @param parent
     * @param labelProvider an ILabelProvider for the tree
     * @param contentProvider an ITreeContentProvider for the tree
     */
    public VirtualModelSelectorDialog( Shell parent,
                                       ILabelProvider labelProvider,
                                       ITreeContentProvider contentProvider ) {
        super(parent, TITLE, labelProvider, contentProvider);
    }

    /*
     * @see Dialog#createDialogArea(Composite)
     */
    @Override
    protected Control createDialogArea( Composite parent ) {
        Composite composite = (Composite)super.createDialogArea(parent);

        // add code here to include new virtual model panel:
        createModelCreationComposite(composite);

        return composite;
    }

    /**
     * Create the controls for creating a new virtual Model.
     * 
     * @param parent the parent composite
     * @return the created composite
     */
    private Composite createModelCreationComposite( Composite parent ) {

        // Set up Composite
        Composite nameComposite = new Composite(parent, SWT.NONE);
        GridLayout nameCompositeLayout = new GridLayout();
        nameComposite.setLayout(nameCompositeLayout);
        nameCompositeLayout.numColumns = 3;
        nameCompositeLayout.marginWidth = 0;
        GridData nameCompositeGridData = new GridData(GridData.FILL_HORIZONTAL);
        nameCompositeGridData.horizontalIndent = 20;
        nameComposite.setLayoutData(nameCompositeGridData);

        // Enter Model name label - col 1 VIRTUAL_LABEL
        lblVirtualModelName = new Label(nameComposite, SWT.NONE);
        lblVirtualModelName.setText(VIRTUAL_LABEL);
        lblVirtualModelName.getAlignment();

        // Model name entry field - col 2
        txtVirtualModelName = WidgetFactory.createTextField(nameComposite, GridData.HORIZONTAL_ALIGN_FILL);
        GridData modelNameTextGridData = new GridData();
        modelNameTextGridData.widthHint = MODEL_NAME_TEXT_WIDTH;
        txtVirtualModelName.setLayoutData(modelNameTextGridData);

        // listener for typing changes
        txtVirtualModelName.addModifyListener(new ModifyListener() {
            @Override
			public void modifyText( final ModifyEvent event ) {
                handleSetVirtualModelName();
            }
        });

        // This initializes the status label
        handleSetVirtualModelName();

        setCreateControlsEnabled(false);

        return nameComposite;
    }

    /**
     * handler for Create Virtual Model Button pressed
     */
    void createVirtualModel() {

        mrVirtualModel = constructVirtualModel(newModelParent, sNewVirtualModelName);

        // Save Virtual Model
        try {
            if (mrVirtualModel != null) {
                mrVirtualModel.save(null, false);

            }
        } catch (ModelWorkspaceException mwe) {
            Util.log(mwe);
        }
    }

    @Override
    public Object[] getResult() {

        // if they created a new relational model, return it
        if (mrVirtualModel != null) {
            return new Object[] {mrVirtualModel};
        } else if (selectedEObject != null) {
            return new Object[] {selectedEObject};
        }
        // if they selected an existing relational model, return it
        return super.getResult();
    }

    private SelectionAdapter getOKSelectionListener() {

        // establish the selection adapter for the OK button
        if (saCreateCbxAdapter == null) {
            saCreateCbxAdapter = new SelectionAdapter() {
                @Override
                public void widgetSelected( final SelectionEvent event ) {
                    if (createNewModel) {
                        // create the new Virtual Model
                        createVirtualModel();
                    }
                }
            };
        }
        return saCreateCbxAdapter;
    }

    private void updateDialogMessage( String sMessage,
                                      boolean bIsError ) {
        int iStatusCode = IStatus.OK;

        if (bIsError) {
            iStatusCode = IStatus.ERROR;
        }

        IStatus status = new StatusInfo(PLUGIN_ID, iStatusCode, sMessage);

        updateStatus(status);
    }

    protected void registerControls() {
        // System.out.println("VirtualModelSelectorDialog.registerControls() adding as selection changed listener to tree viewer....");
        getTreeViewer().addSelectionChangedListener(this);
    }

    @Override
    public void selectionChanged( SelectionChangedEvent event ) {
        super.selectionChanged(event);

        // set default: null the selected project var
        newModelParent = null;

        IStructuredSelection sel = (IStructuredSelection)getTreeViewer().getSelection();

        Object oSelection = sel.getFirstElement();

        // assume we are creating a new model
        createNewModel = true;

        // Selection is a Virtual Relational Model
        if (isVirtualModelResource(oSelection)) {
            // null the selected project var
            newModelParent = null;
            selectedEObject = null;
            createNewModel = false;
            String instruction = Util.getString("VirtualModelSelectorDialog.useExistingModelInstruction.message", //$NON-NLS-1$ 
                                                ((IResource)oSelection).getName());

            updateDialogMessage(instruction, false);
            setCreateControlsEnabled(false);
            enableOKButton();
            // Selection is a Model, but not a Relational Model
        } else if (isModelResource(oSelection)) {
            newModelParent = null;
            selectedEObject = null;
            setCreateControlsEnabled(false);
            disableOKButton();
            updateDialogMessage(SELECTED_MODEL_NOT_VIRTUAL_MODEL, true);
            // Selection is a Container
        } else if (isContainer(oSelection)) {
            // capture the selected project
            newModelParent = (IContainer)oSelection;
            selectedEObject = null;
            setCreateControlsEnabled(true);

            // this will validate the model name
            handleSetVirtualModelName();
        } else if (isPackageInVirtualModel(oSelection)) {
            selectedEObject = (EObject)oSelection;
            newModelParent = null;
            createNewModel = false;
            setCreateControlsEnabled(false);
            enableOKButton();

            updateDialogMessage(EXISTING_MODEL_FOLDER_SELECTED, false);
            // Resource, not a Relational Model
        } else if (oSelection instanceof IResource) {
            newModelParent = null;
            selectedEObject = null;
            setCreateControlsEnabled(false);
            disableOKButton();

            updateDialogMessage(SELECTED_RESOURCE_NOT_VIRTUAL_MODEL, true);
        }
    }

    /**
     * handler when the virtual Model name field changes. This updates the status label under the modelName entry field
     */
    void handleSetVirtualModelName() {

        // get the text from the Text field
        String nameText = txtVirtualModelName.getText();

        // validate it
        if (isValidVirtualModelName(nameText)) {
            sNewVirtualModelName = nameText;
            enableOKButton();
            String message = getVirtualModelNameStatus(nameText);
            updateDialogMessage(message, false);
        } else {
            disableOKButton();

            // if the name has zero length, reapply the 'create' message
            if (nameText.trim().length() == 0) {
                if (newModelParent != null) {
                    MODEL_CREATE_INSTRUCTION = Util.getString("VirtualModelSelectorDialog.modelCreateInstruction.message", //$NON-NLS-1$ 
                                                              newModelParent.getName());
                    updateDialogMessage(MODEL_CREATE_INSTRUCTION, true);
                }
            } else {
                // use the message from the character validation
                String message = getVirtualModelNameStatus(nameText);
                updateDialogMessage(message, true);
            }
        }
    }

    /**
     * Create a Virtual Model with the supplied name, in the desired project
     * 
     * @param targetProj the project resource under which to create the model
     * @param modelName the model name to create
     * @return the newly-created ModelResource
     */
    public ModelResource constructVirtualModel( IResource targetRes,
                                                String sModelName ) {

        String sFileName = getFileName(sModelName);
        IPath relativeModelPath = targetRes.getProjectRelativePath().append(sFileName);
        final IFile modelFile = targetRes.getProject().getFile(relativeModelPath);
        final ModelResource resrc = ModelerCore.create(modelFile);
        try {
            resrc.getModelAnnotation().setPrimaryMetamodelUri(RelationalObjectProcessor.RELATIONAL_PACKAGE_URI);
            resrc.getModelAnnotation().setModelType(ModelType.VIRTUAL_LITERAL);
        } catch (ModelWorkspaceException mwe) {
            mwe.printStackTrace();
        }
        return resrc;
    }

    /**
     * get a string status indicator for the modelName status label, given the modelName
     * 
     * @param modelName the model name to test
     * @return the status of the supplied model name
     */
    private String getVirtualModelNameStatus( String sModelName ) {
        
        IStatus status = ModelNameUtil.validate(sModelName, ModelerCore.MODEL_FILE_EXTENSION, newModelParent,
        		ModelNameUtil.IGNORE_CASE | ModelNameUtil.NO_DUPLICATE_MODEL_NAMES_OTHER_THAN_LOCATION | ModelNameUtil.NO_DUPLICATE_MODEL_NAMES);
        if( status.getSeverity() == IStatus.ERROR ) {
            return ModelNameUtil.MESSAGES.INVALID_MODEL_NAME + status.getMessage();
        }

        // success
        return MODEL_CREATE_ERROR_IS_VALID;
    }

    /**
     * test whether the supplied modelName is valid
     * 
     * @param modelName the model name to test
     * @return 'true' if the name is valid, 'false' if not.
     */
    private boolean isValidVirtualModelName( String sModelName ) {
    	
        IStatus status = ModelNameUtil.validate(sModelName, ModelerCore.MODEL_FILE_EXTENSION, newModelParent,
        		ModelNameUtil.IGNORE_CASE | ModelNameUtil.NO_DUPLICATE_MODEL_NAMES_OTHER_THAN_LOCATION | ModelNameUtil.NO_DUPLICATE_MODEL_NAMES);
        if( status.getSeverity() == IStatus.ERROR ) {
            return false;
        }

        // success
        return true;
    }

    private void setCreateControlsEnabled( boolean b ) {
        txtVirtualModelName.setEnabled(b);
        lblVirtualModelName.setEnabled(b);
    }

    private void disableOKButton() {
        if (getOkButton() != null) {
            getOkButton().removeSelectionListener(getOKSelectionListener());
            getOkButton().setEnabled(false);
        }
    }

    private void enableOKButton() {
        if (getOkButton() != null) {
            getOkButton().addSelectionListener(getOKSelectionListener());
            getOkButton().setEnabled(true);
        }
    }

    private boolean isVirtualModelResource( Object oSelection ) {
        boolean bResult = false;
        if (oSelection instanceof IResource) {

            IResource resource = (IResource)oSelection;

            if (ModelUtilities.isModelFile(resource)) {

                ModelResource mr = null;

                try {
                    mr = ModelUtil.getModelResource((IFile)resource, true);
                } catch (ModelWorkspaceException theException) {
                    ModelerCore.Util.log(IStatus.ERROR, theException, theException.getMessage());
                    WidgetUtil.showError(theException);
                }

                if (ModelIdentifier.isVirtualModelType(mr)) {
                    try {
                        if (mr.getPrimaryMetamodelDescriptor() != null) {
                            if (mr.getPrimaryMetamodelDescriptor().getNamespaceURI().equals(RelationalObjectProcessor.RELATIONAL_PACKAGE_URI)) {
                                bResult = true;
                            }
                        }
                    } catch (ModelWorkspaceException mwe) {
                        ModelerCore.Util.log(IStatus.ERROR, mwe, mwe.getMessage());
                        WidgetUtil.showError(mwe);
                    }
                }
            }

        }
        return bResult;
    }

    private boolean isModelResource( Object oSelection ) {
        boolean bResult = false;
        if (oSelection instanceof IResource) {
            try {

                IResource resource = (IResource)oSelection;

                if (ModelUtilities.isModelFile(resource)) {
                    ModelResource mr = ModelUtil.getModelResource((IFile)resource, true);
                    if (mr.getPrimaryMetamodelDescriptor() != null) {
                        bResult = true;
                    }
                }
            } catch (ModelWorkspaceException mwe) {
                ModelerCore.Util.log(IStatus.ERROR, mwe, mwe.getMessage());
            }
        }
        return bResult;
    }

    private boolean isContainer( Object oSelection ) {
        return (oSelection instanceof IContainer || oSelection instanceof IFolder);
    }

    private boolean isPackageInVirtualModel( Object oSelection ) {
        if (oSelection instanceof EObject) {
            MetamodelAspect mmAspect = AspectManager.getUmlDiagramAspect((EObject)oSelection);
            if (mmAspect != null && mmAspect instanceof UmlPackage) return true;
        }
        return false;
    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.dialogs.ElementTreeSelectionDialog#createTreeViewer(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected TreeViewer createTreeViewer( Composite parent ) {
        TreeViewer result = super.createTreeViewer(parent);

        // add a filter to remove closed projects
        result.addFilter(new ViewerFilter() {
            @Override
            public boolean select( Viewer viewer,
                                   Object parentElement,
                                   Object element ) {
                boolean result = true;

                if (element instanceof IProject) {
                    IProject project = (IProject)element;

                    if (!project.isOpen()) {
                        result = false;
                    } else {
                        try {
                            if (!project.hasNature(ModelerCore.NATURE_ID)) {
                                result = false;
                            } else {
                                String name = project.getName();
                                if (name != null && (org.teiid.designer.ui.PluginConstants.HIDDEN_PROJECTS.contains(name)) ) {
                                    return false;
                                }
                            }
                        } catch (CoreException theException) {
                            ModelerCore.Util.log(theException);
                            result = false;
                        }
                    }
                }

                return result;
            }
        });

        result.expandToLevel(2);
        return result;
    }

    /**
     * get the full file name, given a modelName string
     * 
     * @param modelName the model name
     * @return the full model name, including extension
     */
    private String getFileName( String sModelName ) {
        String sResult = sModelName.trim();

        if (!sResult.endsWith(FILE_EXT)) {
            sResult += FILE_EXT;
        }

        return sResult;
    }

    @Override
    public boolean close() {
        getTreeViewer().removeSelectionChangedListener(this);
        return super.close();
    }
}
