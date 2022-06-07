package hackathon.view.evenement;

import javax.inject.Inject;

import hackathon.data.Activite;
import hackathon.data.Evenement;
import hackathon.data.Personne;
import hackathon.view.EnumView;
import hackathon.view.categorie.ModelCategorie;
import hackathon.view.personne.ModelPersonne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import jfox.javafx.control.EditingCell;
import jfox.javafx.util.ConverterInteger;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;

public class ControleurAjouterActivite extends Controller{
	
    @FXML
    private TextField txfId;

    @FXML
    private TextField txfNom;

    @FXML
    private TextField txfDuree;

    @FXML
    private ComboBox<String> cmbType;

    @FXML
    private ComboBox<Evenement> cmbHackathon;
    
	// Autres champs
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelActivite modelActivite;
    
	
	// Initialisation du controller
	
	public void initialize() {

		Activite courant = modelActivite.getCourant();
		
		// Champs simples
		bindBidirectional( txfId, courant.id_activiteProperty(), new ConverterInteger() );
		bindBidirectional( txfNom, courant.nomProperty() );
		bindBidirectional(txfDuree, courant.dureeProperty());
        
		// Combo box
		ObservableList<String> liste_type =  FXCollections.observableArrayList();
		liste_type.add("anxe");
		liste_type.add("défi");
		cmbType.setItems(  liste_type);
		bindBidirectional(cmbType, courant.typeProperty());
		
		cmbHackathon.setItems(modelActivite.getListeEvenement());
		UtilFX.setCellFactory(cmbHackathon, "nom");
		bindBidirectional(cmbHackathon , courant.hackathonProperty());
	}

	@FXML
	public void refresh() {
		modelActivite.listerHackathon();
	}
    @FXML
    void doAjouter(ActionEvent event) {
    	modelActivite.validerMiseAJour();
    }

    @FXML
    void goBack(ActionEvent event) {
    	managerGui.showView(EnumView.AccueilAdmin);
    }
}
