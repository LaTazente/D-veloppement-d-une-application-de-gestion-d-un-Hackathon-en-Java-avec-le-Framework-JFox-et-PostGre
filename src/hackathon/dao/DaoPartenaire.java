package hackathon.dao;

import java.sql.CallableStatement;
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

public class DaoPartenaire {
	
	@Inject
	private DataSource		dataSource;
	//@Inject
	//private DaoGroupe daoGroupe;
    //	private DaoCompte		daoCompte;
	
	//PreparedStatement	stmt	= null;
	String				sql;
	
	public List<Partenaire> listePartenaire(){
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		//System.out.println("on est ici");
		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM hackathon.partenaire ORDER BY nom_partenaire";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			List<Partenaire> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construirePartenaire( rs ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
		
		
	}
	
	public String inserer( Partenaire p )  {
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;

		try {
			cn = dataSource.getConnection();
			System.out.println("Qui veut creer des partenaires?");
			
			
			// Insère le personne
			sql = "INSERT INTO hackathon.partenaire ( nom_partenaire, description ) VALUES ( ?, ? )";
			stmt=cn.prepareStatement(sql);
			stmt.setObject(	1, p.getNom() );
			stmt.setObject(	2, p.getDescription() );
			//stmt.setObject(7, p.getIdGroupe().getId_groupe());
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
			//rs = stmt.getGeneratedKeys();
			//rs.next();
			//p.setId( rs.getObject( 1, Integer.class )+"" ); 
	
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
		
		// Retourne l'identifiant
		return p.getNom();
	}
	
	public Partenaire retrouver( String nom_partenaire )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs 		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();

			sql = "SELECT * FROM hackathon.partenaire WHERE nom_partenaire = ?";
            stmt = cn.prepareStatement(sql);
            stmt.setObject( 1, nom_partenaire);
            rs = stmt.executeQuery();

            if ( rs.next() ) {
                return construirePartenaire(rs );
            } else {
            	return null;
            }
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public void supprimer( String nom_partenaire )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Supprime le personne
			sql = "DELETE FROM hackathon.partenaire WHERE nom_partenaire = ? ";
			stmt = cn.prepareStatement(sql);
			stmt.setObject( 1, nom_partenaire );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	
	public void modifier( Partenaire p )  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String 				sql;

		try {
			cn = dataSource.getConnection();

			// Modifie le personne
			sql = "UPDATE hackathon.partenaire SET nom_partenaire = ?, description = ? WHERE nom_partenaire =  ?";
			stmt = cn.prepareStatement( sql );
			//stmt.setObject( 1, Integer.parseInt(p.getId()) );
			stmt.setObject( 1, p.getNom() );
			stmt.setObject( 2, p.getDescription() );
			stmt.setObject( 3, p.getNom() );
			//stmt.setObject( 7, p.getIdGroupe().getId_groupe() );
			stmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	
	private Partenaire construirePartenaire(ResultSet rs) throws SQLException {
		Partenaire partenaire = new Partenaire();
		partenaire.setNom(rs.getObject("nom_partenaire", String.class)+"");
		partenaire.setDescription(rs.getObject("description", String.class));
		//participant.setIdGroupe(daoGroupe.retrouver(rs.getObject("id_groupe", String.class)));
		//System.out.println(participant);
		return partenaire;
	}
	
	public List<Partenaire> recherche_partenaires( String idEvenement )  {
    	
		Connection			cn		= null;
		CallableStatement	stmt	= null;
		String 				sql;
		ResultSet 			rs		= null;

		try {
			cn = dataSource.getConnection();

			// Supprime le personne
			sql = "{ CALL partenaire_recherche_partenaires( ? ) }";
			stmt = cn.prepareCall(sql);
			stmt.setObject( 1, idEvenement );
			rs = stmt.executeQuery();
			
			List<Partenaire> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construirePartenaire( rs ) );
			}
			return liste;
            
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	

}








/*package hackathon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import jfox.jdbc.UtilJdbc;
import hackathon.data.Partenaire;


public class DaoPartenaire {

	@Inject
	private DataSource		dataSource;

	
	

	public void inserer( Partenaire partenaire ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO partenaire (nom_partenaire, description ) VALUES( ?, ? ) ";
			stmt.setObject( 1, partenaire.getNom());
			stmt.setObject( 2, partenaire.getDescription() );
			stmt.executeUpdate();


	
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close(  stmt, cn );
		}
	}


	public void modifier( Partenaire partenaire ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "UPDATE partenaire SET description = ?  WHERE nom_partenaire =  ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, partenaire.getNom() );
			stmt.setObject( 2, partenaire.getDescription() );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}


	public void supprimer( String string ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM categorie WHERE idcategorie = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, string );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}

	
	public Partenaire retrouver( String string ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM partenaire WHERE nom_partenaire = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setObject(1, string);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireParetnaire( rs );
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public List<Partenaire> listerTout() {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM categorie ORDER BY libelle";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			List<Partenaire> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construireParetnaire( rs ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	
	// Méthodes auxiliaires
	
	protected Partenaire construireParetnaire( ResultSet rs ) throws SQLException {
		Partenaire partenaire = new Partenaire();
		partenaire.setDescription( rs.getObject( "description", String.class ) );
		partenaire.setNom( rs.getObject( "nom_partenaire", String.class ) );
		
		return partenaire;
	}

}*/

