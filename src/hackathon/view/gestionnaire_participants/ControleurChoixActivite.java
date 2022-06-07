package hackathon.view.gestionnaire_participants;

import javax.inject.Inject;

import hackathon.commun.Roles;
import hackathon.data.Activite;
import hackathon.view.EnumView;
import hackathon.view.evenement.ModelActivite;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Pair;
import jfox.javafx.util.BindingPairCheckList;
import jfox.javafx.util.ConverterInteger;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;

public class ControleurChoixActivite extends Controller{
	
    @FXML
    private ListView<Pair<Activite, BooleanProperty>> lsvActivites;
    @FXML
    private ListView<Pair<Activite, BooleanProperty>> lsvActivites1;

    @FXML
    private TextField txfNomChef;

    @FXML
    private TextField txfNomGroupe;

    @FXML
    private TextField txfNbreMembre;
    
    @Inject
    private ModelGroupe modelGroupe;
    @Inject
    private ModelActivite modelActivite;
	@Inject
	private IManagerGui			managerGui;
    
    public  ObservableList<Activite> listeDefisRestants = FXCollections.observableArrayList();
    public  ObservableList<Activite> listeDefisChoisis = FXCollections.observableArrayList();
	@FXML
	private void initialize() {

		// ListView
	}
	
	@FXML
	public void refresh() {
		var courant = modelGroupe.getCourant();
		listeDefisChoisis.clear();
		listeDefisRestants.clear();
		var binding = new BindingPairCheckList<>( modelGroupe.getDefisRestants(courant),listeDefisRestants);
    	binding.configureListView( lsvActivites, item -> item.getKey().getNom()  );
		var binding1 = new BindingPairCheckList<>( modelGroupe.getDefisChoisis(courant),listeDefisChoisis);
    	binding1.configureListView( lsvActivites1, item -> item.getKey().getNom()  );	
		// Champs simples
		txfNomGroupe.setText(courant.getNom());
		txfNomChef.setText(modelGroupe.getNomChef(courant));
		txfNbreMembre.setText(modelGroupe.getNombreMembres(courant)+"");
	}
	
	
    @FXML
    void doAnnuler() {
    	managerGui.showView(EnumView.GestionEquipes);
    }

    @FXML
    void doValider() {
//    	System.out.println(modelGroupe.getCourant());
    	managerGui.showDialogMessage("Bon choix de défi(s)!\nQue le meilleur gagne!");
    	//for(Activite a:listeDefisRestants)System.out.println(a);
    	modelGroupe.insererChoix(modelGroupe.getCourant(), listeDefisRestants);
    	managerGui.showView(EnumView.choisirActivite);
    }

    @FXML
    void doSupprimer() {
//    	System.out.println(modelGroupe.getCourant());
    	//for(Activite a:listeDefisChoisis)System.out.println(a);
    	managerGui.showDialogMessage("Quel Dommage!\nRendez-vous une prochaine fois!");  //c'est mieux de faire un confirm Mr
    	modelGroupe.supprimerChoix(modelGroupe.getCourant(), listeDefisChoisis);
    	managerGui.showView(EnumView.choisirActivite);
    }

}
