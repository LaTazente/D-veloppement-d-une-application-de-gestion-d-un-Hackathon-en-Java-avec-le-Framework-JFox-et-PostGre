package hackathon.view.gestionnaire_participants;

import javax.inject.Inject;

import hackathon.data.Categorie;
import hackathon.data.Participant;
import hackathon.data.Personne;
import hackathon.view.EnumView;
import hackathon.view.systeme.ControllerConnexion;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;

public class ControleurGestionParticipants extends Controller{
	

 
	@FXML
	private TableView<Participant> tbvParticipants;
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
    
    @FXML 
    private Button	btnModifier;
    
    @FXML 
    private Button	btnSupprimer;
    
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelParticpant		modelParticipant;
	
	@FXML
	private void initialize() {

		// TableView
		tbvParticipants.setItems( modelParticipant.getListe() );
		UtilFX.setValueFactory( tbcNom, "nom" );
		UtilFX.setValueFactory( tbcPrenom, "prenom" );
		UtilFX.setValueFactory( tbcRole, "role" );
		UtilFX.setValueFactory( tbcSexe, "sexe" );
		UtilFX.setValueFactory( tbcTelephone, "telephone" );
		UtilFX.setValueFactory( tbcMail, "email" );
		
		// Configuraiton des boutons
		tbvParticipants.getSelectionModel().getSelectedItems().addListener( 
		        (ListChangeListener<Participant>) (c) -> {
		        	configurerBoutons();
		});
    	configurerBoutons();
	}
	
	// Méthodes auxiliaires

	private void configurerBoutons() {
    	if( tbvParticipants.getSelectionModel().getSelectedItems().isEmpty() ) {
			btnModifier.setDisable(true);
			btnSupprimer.setDisable(true);
		} else {
			btnModifier.setDisable(false);
			btnSupprimer.setDisable(false);
		}
	}
//		
////	}
	
	@Override
	public void refresh() {
		modelParticipant.actualiserListe();
		UtilFX.selectRow( tbvParticipants, modelParticipant.getSelection() );
		tbvParticipants.requestFocus();
	}
	
	// Gestion des évènements

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( tbvParticipants.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					doModifier();
				}
			}
		}
	}
	
	@FXML
	private void doAjouter() {
		modelParticipant.setSelection(null);
		managerGui.showView( EnumView.ParticipantForm );
	}
	
	@FXML
	private void doModifier() {
		modelParticipant.setSelection( tbvParticipants.getSelectionModel().getSelectedItem() );
		managerGui.showView( EnumView.ParticipantFormModif);
	}
	
	@FXML
	private void doSupprimer() {
		if ( managerGui.showDialogConfirm("Êtes-vous sûr de vouloir supprimer ce participant ?" ) ) {
			modelParticipant.supprimer( tbvParticipants.getSelectionModel().getSelectedItem() );
			refresh();
		}
	}
	
	@FXML
	private void goBack() {
		System.out.println("on est ici");
		if(ControllerConnexion.getRoleUser().equals("GestionnaireParticipant")) 
			managerGui.showView(EnumView.AccueilGestionnaireParticipants);
		
		if(ControllerConnexion.getRoleUser().equals("Administrateur"))
			managerGui.showView(EnumView.AccueilAdmin);
	}
	
	

}
