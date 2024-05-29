package hackathon.view.systeme;

import javax.inject.Inject;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import hackathon.data.Evenement;
import hackathon.data.Utilisateur;
import hackathon.view.EnumView;
import hackathon.view.evenement.ModelEvenement;


public class ControllerConnexion extends Controller {
	

	// Composants de la vue
	
	@FXML
	private TextField		txfPseudo;
	@FXML
	private PasswordField	pwfMotDePasse;
	@FXML
	private ComboBox<Evenement>	cmbEvenement;
	
	private static String roleUser;
	
	// Autres champs
	
	@Inject
	private IManagerGui		managerGui;
	@Inject
	private ModelConnexion	modelConnexion;
	@Inject
	private ModelInfo		modelInfo;
	@Inject
	private ModelEvenement		modelEvenement;
	
	// Initialisation du Controller
	
	@FXML
	private void initialize() {

		Utilisateur courant = modelConnexion.getCourant();
		
		// Data binding
		bindBidirectional( txfPseudo, courant.id_userProperty() );
		bindBidirectional( pwfMotDePasse, courant.mdpProperty() );

		cmbEvenement.setItems( modelEvenement.getListe() );
		bindBidirectional( cmbEvenement, courant.codeProperty() );
		UtilFX.setCellFactory( cmbEvenement, item -> item.getCode() );
	}
	
	
	@Override
	public void refresh() {
		// Ferem la session si elle est ouverte
		modelEvenement.actualiserListe();
		if ( modelConnexion.getCompteActif() != null ) {
			modelConnexion.fermerSessionUtilisateur();
		}
	}
	

	// Actions
	
	@FXML
	private void doConnexion() {
		managerGui.execTask( () -> {
			String role = modelConnexion.ouvrirSessionUtilisateur();
			System.out.println("Bonne connexion");
			System.out.println(role);
			Platform.runLater( () -> {
         			modelInfo.titreProperty().setValue( "Bienvenue" );
        			modelInfo.messageProperty().setValue( "Connexion réussie" );
        			setRoleUser(role);
        			if(role.equals("Administrateur")) {
        				managerGui.showView(EnumView.AccueilAdmin);
        			}else if(role.equals("GestionnaireParticipants")) {
        				managerGui.showView(EnumView.AccueilGestionnaireParticipants);
        			}else if(role.equals("GestionnairePartenaires")) {
        				managerGui.showView(EnumView.AccueilGestionnairePartenaires);
        			}else if(role.equals("Jury")) {
        				managerGui.showView(EnumView.AccueilMembreJury);
        			}
        			//
            }) ;
		} );
	}


	public static String getRoleUser() {
		return roleUser;
	}


	public static void setRoleUser(String roleUser) {
		ControllerConnexion.roleUser = roleUser;
	}
	

}
