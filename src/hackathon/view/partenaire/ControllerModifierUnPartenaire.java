package hackathon.view.partenaire;

import javax.inject.Inject;

import hackathon.data.Evenement;
import hackathon.data.Partenaire;
import hackathon.view.EnumView;
import hackathon.view.evenement.ModelEvenement;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;

public class ControllerModifierUnPartenaire  extends Controller{

    @FXML
    private TextField tfNomP;

    @FXML
    private TextField tfDescription;
    @FXML
	 private ComboBox<Evenement>	cmbEvenement;
    
  
    
 // Autres champs
 	@Inject
 	private IManagerGui			managerGui;
 	@Inject
 	private ModelPartenaire		modelPartenaire;
 	@Inject
 	private ModelEvenement		modelEvenement;
 	
 	public void initialize() {

 		Partenaire courant = modelPartenaire.getCourant();
 		
 		// Champs simples
 		bindBidirectional( tfNomP, courant.nomProperty() );
 		bindBidirectional( tfDescription, courant.descriptionProperty() );
 		
 		cmbEvenement.setItems( modelEvenement.getListe() );
		bindBidirectional( cmbEvenement, courant.codeProperty() );
		UtilFX.setCellFactory( cmbEvenement, item -> item.getCode() );
 	}
 	
 	
 	

	public void refresh() {
 		//modelPartenaire.listerGroupe();
 		modelPartenaire.actualiserListe();
 		modelEvenement.actualiserListe();
 		modelPartenaire.actualiserCourant();
 		System.out.println(modelPartenaire.getSelection());
 	}
 	
 	// Actions
 	
 	@FXML
 	private void doValider() {
 		modelPartenaire.modifier();
 		managerGui.showView( EnumView.listepartenaires );
 	}
 	
 	@FXML
 	private void doAnnuler() {
 		managerGui.showView( EnumView.listepartenaires );
 	}

 }



