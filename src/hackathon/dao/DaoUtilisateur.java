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

public class DaoUtilisateur {
	
	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoIntervenir		daoIntervenir;
	
	//PreparedStatement	stmt	= null;
	String				sql;
	
	public List<Utilisateur> listerTout(){
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		//System.out.println("on est ici");
		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM utilisateur ORDER BY nom";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			List<Utilisateur> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construireUtilisateur( rs ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
		
		
	}
	
	public String inserer( Utilisateur p , List<String> tab)  {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;

		try {
			cn = dataSource.getConnection();
			System.out.println("Quelqu'un veut créer des user?");
			// Insère le personne
			sql = "INSERT INTO utilisateur (id_user, nom, prenom, profession, telephone, mdp) VALUES ( ?, ?, ?, ?, ?, ?)";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
			stmt.setObject(	1, p.getId_user() );
			stmt.setObject(	2, p.getNom() );
			stmt.setObject(	3, p.getPrenom() );
			stmt.setObject(	4, p.getProfession() );
			stmt.setObject(	5, p.getTelephone() );
			stmt.setObject(	6, p.getMdp() );
			stmt.executeUpdate();

			System.out.println("Bonne creation");
			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			p.setId_user( rs.getObject( 1, String.class )); 
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
		
		// Insère les rôles
		daoIntervenir.insererPourUtilisateur( p, tab );
				
		// Retourne l'identifiant
		return p.getId_user();
	}
	
	public Utilisateur retrouver( String idUtilisateur )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM utilisateur WHERE id_user = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, idUtilisateur);
            rs = stmt.executeQuery();

            if ( rs.next() ) {
                return construireUtilisateur(rs );
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public void supprimer( String idUtilisateur )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Supprime le personne
			sql = "DELETE FROM utilisateur WHERE id_user = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setObject( 1, idUtilisateur );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	
	public void modifier( Utilisateur p )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le personne
			sql = "UPDATE utilisateur SET nom = ?, prenom = ?, profession = ?, telephone = ?, mdp = ?  WHERE id_user =  ?";
			stmt = cn.prepareStatement( sql );
			//stmt.setObject( 1, Integer.parseInt(p.getId()) );
			stmt.setObject( 1, p.getNom() );
			stmt.setObject( 2, p.getPrenom() );
			stmt.setObject( 3, p.getProfession() );
			stmt.setObject( 4, p.getTelephone() );
			stmt.setObject( 5, p.getMdp() );
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	
	private Utilisateur construireUtilisateur(ResultSet rs) throws SQLException {
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setId_user(rs.getObject("id_user", String.class));
		utilisateur.setNom(rs.getObject("nom", String.class));
		utilisateur.setPrenom(rs.getObject("prenom", String.class));
		utilisateur.setProfession(rs.getObject("profession", String.class));
		utilisateur.setTelephone(rs.getObject("telephone", String.class));
		utilisateur.setMdp(rs.getObject("mdp", String.class));
		//System.out.println(utilisateur);
		return utilisateur;
	}

	public boolean verifierUnicitePseudo( String pseudo)   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		if ( pseudo == null ) pseudo = "";
		
		try {
			cn = dataSource.getConnection();

			sql = "SELECT COUNT(*) = 0 AS unicite"
					+ " FROM utilisateur WHERE id_user = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(	1, pseudo );
			rs = stmt.executeQuery();
			
			rs.next();
			return rs.getBoolean( "unicite" );
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	
	public Utilisateur validerAuthentification( String pseudo, String motDePasse )  {
		
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM utilisateur WHERE id_user = ? AND mdp = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, pseudo );
			stmt.setObject( 2, motDePasse );
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireUtilisateur( rs);			
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


}
