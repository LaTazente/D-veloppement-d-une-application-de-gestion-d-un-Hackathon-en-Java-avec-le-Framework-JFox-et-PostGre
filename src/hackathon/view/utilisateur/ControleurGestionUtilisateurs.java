package hackathon.view.utilisateur;

import javax.inject.Inject;

import hackathon.data.Categorie;
import hackathon.data.Utilisateur;
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

public class ControleurGestionUtilisateurs extends Controller{
	

 
	@FXML
	private TableView<Utilisateur> tbvUtilisateurs;
    @FXML
    private TableColumn<Utilisateur , String> tbcNom;

    @FXML
    private TableColumn<Utilisateur , String> tbcPrenom;

    @FXML
    private TableColumn<Utilisateur , String> tbcProfession;

    @FXML
    private TableColumn<Utilisateur , String> tbcTelephone;

    @FXML
    private TableColumn<Utilisateur , String> tbcRole;

    @FXML
    private TableColumn<Utilisateur , String> tbcIdUser;
    
    @FXML 
    private Button	btnModifier;
    
    @FXML 
    private Button	btnSupprimer;
    
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelUtilisateur		modelUtilisateur;
	
	@FXML
	private void initialize() {

		// TableView
		tbvUtilisateurs.setItems( modelUtilisateur.getListe() );
		UtilFX.setValueFactory( tbcNom, "nom" );
		UtilFX.setValueFactory( tbcPrenom, "prenom" );
		UtilFX.setValueFactory( tbcRole, "role" );
		UtilFX.setValueFactory( tbcIdUser, "id_user" );
		UtilFX.setValueFactory( tbcTelephone, "telephone" );
		UtilFX.setValueFactory( tbcProfession, "profession" );
		
		// Configuraiton des boutons
		tbvUtilisateurs.getSelectionModel().getSelectedItems().addListener( 
		        (ListChangeListener<Utilisateur>) (c) -> {
		        	configurerBoutons();
		});
    	configurerBoutons();
	}
	
	// Méthodes auxiliaires

	private void configurerBoutons() {
    	if( tbvUtilisateurs.getSelectionModel().getSelectedItems().isEmpty() ) {
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
		modelUtilisateur.actualiserListe();
		UtilFX.selectRow( tbvUtilisateurs, modelUtilisateur.getSelection() );
		tbvUtilisateurs.requestFocus();
	}
	
	// Gestion des évènements

	// Clic sur la liste
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( tbvUtilisateurs.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					doModifier();
				}
			}
		}
	}
	
	@FXML
	private void doAjouter() {
		modelUtilisateur.setSelection(null);
		managerGui.showView( EnumView.UserForm );
	}
	
	@FXML
	private void doModifier() {
		modelUtilisateur.setSelection( tbvUtilisateurs.getSelectionModel().getSelectedItem() );
		managerGui.showView( EnumView.UserForm);
	}
	
	@FXML
	private void doSupprimer() {
		if ( managerGui.showDialogConfirm("Êtes-vous sûr de vouloir supprimer cet utilisateur ?" ) ) {
			modelUtilisateur.supprimer( tbvUtilisateurs.getSelectionModel().getSelectedItem() );
			refresh();
		}
	}
	
	@FXML
	private void goBack() {
		
		if(ControllerConnexion.getRoleUser().equals("GestionnaireParticipants")) 
			managerGui.showView(EnumView.AccueilGestionnaireParticipants);
		
		if(ControllerConnexion.getRoleUser().equals("Administrateur"))
			managerGui.showView(EnumView.AccueilAdmin);
	}
	
	

}
