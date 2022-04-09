package hackathon.view.compte;

import javax.inject.Inject;

import javafx.beans.property.BooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import jfox.javafx.util.BindingPairCheckList;
import jfox.javafx.util.ConverterInteger;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import hackathon.commun.Roles;
import hackathon.view.EnumView;


public class ControllerCompteForm extends Controller {

	
	// Composants de la vue
	
	@FXML
	private TextField			txfId;
	@FXML
	private TextField			txfPseudo;
	@FXML
	private TextField			txfMotDePasse;
	@FXML
	private TextField			txfEmail;
	@FXML
	private ListView<Pair<String, BooleanProperty>>	lsvRoles;

	
	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelCompte			modelCompte;

	
	// Initialisation du Controller
	
	@FXML
	private void initialize() {
		
		var courant = modelCompte.getCourant();

		// Champs simples
		bindBidirectional( txfId, courant.idProperty(), new ConverterInteger() );
		bindBidirectional( txfPseudo, courant.pseudoProperty() );
		bindBidirectional( txfMotDePasse, courant.motDePasseProperty() );
		bindBidirectional( txfEmail, courant.emailProperty() );
		
		// ListView
		var binding = new BindingPairCheckList<>( Roles.getRoles(), courant.getRoles() );
    	binding.configureListView( lsvRoles, item -> Roles.getLibelle( item.getKey() ) );
	}
	
	
	@Override
	public void refresh() {
		modelCompte.actualiserCourant();
	}
	
	
	// Actions
	
	@FXML
	private void doAnnuler() {
		managerGui.showView( EnumView.CompteListe );
	}
	
	@FXML
	private void doValider() {
		modelCompte.validerMiseAJour();
		managerGui.showView( EnumView.CompteListe );
	}

}
