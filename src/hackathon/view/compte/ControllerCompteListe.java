package hackathon.view.compte;

import javax.inject.Inject;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import hackathon.data.Compte;
import hackathon.view.EnumView;


public class ControllerCompteListe extends Controller {
	
	
	// Composants de la vue

	@FXML
	private ListView<Compte>	lsvComptes;
	@FXML
	private Button				btnModifier;
	@FXML
	private Button				btnSupprimer;

	
	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelCompte			modelCompte;
	
	
	// Initialisation du Controller

	@FXML
	private void initialize() {
		
		// ListView
		lsvComptes.setItems( modelCompte.getListe() );
		UtilFX.setCellFactory( lsvComptes, "pseudo" );

		// Comportement si modificaiton de la séleciton
		lsvComptes.getSelectionModel().getSelectedItems().addListener( 
				(ListChangeListener<Compte>) (c) -> {
					 configurerBoutons();					
		});
		configurerBoutons();
	}
	
	@Override
	public void refresh() {
		modelCompte.actualiserListe();
		UtilFX.selectRow(lsvComptes, modelCompte.getSelection() );
		lsvComptes.requestFocus();
	}

	
	// Actions

	@FXML
	private void doAjouter() {
		modelCompte.setSelection( null );
		managerGui.showView( EnumView.CompteForm );
	}

	@FXML
	private void doModifier() {
		modelCompte.setSelection( lsvComptes.getSelectionModel().getSelectedItem() );
		managerGui.showView( EnumView.CompteForm );
	}

	@FXML
	private void doSupprimer() {
		if ( managerGui.showDialogConfirm( "Confirmez-vous la suppresion ?" ) ) {
			modelCompte.supprimer( lsvComptes.getSelectionModel().getSelectedItem() );
			refresh();
		}
	}
	
	
	// Gestion des évènements

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( lsvComptes.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					doModifier();
				}
			}
		}
	}


	// Méthodes auxiliaires
	
	private void configurerBoutons() {
    	if( lsvComptes.getSelectionModel().getSelectedItems().isEmpty() ) {
			btnModifier.setDisable(true);
			btnSupprimer.setDisable(true);
		} else {
			btnModifier.setDisable(false);
			btnSupprimer.setDisable(false);
		}
	}

}
