package hackathon.view.gestionnaire_participants;

import javax.inject.Inject;

import javafx.beans.property.BooleanProperty;
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
import hackathon.data.Evenement;
import hackathon.data.Personne;
import hackathon.view.EnumView;
import hackathon.view.evenement.ModelEvenement;
import hackathon.view.personne.ModelPersonne;


public class ControllerGroupeForm extends Controller {

	
	// Composants de la vue
	
	  @FXML
	  private Button btnAjouter;

	  @FXML
	  private TextField txfNomEquipe;

	  @FXML
	  private TextField txfIdEquipe;

	  @FXML
	  private ComboBox<Evenement>	cmbEvenement;
	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelGroupe			modelGroupe;
	@Inject
	private ModelEvenement		modelEvenement;

	
	// Initialisation du Controller
	
	@FXML
	private void initialize() {
		
		var courant = modelGroupe.getCourant();

		// Champs simples
		// bindBidirectional( txfId, courant.idProperty(), new ConverterInteger() );
		bindBidirectional( txfNomEquipe, courant.nomProperty() );
		bindBidirectional( txfIdEquipe, courant.id_groupeProperty());
		
		System.out.println("ici test "+ modelEvenement.getListe());
		cmbEvenement.setItems( modelEvenement.getListe() );
		bindBidirectional( cmbEvenement, courant.codeProperty() );
		UtilFX.setCellFactory( cmbEvenement, item -> item.getCode() );
		
	}
	
	
	@Override
	public void refresh() {
		modelGroupe.actualiserCourant();
		modelEvenement.actualiserListe();
		if(modelGroupe.getCourant().getId_groupe() != null) {
			System.out.println("ici "+  modelGroupe.getCourant().getId_groupe());
			txfIdEquipe.setDisable(true);
			ModelGroupe.setDisabled(true);
		}
		
		
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
	private void doEvenementSupprimer() {
		cmbEvenement.setValue( null );
	}
	

}
