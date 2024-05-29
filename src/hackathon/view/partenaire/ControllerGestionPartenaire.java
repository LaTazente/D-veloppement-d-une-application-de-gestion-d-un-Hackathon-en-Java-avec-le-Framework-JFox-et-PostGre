package hackathon.view.partenaire;

import javax.inject.Inject;

import hackathon.data.Partenaire;
import hackathon.view.EnumView;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;

public class ControllerGestionPartenaire extends Controller {

    @FXML
    private TextField tfSearch;
    
    @FXML
    private TableView<Partenaire> tbvPartenaire;

    @FXML
    private TableColumn<Partenaire, String> colNom;

    @FXML
    private TableColumn<Partenaire, String> colDescription;
    @FXML
    private Button btnModifierP;

    @FXML
    private Button btnSupprimerP;
    
  	@Inject
  	private IManagerGui			managerGui;
  	@Inject
  	private ModelPartenaire		modelPartenaire;
  	/*@Inject
  	private ModelEvenement		modelEvenement;*/
  	
  	@FXML
	private void initialize() {

		// TableView
		tbvPartenaire.setItems( modelPartenaire.getListe() );
		UtilFX.setValueFactory( colNom, "nom" );
		UtilFX.setValueFactory( colDescription, "description" );
		
		
		// Configuraiton des boutons
		tbvPartenaire.getSelectionModel().getSelectedItems().addListener( 
		        (ListChangeListener<Partenaire>) (c) -> {
		        	configurerBoutons();
		});
    	configurerBoutons();
	}
	
	// Méthodes auxiliaires

	private void configurerBoutons() {
    	if( tbvPartenaire.getSelectionModel().getSelectedItems().isEmpty() ) {
			btnModifierP.setDisable(true);
			btnSupprimerP.setDisable(true);
		} else {
			btnModifierP.setDisable(false);
			btnSupprimerP.setDisable(false);
		}
	}
//		
////	}
	
	public void refresh() {
		modelPartenaire.actualiserListe();
		UtilFX.selectRow( tbvPartenaire, modelPartenaire.getSelection() );
		tbvPartenaire.requestFocus();
	}
	
	// Gestion des évènements

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( tbvPartenaire.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					actionModifierP();
				}
			}
		}
	}
	
	@FXML
	private void actionAjouterP() {
		modelPartenaire.setSelection(null);
		managerGui.showView( EnumView.PartenaireForm );
	}
	
	@FXML
	private void actionModifierP() {
		modelPartenaire.setSelection( tbvPartenaire.getSelectionModel().getSelectedItem() );
		managerGui.showView( EnumView.PartenaireFormModif);
	}
	
	@FXML
	private void actionSupprimerP() {
		if ( managerGui.showDialogConfirm("Êtes-vous sûr de vouloir supprimer ce partenaire ?" ) ) {
			modelPartenaire.supprimer( tbvPartenaire.getSelectionModel().getSelectedItem() );
			refresh();
		}
	}
	
	@FXML
	private void goBack() {
		managerGui.showView( EnumView.AccueilAdmin);
	}
	

}
