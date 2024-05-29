package hackathon.view.utilisateur;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import jfox.javafx.util.BindingPairCheckList;
import jfox.javafx.util.ConverterInteger;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import hackathon.commun.Roles;
import hackathon.data.Utilisateur;
import hackathon.data.Evenement;
import hackathon.data.Jury;
import hackathon.data.Personne;
import hackathon.view.EnumView;
import hackathon.view.evenement.ModelEvenement;
import hackathon.view.systeme.ControllerConnexion;


public class ControllerUserForm extends Controller {

	
	// Composants de la vue
	 @FXML
    private TextField txfNom;

    @FXML
    private TextField txfPrenom;

    @FXML
    private TextField txfTelephone;

    @FXML
    private RadioButton rbOrganisateur;

    @FXML
    private RadioButton rbJury;

    @FXML
    private RadioButton rbAdmin;

    @FXML
    private RadioButton rbGestionnairePartenaires;
    
    @FXML
    private RadioButton rbGestionnaireParticipants;

    @FXML
    private TextField txfProfession;

    @FXML
    private TextField txfPseudo;
    
    @FXML
    private TextField txfMdp;

    @FXML
    private TextField txfConfirmMdp;
    
    @FXML
	private ComboBox<Evenement>	cmbEvenement;
	    
	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	
	@Inject
	private ModelUtilisateur		modelUtilisateur;
	
	@Inject
	private ModelEvenement		modelEvenement;
	
	// Initialisation du Controller
	
	@FXML
	private void initialize() {
		
		var courant = modelUtilisateur.getCourant();

		// Champs simples
		// bindBidirectional( txfId, courant.idProperty(), new ConverterInteger() );
		bindBidirectional( txfNom, courant.nomProperty() );
		bindBidirectional( txfMdp, courant.mdpProperty());
		bindBidirectional( txfPrenom, courant.prenomProperty() );
		bindBidirectional( txfProfession, courant.professionProperty() );
		bindBidirectional( txfTelephone, courant.telephoneProperty());
		bindBidirectional( txfPseudo, courant.id_userProperty() );
		bindBidirectional( txfConfirmMdp, courant.confirmMdpProperty() );
		
	//	System.out.println("ici test "+ modelJury.getListe());
		cmbEvenement.setItems( modelEvenement.getListe() );
		bindBidirectional( cmbEvenement, courant.codeProperty() );
		UtilFX.setCellFactory( cmbEvenement, item -> item.getCode() );
//		
	}
	
	
	@Override
	public void refresh() {
		modelUtilisateur.actualiserCourant();
		modelEvenement.actualiserListe();
		System.out.println("ici ");
		if(modelUtilisateur.getCourant().getId_user() != null) {
			System.out.println("ici "+  modelUtilisateur.getCourant().getId_user());
			//txfIdEquipe.setDisable(true);
			//ModelUtilisateur.setDisabled(true);
		}
	}
	
	
	// Actions
	
	@FXML
	private void doAnnuler() {
		managerGui.showView( EnumView.GestionEquipes );
	}
	
	@FXML
	private void doValider() {
		List<String> tab = new ArrayList<String>();
		System.out.println("test");
//		
//		if(rbAdmin.isSelected()) {
//			System.out.println("selected");
//			tab.add("Admininstrateur");
//		}
		
		if(rbGestionnairePartenaires.isSelected()) {
			System.out.println("selected");
			tab.add("GestionnairePartenaires");
		}
		if(rbGestionnaireParticipants.isSelected()) {
			System.out.println("selected");
			tab.add("GestionnaireParticipants");
		}
		
		if(rbJury.isSelected()) {
			System.out.println("selected");
			tab.add("Jury");
		}
		
		if(rbOrganisateur.isSelected()) {
			System.out.println("selected");
			tab.add("Organisateur");
		}
		modelUtilisateur.validerMiseAJour(tab);
		managerGui.showView( EnumView.ConnexionVrai );
	}
	
	@FXML
	private void goBack() {
		managerGui.showView(EnumView.AccueilAdmin);
	}

}
