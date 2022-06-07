package hackathon.view.membrejury;

import javax.inject.Inject;

import hackathon.data.Groupe;
import hackathon.data.Jury;
import hackathon.data.Membre_jury;
import hackathon.view.EnumView;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;

public class ControleurAjouterMembre extends Controller{

	@FXML
    private TextField txfNom;

	@FXML
    private TextField txfCode;
	
    @FXML
    private TextField txfPrenom;

    @FXML
    private TextField txfTelephone;

    @FXML
    private TextField txfEmail;

    @FXML
    private ComboBox<Groupe> cmbGroupe;

    @FXML
    private ComboBox<String> cmbCode_membre;

    @FXML
    private ComboBox<Jury> cmbId_jury;

    @FXML
    private TextField txfProfession;
 // Autres champs
 	@Inject
 	private IManagerGui			managerGui;
 	@Inject
 	private ModelMembre_jury		modelMembre_jury;
 	@Inject
 	private ModelJury		modeljury;
 	// Initialisation du controller
    
 	public void initialize() {

 		Membre_jury courant = modelMembre_jury.getCourant();
 	// Champs simples
 			bindBidirectional( txfNom, courant.nomProperty() );
 			bindBidirectional( txfCode, courant.code_membreProperty() );
 			bindBidirectional( txfPrenom, courant.prenomProperty() );
 			bindBidirectional( txfEmail, courant.emailProperty() );
 			bindBidirectional( txfTelephone, courant.telephoneProperty() );
 			bindBidirectional( txfProfession, courant.professionProperty() );
 	// Combo box
 			ObservableList<String> liste_id_jury =  FXCollections.observableArrayList();
 			liste_id_jury.add("membre");
 			liste_id_jury.add("chef");
 			//cmbId_jury.setItems(  liste_id_jury);	
 			//bindBidirectional( cmbId_jury, courant.id_juryProperty() );
 			ObservableList<String> liste_code_membre =  FXCollections.observableArrayList();
 			liste_code_membre.add("membre");
 			liste_code_membre.add("chef");
 			//cmbCode_membre.setItems(  liste_code_membre);	
 			//bindBidirectional( cmbCode_membre, courant.code_membreProperty() );
 			
 			/*cmbGroupe.setItems(modelMembre_jury.getListeGroupe());
 			bindBidirectional(cmbGroupe, courant.groupeProperty());
 			UtilFX.setCellFactory(cmbGroupe, "groupe");*/
 			cmbId_jury.setItems( modeljury.getListe() );
 			bindBidirectional( cmbId_jury, courant.id_juryProperty() );
 			UtilFX.setCellFactory( cmbId_jury, item -> item.getId_jury() );
 	}
	/*private void bindBidirectional(ComboBox<Groupe> Property<String>) {
		Control.valueProperty().bindBidirectional( groupeProperty );
		
	}*/
	@Override
 	public void refresh() {
		
		modelMembre_jury.actualiserListe();
		modelMembre_jury.actualiserCourant();
		modeljury.actualiserListe();

		if(modelMembre_jury.getCourant().getCode_membre()!=null) {
			txfCode.setDisable(true);
			ModelMembre_jury.setDisabled(true);
		}
	}
 	
    @FXML
    void doAnnuler() {
    	modelMembre_jury.setSelection(null);
		managerGui.showView( EnumView.GestionsdesMembreJury );
		refresh();
    }

    @FXML
    void doValider() {
    	modelMembre_jury.validerMiseAJour();
    	managerGui.showView( EnumView.GestionsdesMembreJury );
    	refresh();
    }
}
