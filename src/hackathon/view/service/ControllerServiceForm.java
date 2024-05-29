package hackathon.view.service;

import javax.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import jfox.javafx.util.ConverterInteger;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import hackathon.data.Personne;
import hackathon.data.Service;
import hackathon.view.EnumView;
import hackathon.view.personne.ModelPersonne;


public class ControllerServiceForm extends Controller {

	
	// Composants de la vue
	
	@FXML
	private TextField		txfId;
	@FXML
	private TextField		txfNom;
	@FXML
	private TextField		txfAnneeCreation;
	@FXML
	private CheckBox		ckbSiege;
	@FXML
	private ComboBox<Personne>	cmbPersonne;


	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelService		modelService;
	@Inject
	private ModelPersonne		modelPersonne;


	// Initialisation du Controller

	@FXML
	private void initialize() {
		
		Service courant = modelService.getCourant();

		// Data binding
		bindBidirectional( txfId,  courant.idProperty(), new ConverterInteger() );
		bindBidirectional( txfNom, courant.nomProperty()  );
		bindBidirectional( txfAnneeCreation, courant.anneeCreationProperty(), new ConverterInteger() );
		bindBidirectional( ckbSiege, courant.flagSiegeProperty() );
		
		cmbPersonne.setItems( modelPersonne.getListe() );
		bindBidirectional( cmbPersonne, courant.categorieProperty() );
		UtilFX.setCellFactory( cmbPersonne, item -> item.getNom() + " " +item.getPrenom() );
	}
	
	
	@Override
	public void refresh() {
		modelPersonne.actualiserListe();
		modelService.actualiserCourant();
	}
	
	
	// Actions
	
	@FXML
	private void doAnnuler() {
		managerGui.showView( EnumView.ServiceListe );
	}
	
	@FXML
	private void doValider() {
		modelService.validerMiseAJour();
		managerGui.showView( EnumView.ServiceListe );
	}

	@FXML
	private void doPersonneSupprimer() {
		cmbPersonne.setValue( null );
	}
}
