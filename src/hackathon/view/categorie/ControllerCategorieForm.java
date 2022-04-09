package hackathon.view.categorie;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import jfox.javafx.util.ConverterInteger;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import hackathon.data.Categorie;
import hackathon.view.EnumView;


public class ControllerCategorieForm extends Controller {

	
	// Composants de la vue
	
	@FXML
	private TextField		txfId;
	@FXML
	private TextField		txfLibelle;
	@FXML
	private DatePicker		dtpDebut;


	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelCategorie		modelCategorie;


	// Initialisation du Controller

	@FXML
	private void initialize() {
		
		Categorie courant = modelCategorie.getCourant();

		// Data binding
		bindBidirectional( txfId,  courant.idProperty(), new ConverterInteger() );
		bindBidirectional( txfLibelle, courant.libelleProperty()  );
		bindBidirectional( dtpDebut, courant.debutProperty() );
	}
	
	
	@Override
	public void refresh() {
		modelCategorie.actualiserCourant();
	}
	
	
	// Actions
	
	@FXML
	private void doAnnuler() {
		managerGui.showView( EnumView.CategorieListe );
	}
	
	@FXML
	private void doValider() {
		modelCategorie.validerMiseAJour();
		managerGui.showView( EnumView.CategorieListe );
	}

}
