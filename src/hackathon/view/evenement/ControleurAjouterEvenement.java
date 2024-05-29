package hackathon.view.evenement;

import javax.inject.Inject;

import hackathon.data.Evenement;
import hackathon.view.EnumView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;

public class ControleurAjouterEvenement extends Controller{

	@Inject
	private IManagerGui			managerGui;
	
    @FXML
    private TextField txfCode;
    
    @FXML
    private TextField txfNom;

    @FXML
    private TextField txfTheme;

    @FXML
    private TextField txfLieu;

    @FXML
    private DatePicker dpkDateDebut;

    @FXML
    private DatePicker dpkDateFin;
    
    @Inject
    private ModelEvenement modelEvenement;
    
	public void initialize() {

		Evenement courant = modelEvenement.getCourant();
		
		// Champs simples
		bindBidirectional( txfNom, courant.nomProperty() );
		bindBidirectional( txfCode, courant.codeProperty() );
		bindBidirectional( txfTheme, courant.themeProperty() );
		bindBidirectional( txfLieu, courant.lieuProperty() );
		bindBidirectional( dpkDateDebut, courant.dateDebutProperty() );
		bindBidirectional( dpkDateFin, courant.dateFinProperty() );
	}
    @FXML
    void doAjouter(ActionEvent event) {
    	modelEvenement.insertion();
    	//managerGui.showView(EnumView.GestionEvenements);
    	managerGui.showView(EnumView.Connexion);
    }

    @FXML
    void doRetour(ActionEvent event) {

    }
}
