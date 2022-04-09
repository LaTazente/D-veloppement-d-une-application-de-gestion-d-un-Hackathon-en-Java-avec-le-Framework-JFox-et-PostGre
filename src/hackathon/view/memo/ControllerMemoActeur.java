package hackathon.view.memo;

import javax.inject.Inject;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;
import hackathon.data.Agir;
import hackathon.data.Personne;


public class ControllerMemoActeur extends Controller {

	
	// Composants de la vue
	
	@FXML
	private TextField		txfTitre;
	@FXML
	private ComboBox<Personne>	cmbPersonne;
	@FXML
	private TextField		txfFonction;
	@FXML
	private DatePicker		dtpDebut;


	// Autres champs
	
	@Inject
	private IManagerGui		managerGui;
	@Inject
	private ModelMemoActeur		modelActeur;


	// Initialisation du Controller

	@FXML
	private void initialize() {
		
		Agir courant = modelActeur.getCourant();

		// Data binding
		txfTitre.textProperty().bind( Bindings.createStringBinding( () -> courant.getMemo() == null ? null: courant.getMemo().getTitre(), courant.memoProperty() ) );
		
		cmbPersonne.setItems( modelActeur.getActeursPossibles() );
		bindBidirectional( cmbPersonne, courant.personneProperty() );
		UtilFX.setCellFactory( cmbPersonne, p -> p.getNom() + " " + p.getPrenom() );
		
		bindBidirectional( txfFonction, courant.fonctionProperty()  );
		bindBidirectional( dtpDebut, courant.debutProperty() );
	}
	
	
	@Override
	public void refresh() {
		modelActeur.actualiserActeursPossibles();
		modelActeur.getCourant().setPersonne(null);
		modelActeur.actualiserCourant();
	}
	
	
	// Actions
	
	@FXML
	private void doAnnuler() {
		managerGui.closeDialog();
	}
	
	@FXML
	private void doValider() {
		modelActeur.validerMiseAJour();
		managerGui.closeDialog();
	}

}
