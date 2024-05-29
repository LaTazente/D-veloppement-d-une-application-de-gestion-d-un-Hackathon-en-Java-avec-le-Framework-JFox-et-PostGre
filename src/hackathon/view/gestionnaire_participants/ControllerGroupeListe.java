package hackathon.view.gestionnaire_participants;

import javax.inject.Inject;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IEnumView;
import jfox.javafx.view.IManagerGui;
import hackathon.data.Groupe;
import hackathon.view.EnumView;


public class ControllerGroupeListe extends Controller {
	
	
	// Composants de la vue

	@FXML
	private TableView<Groupe>	lsvGroupes;
	@FXML
	private Button				btnModifier;
	@FXML
	private Button				btnSupprimer;

	// Column
	@FXML
	private TableColumn<Groupe, String> columnNom;
	@FXML
	private TableColumn<Groupe, String> columnIdGroupe;
	@FXML
	private TableColumn<Groupe, String> columnEvenement;
	@FXML
	private TableColumn<Groupe, String> columnNbreMembres;
	
	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelGroupe			modelGroupe;
	
	
	// Initialisation du Controller

	@FXML
	private void initialize() {
		
		// ListView
		lsvGroupes.setItems( modelGroupe.getListe() );
		// UtilFX.setCellFactory( lsvGroupes, "nom" );
		// UtilFX.setValueFactory(columnNom, modelGroupe.getCourant().getNom());
		UtilFX.setValueFactory(columnNom, "nom");
		UtilFX.setValueFactory(columnIdGroupe, "id_groupe");
		UtilFX.setValueFactory(columnEvenement, "code" );
		UtilFX.setValueFactory(columnNbreMembres, "nbre_menbres");
		
		// Comportement si modificaiton de la séleciton
		lsvGroupes.getSelectionModel().getSelectedItems().addListener( 
				(ListChangeListener<Groupe>) (c) -> {
					 configurerBoutons();					
		});
		configurerBoutons();
	}
	
	@Override
	public void refresh() {
		modelGroupe.actualiserListe();
		UtilFX.selectRow(lsvGroupes, modelGroupe.getSelection() );
		lsvGroupes.requestFocus();
	}

	
	// Actions

	@FXML
	private void doAjouter() {
		modelGroupe.setSelection( null );
		managerGui.showView( EnumView.GroupeForm );
	}

	@FXML
	private void doModifier() {
		
		modelGroupe.setSelection( lsvGroupes.getSelectionModel().getSelectedItem() );
		managerGui.showView( EnumView.GroupeForm );
	}

	@FXML
	private void doSupprimer() {
		if ( managerGui.showDialogConfirm( "Confirmez-vous la suppresion ?" ) ) {
			modelGroupe.supprimer( lsvGroupes.getSelectionModel().getSelectedItem() );
			refresh();
		}
	}
	
	@FXML
	private void goBack() {
//		managerGui.showView(EnumView.AccueilGestionnaireParticipants);
	}
	
	// Gestion des évènements

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( lsvGroupes.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					modelGroupe.setSelection(lsvGroupes.getSelectionModel().getSelectedItem());
					modelGroupe.actualiserCourant();
					managerGui.showView(EnumView.choisirActivite);
				}
			}
		}
	}


	// Méthodes auxiliaires
	
	private void configurerBoutons() {
    	if( lsvGroupes.getSelectionModel().getSelectedItems().isEmpty() ) {
			btnModifier.setDisable(true);
			btnSupprimer.setDisable(true);
		} else {
			btnModifier.setDisable(false);
			btnSupprimer.setDisable(false);
		}
	}
	@FXML
	public void fermer() {
		managerGui.closeDialog();
	}

}
