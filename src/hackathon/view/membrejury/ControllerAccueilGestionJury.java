package hackathon.view.membrejury;

import javax.inject.Inject;

import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import jfox.javafx.util.BindingPairCheckList;
import jfox.javafx.util.ConverterInteger;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import hackathon.commun.Roles;
import hackathon.dao.DaoActivite;
import hackathon.dao.DaoEffectuer;
import hackathon.dao.DaoGroupe;
import hackathon.dao.DaoInscrire;
import hackathon.dao.DaoPartenaire;
import hackathon.dao.DaoParticipant;
import hackathon.data.Jury;
import hackathon.data.Personne;
import hackathon.view.EnumView;
import hackathon.view.gestionnaire_participants.ModelGroupe;
import hackathon.view.jury.ModelJury;
import hackathon.view.personne.ModelPersonne;


public class ControllerAccueilGestionJury extends Controller {

	
	// Composants de la vue
	
	
	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelGroupe			modelGroupe;

	 @Inject
	 private DaoActivite daoActivite;
	 @Inject
	 private DaoInscrire daoInscrire;
	 @Inject
	 private DaoGroupe daoGroupe;
	 @Inject
	 private DaoParticipant daoParticipant;
	 @Inject
	 private DaoPartenaire daoPartenaire;
	// Initialisation du Controller
	
	@FXML
	private void initialize() {
		
		var courant = modelGroupe.getCourant();

		// Champs simples
		// bindBidirectional( txfId, courant.idProperty(), new ConverterInteger() );
		
	}
	
	
	@Override
	public void refresh() {
		
		
	}
	
	
	// Actions
	
	@FXML
	private void doAnnuler() {
		managerGui.showView( EnumView.GestionEquipes );
	}
	
	@FXML
	private void doValider() {
		//System.out.println("test");
		
		modelGroupe.validerMiseAJour();
		managerGui.showView( EnumView.GestionEquipes );
	}
	
	
	 @FXML
    void goGestionCandidats(ActionEvent event) {
		 managerGui.showView(EnumView.listecandidats);
    }

    @FXML
    void goStatistiques(ActionEvent event) {
    	managerGui.showView(EnumView.Statistiques);
    }
    
    @FXML
    void goAccueil(ActionEvent event) {
		 managerGui.showView(EnumView.UserForm);
    }

    
    @FXML
    void goGestionEvenements(ActionEvent event) {
		 managerGui.showView(EnumView.GestionEvenements);
    }
    

    @FXML
    void goGestionJurys(ActionEvent event) {
    	managerGui.showView(EnumView.GestionsdesMembreJury);
    }

    @FXML
    void goPartenaires(ActionEvent event) {
    	managerGui.showView(EnumView.GestionPartenaires);
    }
    
    @FXML
    void goActivites(ActionEvent event) {
    	managerGui.showView(EnumView.AjouterActivite);
    }
    
    @FXML
    void goNotation(ActionEvent event) {
    	managerGui.showView(EnumView.gestionNotes);
    }
}
