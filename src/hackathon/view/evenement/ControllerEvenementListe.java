package hackathon.view.evenement;

import java.time.LocalDate;

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
import hackathon.data.Evenement;
import hackathon.view.EnumView;


public class ControllerEvenementListe extends Controller {
	
	
	// Composants de la vue

	@FXML
	private TableView<Evenement>	lsvEvenements;
	@FXML
	private Button				btnModifier;
	@FXML
	private Button				btnSupprimer;

	// Column
	@FXML
	private TableColumn<Evenement, String> columnNom;
	@FXML
	private TableColumn<Evenement, String> columnCode;
	@FXML
	private TableColumn<Evenement, String> columnTheme;
	@FXML
	private TableColumn<Evenement, String> columnLieu;
	@FXML
	private TableColumn<Evenement, LocalDate> columnDebut;
	@FXML
	private TableColumn<Evenement, LocalDate> columnFin;
	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelEvenement			modelEvenement;
	
	
	// Initialisation du Controller

	@FXML
	private void initialize() {
		
		// ListView
		lsvEvenements.setItems( modelEvenement.getListe() );
		// UtilFX.setCellFactory( lsvEvenements, "nom" );
		// UtilFX.setValueFactory(columnNom, modelEvenement.getCourant().getNom());
		UtilFX.setValueFactory(columnNom, "nom");
		UtilFX.setValueFactory(columnCode, "code");
		UtilFX.setValueFactory(columnTheme, "theme");
		UtilFX.setValueFactory(columnLieu, "lieu");
		UtilFX.setValueFactory(columnDebut, "dateDebut");
		UtilFX.setValueFactory(columnFin, "dateFin");
		
		// Comportement si modificaiton de la séleciton
		lsvEvenements.getSelectionModel().getSelectedItems().addListener( 
				(ListChangeListener<Evenement>) (c) -> {
					 configurerBoutons();					
		});
		configurerBoutons();
	}
	
	@Override
	public void refresh() {
		modelEvenement.actualiserListe();
		UtilFX.selectRow(lsvEvenements, modelEvenement.getSelection() );
		lsvEvenements.requestFocus();
	}

	
	// Actions

	@FXML
	private void doAjouter() {
		modelEvenement.setSelection( null );
		managerGui.showView( EnumView.EvenementForm );
	}

	@FXML
	private void doModifier() {
		
		modelEvenement.setSelection( lsvEvenements.getSelectionModel().getSelectedItem() );
		managerGui.showView( EnumView.EvenementForm );
	}

	@FXML
	private void doSupprimer() {
		if ( managerGui.showDialogConfirm( "Confirmez-vous la suppresion ?" ) ) {
			modelEvenement.supprimer( lsvEvenements.getSelectionModel().getSelectedItem() );
			refresh();
		}
	}
	
	@FXML
	private void goBack() {
		managerGui.showView(EnumView.AccueilAdmin);
	}
	
	// Gestion des évènements

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( lsvEvenements.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					modelEvenement.setSelection(lsvEvenements.getSelectionModel().getSelectedItem());
					modelEvenement.actualiserCourant();
					
					modelEvenement.actualiserStatistiques(lsvEvenements.getSelectionModel().getSelectedItem().getCode());
					managerGui.showView(EnumView.EvenementStatistique);
				}
			}
		}
	}


	// Méthodes auxiliaires
	
	private void configurerBoutons() {
    	if( lsvEvenements.getSelectionModel().getSelectedItems().isEmpty() ) {
			btnModifier.setDisable(true);
			btnSupprimer.setDisable(true);
		} else {
			btnModifier.setDisable(false);
			btnSupprimer.setDisable(false);
		}
	}

}
