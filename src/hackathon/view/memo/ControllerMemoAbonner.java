package hackathon.view.memo;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import hackathon.data.Compte;


public class ControllerMemoAbonner extends Controller {
	
	
	// Composants de la vue

	@FXML
	private ListView<Compte>	lsvComptes;
	@FXML
	private Button				btnAbonner;


	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelMemo			modelMemo;
	
	
	// Initialisation du Controller

	@FXML
	private void initialize() {

		// ListView
		lsvComptes.setItems( modelMemo.getComptesAbonnables() );
		UtilFX.setCellFactory( lsvComptes, "pseudo" );
		
		lsvComptes.getSelectionModel().setSelectionMode( SelectionMode.MULTIPLE );
		
		// Configuraiton des boutons
		lsvComptes.getSelectionModel().selectedItemProperty().addListener(
				(obs, oldVal, newVal) -> {
					configurerBoutons();
		});
		configurerBoutons();
		
	}
	
	@Override
	public void refresh() {
		modelMemo.actualiserComptesAbonnables();
		lsvComptes.requestFocus();
	}

	
	// Actions
	
	@FXML
	private void doAbonner() {
		var items = lsvComptes.getSelectionModel().getSelectedItems();
		modelMemo.getCourant().getAbonnes().addAll( items );
		managerGui.closeDialog();
	}

	@FXML
	private void doFermer() {
		managerGui.closeDialog();
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
					doAbonner();
				}
			}
		}
	}
	
	@FXML
	private void gererEchapSurListe( KeyEvent event ) {
		if ( event.getCharacter().charAt(0) == 27 )  {
			doFermer();
		}
	}

	
	// Méthodes auxiliaires
	
	private void configurerBoutons() {
		
    	if( lsvComptes.getSelectionModel().getSelectedItems().isEmpty() ) {
			btnAbonner.setDisable(true);
		} else {
			btnAbonner.setDisable(false);
		}
	}

}
