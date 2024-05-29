package hackathon.view.categorie;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import hackathon.data.Categorie;
import hackathon.view.EnumView;


public class ControllerCategorieListe extends Controller {
	
	
	// Composants de la vue

	@FXML
	private ListView<Categorie>	lsvCategories;
	@FXML
	private Button				btnModifier;
	@FXML
	private Button				btnSupprimer;


	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelCategorie		modelCategorie;
	
	
	// Initialisation du Controller

	@FXML
	private void initialize() {

		// ListView
		lsvCategories.setItems( modelCategorie.getListe() );
		UtilFX.setCellFactory( lsvCategories, "libelle" );
		
		// Configuraiton des boutons
		lsvCategories.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldVal, newVal) -> {
					configurerBoutons();
		});
		configurerBoutons();
		
	}
	
	@Override
	public void refresh() {
		modelCategorie.actualiserListe();
		UtilFX.selectRow( lsvCategories, modelCategorie.getSelection() );
		lsvCategories.requestFocus();
	}

	
	// Actions
	
	@FXML
	private void doAjouter() {
		modelCategorie.setSelection( null );
		managerGui.showView( EnumView.CategorieForm );
	}

	@FXML
	private void doModifier() {
		modelCategorie.setSelection( lsvCategories.getSelectionModel().getSelectedItem() );
		managerGui.showView( EnumView.CategorieForm );
	}

	@FXML
	private void doSupprimer() {
		if ( managerGui.showDialogConfirm( "Confirmez-vous la suppresion ?" ) ) {
			modelCategorie.supprimer( lsvCategories.getSelectionModel().getSelectedItem() );
			refresh();
		}
	}
	
	
	// Gestion des évènements

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( lsvCategories.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					doModifier();
				}
			}
		}
	}

	
	// Méthodes auxiliaires
	
	private void configurerBoutons() {
		
    	if( lsvCategories.getSelectionModel().getSelectedItems().isEmpty() ) {
			btnModifier.setDisable(true);
			btnSupprimer.setDisable(true);
		} else {
			btnModifier.setDisable(false);
			btnSupprimer.setDisable(false);
		}
	}

}
