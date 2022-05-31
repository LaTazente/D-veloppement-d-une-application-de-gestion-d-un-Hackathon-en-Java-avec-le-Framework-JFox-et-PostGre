package hackathon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hackathon.data.*;
import jfox.jdbc.UtilJdbc;

import javax.inject.Inject;
import javax.sql.DataSource;

public class DaoParticipant {
	
	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoGroupe daoGroupe;
//	private DaoCompte		daoCompte;
	
	//PreparedStatement	stmt	= null;
	String				sql;
	
	public List<Participant> listeParticipant(){
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		//System.out.println("on est ici");
		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM hackathon.participant ORDER BY nom";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			List<Participant> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construireParticipant( rs ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
		
		
	}
	
	public String inserer( Participant p )  {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;

		try {
			cn = dataSource.getConnection();
			System.out.println("Quelqu'un veut créer des particpants?");
			// Insère le personne
			sql = "INSERT INTO hackathon.participant ( nom, prenom, role, sexe, telephone, email, id_groupe ) VALUES ( ?, ?, ?, ?, ?, ?, ? )";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
			stmt.setObject(	1, p.getNom() );
			stmt.setObject(	2, p.getPrenom() );
			stmt.setObject(	3, p.getRole() );
			stmt.setObject(	4, p.getSexe() );
			stmt.setObject(	5, p.getTelephone() );
			stmt.setObject(	6, p.getEmail() );
			stmt.setObject(7, p.getIdGroupe().getId_groupe());
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			p.setId( rs.getObject( 1, Integer.class )+"" ); 
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
		
		// Retourne l'identifiant
		return p.getId();
	}
	
	public Participant retrouver( int idParticipant )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM hackathon.participant WHERE id = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, idParticipant);
            rs = stmt.executeQuery();

            if ( rs.next() ) {
                return construireParticipant(rs );
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public void supprimer( int idParticipant )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Supprime le personne
			sql = "DELETE FROM hackathon.participant WHERE id = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setObject( 1, idParticipant );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	
	public void modifier( Participant p )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le personne
			sql = "UPDATE hackathon.participant SET nom = ?, prenom = ?, role = ?, sexe = ?, telephone = ?, email = ? ,id_groupe = ? WHERE id =  ?";
			stmt = cn.prepareStatement( sql );
			//stmt.setObject( 1, Integer.parseInt(p.getId()) );
			stmt.setObject( 1, p.getNom() );
			stmt.setObject( 2, p.getPrenom() );
			stmt.setObject( 3, p.getRole() );
			stmt.setObject( 4, p.getSexe() );
			stmt.setObject( 5, p.getTelephone() );
			stmt.setObject( 6, p.getEmail() );
			stmt.setObject( 7, p.getIdGroupe().getId_groupe() );
			stmt.setObject( 8, Integer.parseInt(p.getId())  );
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	
	private Participant construireParticipant(ResultSet rs) throws SQLException {
		Participant participant = new Participant();
		participant.setId(rs.getObject("id", Integer.class)+"");
		participant.setNom(rs.getObject("nom", String.class));
		participant.setPrenom(rs.getObject("prenom", String.class));
		participant.setRole(rs.getObject("role", String.class));
		participant.setSexe(rs.getObject("sexe", String.class));
		participant.setTelephone(rs.getObject("telephone", String.class));
		participant.setEmail(rs.getObject("email", String.class));
		participant.setIdGroupe(daoGroupe.retrouver(rs.getObject("id_groupe", String.class)));
		//System.out.println(participant);
		return participant;
	}
	
	

}
