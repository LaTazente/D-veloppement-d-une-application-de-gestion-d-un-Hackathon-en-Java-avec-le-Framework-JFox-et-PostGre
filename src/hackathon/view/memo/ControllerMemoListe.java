package hackathon.view.memo;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import hackathon.data.Memo;
import hackathon.view.EnumView;


public class ControllerMemoListe extends Controller {
	
	
	// Composants de la vue

	@FXML
	private ListView<Memo>	lsvMemos;
	@FXML
	private Button				btnModifier;
	@FXML
	private Button				btnSupprimer;


	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelMemo		modelMemo;
	
	
	// Initialisation du Controller

	@FXML
	private void initialize() {

		// ListView
		lsvMemos.setItems( modelMemo.getListe() );
		UtilFX.setCellFactory( lsvMemos, "titre" );
		
		// Configuraiton des boutons
		lsvMemos.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldVal, newVal) -> {
					configurerBoutons();
		});
		configurerBoutons();
		
	}
	
	@Override
	public void refresh() {
		modelMemo.actualiserListe();
		UtilFX.selectRow( lsvMemos, modelMemo.getSelection() );
		lsvMemos.requestFocus();
	}

	
	// Actions
	
	@FXML
	private void doAjouter() {
		modelMemo.setSelection( null );
		managerGui.showView( EnumView.MemoForm );
	}

	@FXML
	private void doModifier() {
		modelMemo.setSelection( lsvMemos.getSelectionModel().getSelectedItem() );
		managerGui.showView( EnumView.MemoForm );
	}

	@FXML
	private void doSupprimer() {
		if ( managerGui.showDialogConfirm( "Confirmez-vous la suppresion ?" ) ) {
			modelMemo.supprimer( lsvMemos.getSelectionModel().getSelectedItem() );
			refresh();
		}
	}
	
	
	// Gestion des évènements

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( lsvMemos.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					doModifier();
				}
			}
		}
	}

	
	// Méthodes auxiliaires
	
	private void configurerBoutons() {
		
    	if( lsvMemos.getSelectionModel().getSelectedItems().isEmpty() ) {
			btnModifier.setDisable(true);
			btnSupprimer.setDisable(true);
		} else {
			btnModifier.setDisable(false);
			btnSupprimer.setDisable(false);
		}
	}

}
