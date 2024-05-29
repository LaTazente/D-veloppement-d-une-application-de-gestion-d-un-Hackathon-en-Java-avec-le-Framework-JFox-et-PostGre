package hackathon.view.evenement;

import java.time.LocalDate;

import javax.inject.Inject;

import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import jfox.javafx.util.UtilFX;
import jfox.javafx.view.Controller;
import jfox.javafx.view.IEnumView;
import jfox.javafx.view.IManagerGui;
import hackathon.data.Evenement;
import hackathon.data.Groupe;
import hackathon.data.Partenaire;
import hackathon.view.EnumView;
import hackathon.view.gestionnaire_participants.ModelGroupe;
import hackathon.view.gestionnaire_participants.ModelParticpant;
import hackathon.view.partenaire.ModelPartenaire;


public class ControllerStatistiqueEvenement extends Controller {
	
	
	// Composants de la vue

	@FXML
    private TextField txfCode;

    @FXML
    private TextField txfDateDebut;

    @FXML
    private TextField txfTheme;

    @FXML
    private TextField txfDateFin;

    @FXML
    private TextField txfTauxPresence;

    @FXML
    private TextField txfTauxAbsence;

    @FXML
    private TableView<Groupe> tbvGroupes;

    @FXML
    private TableColumn<Groupe, String> columnNom;

    @FXML
    private TableColumn<Groupe, String> columnIdGroupe;

    @FXML
    private TableColumn<Groupe, String> columnCode;

    @FXML
    private TableColumn<Groupe, String> columnNbreMembres;

    @FXML
    private TableView<Partenaire> tbvPartenaire;

    @FXML
    private TableColumn<Partenaire, String> columnNomPartenaire;
    
    @FXML
    private TableColumn<Partenaire, String> columnDescription;
    
	// Autres champs
	
	@Inject
	private IManagerGui			managerGui;
	@Inject
	private ModelEvenement			modelEvenement;
	@Inject
	private ModelGroupe			modelGroupe;
	@Inject
	private ModelPartenaire			modelPartenaire;
	
	// Initialisation du Controller

	@FXML
	private void initialize() {
		
		// TableView
		tbvGroupes.setItems( modelEvenement.getStatistique().getGroupes() );
		tbvPartenaire.setItems( modelEvenement.getStatistique().getPartenaires());
		
		// UtilFX.setCellFactory( lsvEvenements, "nom" );
		// UtilFX.setValueFactory(columnNom, modelEvenement.getCourant().getNom());
		
		// Groupe 
		UtilFX.setValueFactory(columnNom, "nom");
		UtilFX.setValueFactory(columnIdGroupe, "id_groupe");
		UtilFX.setValueFactory(columnCode, "code" );
		UtilFX.setValueFactory(columnNbreMembres, "nbre_menbres");
		
		
		// Partenaire
		tbvPartenaire.setItems( modelPartenaire.getListe() );
		UtilFX.setValueFactory( columnNomPartenaire, "nom" );
		UtilFX.setValueFactory( columnDescription, "description" );
		
		
		// Configuraiton des boutons
//		tbvPartenaire.getSelectionModel().getSelectedItems().addListener( 
//		        (ListChangeListener<Partenaire>) (c) -> {
//		        	configurerBoutons();
//		});
//    	configurerBoutons();
//		
//		// Comportement si modificaiton de la séleciton
//		tbvGroupes.getSelectionModel().getSelectedItems().addListener( 
//				(ListChangeListener<Groupe>) (c) -> {
//					 configurerBoutons();					
//		});
//		configurerBoutons();
	}
	
	@Override
	public void refresh() {
		
		var courant = modelEvenement.getCourant();
		
		modelEvenement.actualiserListe();
		System.out.println("courant "+courant);
		//modelGroupe.actualiserListe();
		//modelPartenaire.actualiserListe();
		// UtilFX.selectRow(lsvEvenements, modelEvenement.getSelection() );
		// lsvEvenements.requestFocus();
		
		
		//Ajout des valeurs
		txfTauxAbsence.setText(modelEvenement.getStatistique().getTaux_absence()+"");
		txfTauxPresence.setText(modelEvenement.getStatistique().getTaux_presence()+"");
		txfCode.setText(courant.getCode());
		txfDateDebut.setText(courant.getDateDebut()+"");
		txfDateFin.setText(courant.getDateFin()+"");
		txfTheme.setText(courant.getTheme());
		
		tbvGroupes.setItems( modelEvenement.getStatistique().getGroupes() );
		tbvPartenaire.setItems( modelEvenement.getStatistique().getPartenaires());
		
	}

	
	// Actions

//	@FXML
//	private void doAjouter() {
//		modelEvenement.setSelection( null );
//		managerGui.showView( EnumView.EvenementForm );
//	}
//
//	@FXML
//	private void doModifier() {
//		
//		modelEvenement.setSelection( lsvEvenements.getSelectionModel().getSelectedItem() );
//		managerGui.showView( EnumView.EvenementForm );
//	}
//
//	@FXML
//	private void doSupprimer() {
//		if ( managerGui.showDialogConfirm( "Confirmez-vous la suppresion ?" ) ) {
//			modelEvenement.supprimer( lsvEvenements.getSelectionModel().getSelectedItem() );
//			refresh();
//		}
//	}
	
	
	
	// Gestion des évènements

	// Clic sur la liste
//	@FXML
//	private void gererClicSurListe( MouseEvent event ) {
//		if (event.getButton().equals(MouseButton.PRIMARY)) {
//			if (event.getClickCount() == 2) {
//				if ( lsvEvenements.getSelectionModel().getSelectedIndex() == -1 ) {
//					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
//				} else {
//					modelEvenement.setSelection(lsvEvenements.getSelectionModel().getSelectedItem());
//					modelEvenement.actualiserCourant();
//					managerGui.showView(EnumView.choisirActivite);
//				}
//			}
//		}
//	}
	
	@FXML
	private void gererClicSurListe( MouseEvent event ) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				if ( tbvGroupes.getSelectionModel().getSelectedIndex() == -1 ) {
					managerGui.showDialogError( "Aucun élément n'est sélectionné dans la liste.");
				} else {
					//modelEvenement.setSelection(lsvEvenement.getSelectionModel().getSelectedItem());
					modelEvenement.actualiserCourant();
					//managerGui.showView(EnumView.EvenementStatistique);
					modelGroupe.getMembres(tbvGroupes.getSelectionModel().getSelectedItem().getId_groupe());
					managerGui.showDialog(EnumView.ParticipantsEquipe );
					// managerGui.showDialog(EnumView.EvenementStatistique);
				}
			}
		}
	}

	@FXML
	private void goBack() {
	managerGui.showView(EnumView.GestionEvenements);
	}
	// Méthodes auxiliaires
	
}
