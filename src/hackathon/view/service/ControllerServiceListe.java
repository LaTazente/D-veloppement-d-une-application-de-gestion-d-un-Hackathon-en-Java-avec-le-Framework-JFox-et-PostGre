package hackathon.view.service;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import hackathon.data.Service;
import hackathon.view.EnumView;


public class ControllerServiceListe extends Controller {
	
	
	// Composants de la vue

	@FXML
	private ListView<Service>	lsvServices;
	@FXML
	private Button				btnModifier;
	@FXML
	private Button				btnSupprimer;


	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelService		modelService;
	
	
	// Initialisation du Controller

	@FXML
	private void initialize() {

		// ListView
		lsvServices.setItems( modelService.getListe() );
		UtilFX.setCellFactory( lsvServices, "nom" );
		
		// Configuraiton des boutons
		lsvServices.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldVal, newVal) -> {
					configurerBoutons();
		});
		configurerBoutons();
		
	}
	
	@Override
	public void refresh() {
		modelService.actualiserListe();
		UtilFX.selectRow( lsvServices, modelService.getSelection() );
		lsvServices.requestFocus();
	}

	
	// Actions
	
	@FXML
	private void doAjouter() {
		modelService.setSelection( null );
		managerGui.showView( EnumView.ServiceForm );
	}

	@FXML
	private void doModifier() {
		modelService.setSelection( lsvServices.getSelectionModel().getSelectedItem() );
		managerGui.showView( EnumView.ServiceForm );
	}

	@FXML
	private void doSupprimer() {
		if ( managerGui.showDialogConfirm( "Confirmez-vous la suppresion ?" ) ) {
			modelService.supprimer( lsvServices.getSelectionModel().getSelectedItem() );
			refresh();
		}
	}
	
	
	// Gestion des évènements

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( lsvServices.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					doModifier();
				}
			}
		}
	}

	
	// Méthodes auxiliaires
	
	private void configurerBoutons() {
		
    	if( lsvServices.getSelectionModel().getSelectedItems().isEmpty() ) {
			btnModifier.setDisable(true);
			btnSupprimer.setDisable(true);
		} else {
			btnModifier.setDisable(false);
			btnSupprimer.setDisable(false);
		}
	}

}
