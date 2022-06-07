package hackathon.view.evenement;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import hackathon.dao.DaoActivite;
import hackathon.dao.DaoEvenement;
import hackathon.data.Activite;
import hackathon.data.Evenement;
import hackathon.data.Groupe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;

public class ModelActivite {
	
	private final ObservableList<Evenement> listeEvenement = FXCollections.observableArrayList();
	private final Activite courant = new Activite();
	
	
	
	public Activite getCourant() {
		return courant;
	}
	@Inject
	private DaoActivite daoActivite;
	@Inject
	private DaoEvenement daoEvenement;
	
	public ObservableList<Evenement> getListeEvenement(){
		return listeEvenement;
	}
	
	public void listerHackathon() {
		listeEvenement.setAll(daoEvenement.listerTout());
	}
	
	public List<Activite> getListeDefis(){
		return  daoActivite.listerDefis();
	}
	
	public void validerMiseAJour() {

		// Vérifie la validité des données
		
//		StringBuilder message = new StringBuilder();
//		
//		if( courant.getNom() == null || courant.getNom().isEmpty() ) {
//			message.append( "\nLe nom ne doit pas être vide." );
//		} else  if ( courant.getNom().length()> 25 ) {
//			message.append( "\nLe nom est trop long." );
//		}
//
//		if( courant.getPrenom() == null || courant.getPrenom().isEmpty() ) {
//			message.append( "\nLe prénom ne doit pas être vide." );
//		} else if ( courant.getPrenom().length()> 25 ) {
//			message.append( "\nLe prénom est trop long." );
//		}
//
//		if( courant.getCategorie() == null ) {
//			message.append( "\nLe catégorie doit être indiquée." );
//		}
//		
//		if ( message.length() > 0 ) {
//			throw new ExceptionValidation( message.toString().substring(1) );
//		}

		
		// Effectue la mise à jour
		
		if ( courant.getId_activite() == null ) {
			// Insertion
			courant.setId_activite(daoActivite.inserer(courant));
		} else {
			// modficiation
			System.out.println("pas de chance!");
		}
	}

}
