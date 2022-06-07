package hackathon.view.evenement;

import java.time.LocalDate;

import javax.inject.Inject;

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
import jfox.javafx.view.IEnumView;
import jfox.javafx.view.IManagerGui;
import hackathon.data.Evenement;
import hackathon.data.Groupe;
import hackathon.data.Partenaire;
import hackathon.data.Participant;
import hackathon.view.EnumView;
import hackathon.view.gestionnaire_participants.ModelGroupe;
import hackathon.view.gestionnaire_participants.ModelParticpant;
import hackathon.view.partenaire.ModelPartenaire;


public class ControllerParticipantEquipe extends Controller {
	
	
	// Composants de la vue

    @FXML
    private TableView<Participant> tbvParticipant;

    @FXML
    private TableColumn<Participant , String> tbcNom;

    @FXML
    private TableColumn<Participant , String> tbcPrenom;

    @FXML
    private TableColumn<Participant , String> tbcSexe;

    @FXML
    private TableColumn<Participant , String> tbcTelephone;

    @FXML
    private TableColumn<Participant , String> tbcRole;

    @FXML
    private TableColumn<Participant , String> tbcMail;
	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelEvenement			modelEvenement;
	@Inject
	private ModelGroupe			modelGroupe;
	@Inject
	private ModelPartenaire			modelPartenaire;
	
	// Initialisation du Controller

	@FXML
	private void initialize() {
		
		// TableView
		
		tbvParticipant.setItems( modelGroupe.getStatistique().getParticipants());
		
		
		UtilFX.setValueFactory( tbcNom, "nom" );
		UtilFX.setValueFactory( tbcPrenom, "prenom" );
		UtilFX.setValueFactory( tbcRole, "role" );
		UtilFX.setValueFactory( tbcSexe, "sexe" );
		UtilFX.setValueFactory( tbcTelephone, "telephone" );
		UtilFX.setValueFactory( tbcMail, "email" );
		
		
		// Configuraiton des boutons
//		tbvPartenaire.getSelectionModel().getSelectedItems().addListener( 
//		        (ListChangeListener<Partenaire>) (c) -> {
//		        	configurerBoutons();
//		});
//    	configurerBoutons();
//		
//		// Comportement si modificaiton de la séleciton
//		tbvGroupes.getSelectionModel().getSelectedItems().addListener( 
//				(ListChangeListener<Groupe>) (c) -> {
//					 configurerBoutons();					
//		});
//		configurerBoutons();
	}
	
	@Override
	public void refresh() {
		
		var courant = modelEvenement.getCourant();
		
		modelEvenement.actualiserListe();
		System.out.println("courant "+courant);
		//modelGroupe.actualiserListe();
		//modelPartenaire.actualiserListe();
		// UtilFX.selectRow(lsvEvenements, modelEvenement.getSelection() );
		// lsvEvenements.requestFocus();
		
		
		//Ajout des valeurs
		
		tbvParticipant.setItems( modelGroupe.getStatistique().getParticipants());
		
	}

	
	// Actions

//	@FXML
//	private void doAjouter() {
//		modelEvenement.setSelection( null );
//		managerGui.showView( EnumView.EvenementForm );
//	}
//
//	@FXML
//	private void doModifier() {
//		
//		modelEvenement.setSelection( lsvEvenements.getSelectionModel().getSelectedItem() );
//		managerGui.showView( EnumView.EvenementForm );
//	}
//
//	@FXML
//	private void doSupprimer() {
//		if ( managerGui.showDialogConfirm( "Confirmez-vous la suppresion ?" ) ) {
//			modelEvenement.supprimer( lsvEvenements.getSelectionModel().getSelectedItem() );
//			refresh();
//		}
//	}
	
	
	
	// Gestion des évènements

	// Clic sur la liste
//	@FXML
//	private void gererClicSurListe( MouseEvent event ) {
//		if (event.getButton().equals(MouseButton.PRIMARY)) {
//			if (event.getClickCount() == 2) {
//				if ( lsvEvenements.getSelectionModel().getSelectedIndex() == -1 ) {
//					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
//				} else {
//					modelEvenement.setSelection(lsvEvenements.getSelectionModel().getSelectedItem());
//					modelEvenement.actualiserCourant();
//					managerGui.showView(EnumView.choisirActivite);
//				}
//			}
//		}
//	}
	
//	@FXML
//	private void gererClicSurListe( MouseEvent event ) {
//		if (event.getButton().equals(MouseButton.PRIMARY)) {
//			if (event.getClickCount() == 2) {
//				if ( tbvGroupes.getSelectionModel().getSelectedIndex() == -1 ) {
//					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
//				} else {
//					//modelEvenement.setSelection(lsvEvenement.getSelectionModel().getSelectedItem());
//					modelEvenement.actualiserCourant();
//					//managerGui.showView(EnumView.EvenementStatistique);
//					managerGui.showDialog(EnumView.GestionEquipes );
//					// managerGui.showDialog(EnumView.EvenementStatistique);
//				}
//			}
//		}
//	}

	@FXML
	private void goBack() {
	managerGui.showView(EnumView.GestionEvenements);
	}
	// Méthodes auxiliaires
	@FXML
	public void fermer() {
		managerGui.closeDialog();
	}
}
