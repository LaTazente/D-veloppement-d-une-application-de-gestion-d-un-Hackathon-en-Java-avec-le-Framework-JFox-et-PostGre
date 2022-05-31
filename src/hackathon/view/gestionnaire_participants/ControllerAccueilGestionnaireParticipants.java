package hackathon.view.gestionnaire_participants;

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
import hackathon.data.Jury;
import hackathon.data.Personne;
import hackathon.view.EnumView;
import hackathon.view.jury.ModelJury;
import hackathon.view.personne.ModelPersonne;


public class ControllerAccueilGestionnaireParticipants extends Controller {

	
	// Composants de la vue
	
	
	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelGroupe			modelGroupe;
	@Inject
	private ModelJury		modelJury;

	
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
    void goGestionEquipes(ActionEvent event) {
    	managerGui.showView(EnumView.GestionEquipes);
    }
    
    @FXML
    void goAccueil(ActionEvent event) {
		 managerGui.showView(EnumView.UserForm);
    }

}
