package hackathon.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.batik.anim.timing.Interval;

import hackathon.data.Activite;
import hackathon.data.Personne;
import jfox.jdbc.UtilJdbc;

public class DaoInscrire {
	
	// Champs

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoTelephone	daoTelephone;
	@Inject
	private DaoCategorie	daoCategorie;

	
	// Actions

	public int inserer( Activite a )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			// Insère le personne
			sql = "INSERT INTO hackathon.activite (  nom, type, duree, code) VALUES ( ?, ?, ?, ?)";
			stmt = cn.prepareStatement( sql, Statement.RETURN_GENERATED_KEYS  );
			stmt.setObject(	1, a.getNom() );
			stmt.setObject(	2, a.getType() );
			stmt.setObject(	3, LocalTime.parse(a.getDuree()) );
			stmt.setObject(4, a.getHackathon().getCode());
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			rs = stmt.getGeneratedKeys();
			rs.next();
			a.setId_activite( rs.getObject( 1, Integer.class ) );
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}


		
		// Retourne l'identifiant
		return a.getId_activite();
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
//	public Personne retrouver( int idPersonne )  {
//
//		Connection			cn		= null;
//		PreparedStatement	stmt	= null;
//		ResultSet 			rs 		= null;
//		String				sql;
//
//		try {
//			cn = dataSource.getConnection();
//
//			sql = "SELECT * FROM personne WHERE idpersonne = ?";
//            stmt = cn.prepareStatement(sql);
//            stmt.setObject( 1, idPersonne);
//            rs = stmt.executeQuery();
//
//            if ( rs.next() ) {
//                return construirePersonne(rs, true );
//            } else {
//            	return null;
//            }
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		} finally {
//			UtilJdbc.close( rs, stmt, cn );
//		}
//	}

	
	public List<Activite> listerTout()   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM hackathon.activite ORDER BY nom";
			stmt = cn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			List<Activite> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construireActivite(rs) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public List<Activite> listerDefis()   {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM hackathon.activite WHERE type = ? ORDER BY nom";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, "défi");
			rs = stmt.executeQuery();
			
			List<Activite> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construireActivite(rs) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}

    
    public int compterPourCategorie( int idCategorie ) {
    	
		Connection			cn		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;

		try {
			cn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) FROM personne WHERE idcategorie = ?";
            stmt = cn.prepareStatement( sql );
            stmt.setObject( 1, idCategorie );
            rs = stmt.executeQuery();

            rs.next();
            return rs.getInt( 1 );

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
    }
	
	
	// Méthodes auxiliaires
	
    protected Activite construireActivite( ResultSet rs) throws SQLException {

		Activite a = new Activite();
		a.setId_activite(rs.getObject( "id_activite", Integer.class ));
		a.setNom(rs.getObject("nom", String.class));
		a.setType(rs.getObject("type", String.class));
		a.setDuree(rs.getObject("duree", LocalTime.class).toString());
		a.setHackathon(null);

		
		return a;
	}
	

    public String dureeActivites( String idEvemenent )  {
    	
		Connection			cn		= null;
		CallableStatement	stmt	= null;
		String 				sql;
		ResultSet 			rs		= null;

		try {
			cn = dataSource.getConnection();

			// Supprime le personne
			sql = "{ CALL activite_dureeActivites( ? ) }";
			stmt = cn.prepareCall(sql);
			stmt.setObject( 1, idEvemenent );
			rs = stmt.executeQuery();
			
			rs.next();
            return rs.getString(1);
            
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
    

    public double taux_participation_presence( String idEvemenent )  {
    	
		Connection			cn		= null;
		CallableStatement	stmt	= null;
		String 				sql;
		ResultSet 			rs		= null;

		try {
			cn = dataSource.getConnection();

			// Supprime le personne
			sql = "{ CALL s_inscrire_taux_participation_presence( ? ) }";
			stmt = cn.prepareCall(sql);
			stmt.setObject( 1, idEvemenent );
			rs = stmt.executeQuery();
			
			rs.next();
            return rs.getDouble(1);
            
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
    
 public double taux_participation_absence( String idEvemenent )  {
    	
		Connection			cn		= null;
		CallableStatement	stmt	= null;
		String 				sql;
		ResultSet 			rs		= null;

		try {
			cn = dataSource.getConnection();

			// Supprime le personne
			sql = "{ CALL s_inscrire_taux_participation_absence( ? ) }";
			stmt = cn.prepareCall(sql);
			stmt.setObject( 1, idEvemenent );
			rs = stmt.executeQuery();
			
			rs.next();
            return rs.getDouble(1);
            
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
}
