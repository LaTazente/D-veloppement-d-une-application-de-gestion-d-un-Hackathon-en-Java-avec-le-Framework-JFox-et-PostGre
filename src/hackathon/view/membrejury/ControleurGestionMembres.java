package hackathon.view.membrejury;

import javax.inject.Inject;

import hackathon.data.Categorie;
import hackathon.data.Membre_jury;
import hackathon.data.Membre_jury;
import hackathon.data.Personne;
import hackathon.view.EnumView;
import hackathon.view.systeme.ControllerConnexion;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IManagerGui;

public class ControleurGestionMembres extends Controller{
	

 
	@FXML
	private TableView<Membre_jury> tbvMembre_jurys;
	
    @FXML
    private TableColumn<Membre_jury , String> tbcNom;

    @FXML
    private TableColumn<Membre_jury , String> tbcPrenom;

    @FXML
    private TableColumn<Membre_jury , String> tbcMail;

    @FXML
    private TableColumn<Membre_jury , String> tbcTelephone;

    @FXML
    private TableColumn<Membre_jury , String> tbcProfession;

    @FXML
    private TableColumn<Membre_jury , String> tbcCodeMembre_jury;
    
    @FXML
    private TableColumn<Membre_jury , String> tbcIdMembre_jury;
    
    
    @FXML 
    private Button	btnModifier;
    
    @FXML 
    private Button	btnSupprimer;
    
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelMembre_jury		modelMembre_jury;
	
	@FXML
	private void initialize() {

		// TableView
		tbvMembre_jurys.setItems( modelMembre_jury.getListe() );
		UtilFX.setValueFactory( tbcNom, "nom" );
		UtilFX.setValueFactory( tbcPrenom, "prenom" );
		UtilFX.setValueFactory( tbcMail, "email" );
		UtilFX.setValueFactory( tbcTelephone, "telephone" );
		UtilFX.setValueFactory( tbcProfession, "profession" );
		UtilFX.setValueFactory( tbcCodeMembre_jury, "code_membre" );
		UtilFX.setValueFactory( tbcIdMembre_jury, "id_jury" );
		
		// Configuraiton des boutons
		tbvMembre_jurys.getSelectionModel().getSelectedItems().addListener( 
		        (ListChangeListener<Membre_jury>) (c) -> {
		        	configurerBoutons();
		});
    	configurerBoutons();
	}
	
	// Méthodes auxiliaires

	private void configurerBoutons() {
    	if( tbvMembre_jurys.getSelectionModel().getSelectedItems().isEmpty() ) {
			btnModifier.setDisable(true);
			btnSupprimer.setDisable(true);
		} else {
			btnModifier.setDisable(false);
			btnSupprimer.setDisable(false);
		}
	}
//		
////	}
	
	@Override
	public void refresh() {
		modelMembre_jury.actualiserListe();
		//UtilFX.setCellFactory( tbvMembre_jurys, modelMembre_jury.getSelection() );
		tbvMembre_jurys.requestFocus();
	}
	
	// Gestion des évènements

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( tbvMembre_jurys.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					doModifier();
				}
			}
		}
	}
	
	@FXML
	private void doAjouter() {
		modelMembre_jury.setSelection(null);
		managerGui.showView( EnumView.AjouterUnMembre );
		refresh();
	}
	
	@FXML
	private void doModifier() {
			modelMembre_jury.setSelection(tbvMembre_jurys.getSelectionModel().getSelectedItem());
			managerGui.showView( EnumView.AjouterUnMembre );
			refresh();
		
	}
	
	@FXML
	private void doSupprimer() {
		if ( managerGui.showDialogConfirm("Êtes-vous sûr de vouloir supprimer ce membre ?" ) ) {
			modelMembre_jury.supprimer( tbvMembre_jurys.getSelectionModel().getSelectedItem() );
			refresh();
		}
	}
	
	@FXML
	private void goBack() {
			managerGui.showView(EnumView.AccueilAdmin);
	}
}
