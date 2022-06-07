package hackathon.view.gestionNotes;

import java.util.List;

import javax.inject.Inject;


import hackathon.dao.DaoEvaluer;
import hackathon.dao.DaoJury;
import hackathon.data.Activite;
import hackathon.data.Jury;
import hackathon.data.Note;
import hackathon.view.evenement.ModelActivite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jfox.exception.ExceptionValidation;

public class ModelNotation {
	
	@Inject
	private DaoEvaluer daoEvaluer;
	@Inject
	private DaoJury	daoJury;
	@Inject 
	private ModelActivite modelActivite;
	private ObservableList<Jury> listeJury = FXCollections.observableArrayList();
    private ObservableList<Activite> listeDefis = FXCollections.observableArrayList();
    private ObservableList<Note> liste = FXCollections.observableArrayList();
    
    
	
	
	public ObservableList<Jury> getListeJury() {
		return listeJury;
	}



	public ObservableList<Activite> getListeDefis() {
		return listeDefis;
	}



	public ObservableList<Note> getListe() {
		return liste;
	}


	public void insertion(Activite a, Jury j) {
		daoEvaluer.inserer(a, j);
	}
	
	public void modifierJury(Activite a,Jury j) {
		daoEvaluer.ModifierJury(a,j);
	}
	
	public void listerJury() {
		listeJury.setAll(daoJury.listerTout());
	}
	
	public void listerNote(Activite a) {
		liste.setAll(daoEvaluer.retrouver(a));
	}
	
	public void listerDefis() {
		listeDefis.setAll(modelActivite.getListeDefis());
	}
	
	public void ValiderModif(List<Note> notes) {
		for(Note note:notes) {
			Double point = Double.parseDouble(note.getNote());
			if(point<0 || point >20) 
				throw new ExceptionValidation("Entrez une note correcte(0<note<20)!");
		}
		daoEvaluer.modifier(notes);
		
	}
	
	public Jury getJury(Activite a) {
		return daoEvaluer.retrouverJury(a);
	}
	
//	public ObservableList<Jury> getJurys(){
//		ObservableList<Jury> listeJurys = FXCollections.observableArrayList();
//		listeJurys.addAll(daoJury.listerTout());
//		return listeJurys;
//	}
	
//	public List<Note> getNotes(Activite a){
//		for(Note n: daoEvaluer.retrouver(a)) {
//			System.out.println(n);
//		}
//		return daoEvaluer.retrouver(a);
//	}
	
	

}
