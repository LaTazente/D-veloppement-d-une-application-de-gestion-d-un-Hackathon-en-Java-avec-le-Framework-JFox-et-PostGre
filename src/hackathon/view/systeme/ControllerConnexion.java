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
			modelConnexion.ouvrirSessionUtilisateur();
			System.out.println("Bonne connexion");
			Platform.runLater( () -> {
         			modelInfo.titreProperty().setValue( "Bienvenue" );
        			modelInfo.messageProperty().setValue( "Connexion réussie" );
        			managerGui.showView(EnumView.AccueilGestionnaireParticipants);
            }) ;
		} );
	}
	

}
