package hackathon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;

import javax.inject.Inject;
//import javax.sql.CommonDataSource;
import javax.sql.DataSource;

//import javax.sql.DataSource;
import jfox.jdbc.UtilJdbc;
import hackathon.data.Evenement;
//import hackathon.data.Personne;

import java.util.ArrayList;
import java.util.List;

public class DaoEvenement {

	
	// Champs

	@Inject
	private DataSource		dataSource;
//	@Inject
//	private DaoTelephone	daoTelephone;
//	@Inject
//	private DaoCategorie	daoCategorie;

	
	// Actions

	public void inserer( Evenement evt )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn =  dataSource.getConnection();

			// Insère le personne
			sql = "INSERT INTO hackathon.evenement ( code, nom, theme, lieu, date_debut, date_fin ) VALUES ( ?, ?, ?, ?, ?, ? )";
			stmt = cn.prepareStatement( sql/*, Statement.RETURN_GENERATED_KEYS */ );
			stmt.setObject(	1, evt.getCode() );
			stmt.setObject(	2, evt.getNom() );
			stmt.setObject(	3, evt.getTheme() );
			stmt.setObject(	4, evt.getLieu() );
			stmt.setObject(	5, evt.getDateDebut() );
			stmt.setObject(	6, evt.getDateFin() );
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
//			rs = stmt.getGeneratedKeys();
//			rs.next();
//			personne.setId( rs.getObject( 1, Integer.class ) );
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}

		// Insère les telephones
//		daoTelephone.insererPourPersonne( personne );
		
		// Retourne l'identifiant
//		return personne.getId();
	}

	
//	public void modifier( Personne personne )  {
//
//		Connection			cn		= null;
//		PreparedStatement	stmt	= null;
//		String 				sql;
//
//		try {
//			cn = dataSource.getConnection();
//
//			// Modifie le personne
//			sql = "UPDATE personne SET idcategorie = ?, nom = ?, prenom = ? WHERE idpersonne =  ?";
//			stmt = cn.prepareStatement( sql );
//			stmt.setObject( 1, personne.getCategorie().getId() );
//			stmt.setObject( 2, personne.getNom() );
//			stmt.setObject( 3, personne.getPrenom() );
//			stmt.setObject( 4, personne.getId() );
//			stmt.executeUpdate();
//			
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		} finally {
//			UtilJdbc.close( stmt, cn );
//		}
//
//		// Modifie les telephones
//		daoTelephone.modifierPourPersonne( personne );
//	}
//
//	
//	public void supprimer( int idPersonne )  {
//
//		Connection			cn		= null;
//		PreparedStatement	stmt	= null;
//		String 				sql;
//
//		// Supprime les telephones
//		daoTelephone.supprimerPourPersonne( idPersonne );
//
//		try {
//			cn = dataSource.getConnection();
//
//			// Supprime le personne
//			sql = "DELETE FROM personne WHERE idpersonne = ? ";
//			stmt = cn.prepareStatement(sql);
//			stmt.setObject( 1, idPersonne );
//			stmt.executeUpdate();
//
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		} finally {
//			UtilJdbc.close( stmt, cn );
//		}
//	}
//
//	
	public Evenement retrouver( int idEvenement )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM evenement WHERE code = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, idEvenement);
            rs = stmt.executeQuery();

            if ( rs.next() ) {
                return construireEvenement(rs);
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
//
//	
//	public List<Personne> listerTout()   {
//
//		Connection			cn		= null;
//		PreparedStatement	stmt	= null;
//		ResultSet 			rs 		= null;
//		String				sql;
//
//		try {
//			cn = dataSource.getConnection();
//
//			sql = "SELECT * FROM personne ORDER BY nom, prenom";
//			stmt = cn.prepareStatement(sql);
//			rs = stmt.executeQuery();
//			
//			List<Personne> liste = new ArrayList<>();
//			while (rs.next()) {
//				liste.add( construirePersonne(rs, false) );
//			}
//			return liste;
//
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		} finally {
//			UtilJdbc.close( rs, stmt, cn );
//		}
//	}
//
//    
//    public int compterPourCategorie( int idCategorie ) {
//    	
//		Connection			cn		= null;
//		PreparedStatement	stmt 	= null;
//		ResultSet 			rs		= null;
//
//		try {
//			cn = dataSource.getConnection();
//            String sql = "SELECT COUNT(*) FROM personne WHERE idcategorie = ?";
//            stmt = cn.prepareStatement( sql );
//            stmt.setObject( 1, idCategorie );
//            rs = stmt.executeQuery();
//
//            rs.next();
//            return rs.getInt( 1 );
//
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		} finally {
//			UtilJdbc.close( rs, stmt, cn );
//		}
//    }
//	
//	
//	// Méthodes auxiliaires
//	
//    protected Personne construirePersonne( ResultSet rs, boolean flagComplet ) throws SQLException {
//
//		Personne personne = new Personne();
//		personne.setId(rs.getObject( "idpersonne", Integer.class ));
//		personne.setNom(rs.getObject( "nom", String.class ));
//		personne.setPrenom(rs.getObject( "prenom", String.class ));
//		personne.setCategorie( daoCategorie.retrouver( rs.getObject("idcategorie", Integer.class) ) );
//
//		if ( flagComplet ) {
//			personne.getTelephones().addAll( daoTelephone.listerPourPersonne( personne ) );
//		}
//		
//		return personne;
//	}
	
	public List<Evenement> listerTout()   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM evenement ORDER BY nom";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			System.out.println("lister tout");
			
			List<Evenement> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construireEvenement(rs) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	  protected Evenement construireEvenement( ResultSet rs) throws SQLException {

			Evenement evenement = new Evenement();
			evenement.setCode(rs.getObject( "code", String.class ));
			evenement.setNom(rs.getObject( "nom", String.class ));
			//evenement.setDateDebut(rs.getObject( "prenom", String.class ));
			//evenement.setCategorie( daoCategorie.retrouver( rs.getObject("idcategorie", Integer.class) ) );

			return evenement;
		}

}
