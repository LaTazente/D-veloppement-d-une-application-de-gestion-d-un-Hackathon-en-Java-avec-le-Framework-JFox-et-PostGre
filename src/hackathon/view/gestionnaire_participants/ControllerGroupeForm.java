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
import hackathon.data.Jury;
import hackathon.data.Personne;
import hackathon.view.EnumView;
import hackathon.view.jury.ModelJury;
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
	  private TextField txfNombreParticipants;

	  @FXML
	  private ComboBox<Jury>	cmbJury;
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
		bindBidirectional( txfNomEquipe, courant.nomProperty() );
		bindBidirectional( txfIdEquipe, courant.id_groupeProperty());
		bindBidirectional( txfNombreParticipants, courant.nbre_menbresProperty(), new ConverterInteger() );
		
		System.out.println("ici test "+ modelJury.getListe());
		cmbJury.setItems( modelJury.getListe() );
		bindBidirectional( cmbJury, courant.id_juryProperty() );
		UtilFX.setCellFactory( cmbJury, item -> item.getId_jury() );
		
	}
	
	
	@Override
	public void refresh() {
		modelGroupe.actualiserCourant();
		modelJury.actualiserListe();
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
	private void doJurySupprimer() {
		cmbJury.setValue( null );
	}
	

}
