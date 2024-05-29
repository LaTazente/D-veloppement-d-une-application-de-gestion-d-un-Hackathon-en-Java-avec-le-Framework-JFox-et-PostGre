package hackathon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import hackathon.data.Activite;
import hackathon.data.Groupe;
import hackathon.data.Jury;
import hackathon.data.Note;
import hackathon.data.Participant;
import jfox.jdbc.UtilJdbc;

public class DaoEvaluer {
	
	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoEffectuer daoEffectuer;
	@Inject
	private DaoActivite daoActivite;
	@Inject
	private DaoJury daoJury;
	@Inject
	private DaoGroupe daoGroupe;
	
	//éléments de connexion
	String				sql;
	Connection			cn		= null;
	PreparedStatement	stmt	= null;
	ResultSet 			rs 		= null;
	
	public void inserer( Activite a,Jury j)  {
		try {
			cn = dataSource.getConnection();
			// Insère le personne
			sql = "INSERT INTO hackathon.evaluer ( id_groupe,id_activite,id_jury ) VALUES ( ?, ?, ? )";
			stmt = cn.prepareStatement( sql );
			for(Groupe g:daoEffectuer.getGroupeDefi(a)) {
				stmt.setObject(1, g.getId_groupe());
				stmt.setObject(2, a.getId_activite());
				stmt.setObject(3, j.getId_jury());
				stmt.executeUpdate();
			}
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
		
	}
	public List<Note> retrouver(Activite a){
		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM hackathon.evaluer WHERE id_activite = ? ORDER BY note DESC";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, a.getId_activite());
			rs = stmt.executeQuery();
			List<Note> liste = new  ArrayList<Note>();
			while(rs.next()) {
				liste.add(constructionNote(rs));
			}
			return liste;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
		
	}
	
	public Jury retrouverJury(Activite a) {
		try {
			cn = dataSource.getConnection();
			sql = "SELECT id_jury  FROM hackathon.evaluer WHERE id_activite = ? LIMIT 1";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, a.getId_activite());
			rs = stmt.executeQuery();
			if(rs.next())
				return daoJury.retrouver(rs.getObject("id_jury", String.class));
			return null;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public void ModifierJury(Activite a,Jury j) {
		try {
			cn = dataSource.getConnection();

			// Modifie le personne
			sql = "UPDATE hackathon.evaluer SET id_jury = ? WHERE  id_activite = ?";
			stmt = cn.prepareStatement( sql );
			//stmt.setObject( 1, Integer.parseInt(p.getId()) );
			stmt.setObject( 1, j.getId_jury() );
			stmt.setObject( 2, a.getId_activite());
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	
	public List<Note> listerTout(){
		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM hackathon.evaluer ";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();
			List<Note> liste = new  ArrayList<Note>();
			while(rs.next()) {
				liste.add(constructionNote(rs));
			}
			return liste;
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public void modifier(List<Note> notes) {
		try {
			cn = dataSource.getConnection();

			// Modifie le personne
			sql = "UPDATE hackathon.evaluer SET note = ? WHERE id_groupe = ? AND id_activite = ?";
			stmt = cn.prepareStatement( sql );
			//stmt.setObject( 1, Integer.parseInt(p.getId()) );
			for(Note n:notes) {
				stmt.setObject( 1, Double.parseDouble(n.getNote()) );
				stmt.setObject( 2, n.getGroupe().getId_groupe());
				stmt.setObject( 3, n.getDefi().getId_activite() );
				stmt.executeUpdate();
			}
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	
	private Note constructionNote(ResultSet rs) throws SQLException {
		Note note = new Note();
		note.setDefi(daoActivite.retrouver(rs.getObject("id_activite", Integer.class)));
		note.setGroupe(daoGroupe.retrouver(rs.getObject("id_groupe", String.class)));
		note.setNote(rs.getObject("note", Double.class)+"");
		
		return note;
		
	}

}
