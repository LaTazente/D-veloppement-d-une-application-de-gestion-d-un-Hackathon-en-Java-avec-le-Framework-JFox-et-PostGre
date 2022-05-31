package hackathon.view.gestionnaire_participants;

import javax.inject.Inject;

import hackathon.data.Groupe;
import hackathon.data.Participant;
import hackathon.view.EnumView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;

public class ControleurModifierCandidat extends Controller{
	
	@FXML
	private TextField			txfNom;
	@FXML
	private TextField			txfPrenom;
	@FXML
	private TextField			txfMail;
	@FXML
	private TextField			txfTelephone;
    @FXML
    private ComboBox<String>	cmbSexe;
    @FXML
    private ComboBox<String>	cmbRole;
    @FXML
    private ComboBox<Groupe>	cmbGroupe;
    
    
	// Autres champs
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelParticpant		modelParticipant;
	
	public void initialize() {

		Participant courant = modelParticipant.getCourant();
		
		// Champs simples
		bindBidirectional( txfNom, courant.nomProperty() );
		bindBidirectional( txfPrenom, courant.prenomProperty() );
		bindBidirectional( txfMail, courant.emailProperty() );
		bindBidirectional( txfTelephone, courant.telephoneProperty() );
		// Combo box
		ObservableList<String> liste_role =  FXCollections.observableArrayList();
		liste_role.add("membre");
		liste_role.add("chef");
		cmbRole.setItems(  liste_role);
		bindBidirectional( cmbRole, courant.roleProperty() );
		ObservableList<String> liste_sexe =  FXCollections.observableArrayList();
		liste_sexe.add("F");
		liste_sexe.add("M");
		cmbSexe.setItems(  liste_sexe);
		bindBidirectional( cmbSexe, courant.sexeProperty() );
		cmbGroupe.setItems(modelParticipant.getListeGroupe());
		UtilFX.setCellFactory(cmbGroupe, "id_groupe");
		bindBidirectional(cmbGroupe, courant.idGroupeProperty());
		
	}
	
	
	@Override
	public void refresh() {
		modelParticipant.listerGroupe();
		modelParticipant.actualiserListe();
		modelParticipant.actualiserCourant();
		System.out.println(modelParticipant.getSelection());
	}
	
	// Actions
	
	@FXML
	private void doValider() {
		modelParticipant.validerMiseAJour();
		managerGui.showView( EnumView.listecandidats );
	}
	
	@FXML
	private void doAnnuler() {
		managerGui.showView( EnumView.listecandidats );
	}

}
