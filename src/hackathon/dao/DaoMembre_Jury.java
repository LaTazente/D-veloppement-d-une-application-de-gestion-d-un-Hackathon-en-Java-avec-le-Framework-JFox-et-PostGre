package hackathon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import hackathon.data.Jury;
import hackathon.data.Membre_jury;
import jfox.jdbc.UtilJdbc;

public class DaoMembre_Jury {


	// Champs

	@Inject
	private DataSource		dataSource;
	@Inject
	private DaoPersonne		daoPersonne;
	@Inject
	private DaoGroupe daoGroupe;
	
	// Actions

	public String inserer( Membre_jury courant ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;
		
		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO membre_jury ( code_membre, nom,prenom,email, telephone,profession, id_jury ) VALUES( ?, ?, ?, ?, ?, ?,?) ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1, courant.getCode_membre() );
			stmt.setObject( 2, courant.getNom() );
			stmt.setObject( 3, courant.getPrenom() );
			stmt.setObject( 4, courant.getEmail() );
			stmt.setObject( 5, courant.getTelephone() );
			stmt.setObject( 6, courant.getProfession() );
			stmt.setObject( 7, courant.getId_jury().getId_jury());
			//stmt.setObject( 3, jury.getFlagSiege() );
			//stmt.setObject( 4, jury.getPersonne() == null ? null : jury.getPersonne().getId() );
			stmt.executeUpdate();

			// Récupère l'identifiant généré par le SGBD
//			rs = stmt.getGeneratedKeys();
//			rs.next();
//			courant.setCode_membre( rs.getObject( 1, Integer.class)+ "");
			return courant.getCode_membre();
	
		} catch ( SQLException e ) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	/*public void modifier( Membre_jury courant ) {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "UPDATE jury SET nom = ?, prenom= ?, email= ?, telehone= ?, profession = ?, id_jury= ? WHERE code_membre =  ?";
			stmt = cn.prepareStatement( sql );
			Jury membre_jury = null;
			stmt.setObject( 1, courant.getNom() );
			 stmt.setObject( 2, jury.getAnneeCreation() );
			stmt.setObject( 4, jury.getPersonne() == null ? null : jury.getPersonne().getId() );
			stmt.setObject( 2, courant.getId_jury() );
			stmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}*/


	public void supprimer( Membre_jury jury ) {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM hackathon.membre_jury WHERE code_membre = ? ";
			stmt = cn.prepareStatement( sql );
			stmt.setObject( 1,jury.getCode_membre() );
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	
	public void modifier(Membre_jury jury) {
		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "UPDATE membre_jury SET nom = ?, prenom= ?, email= ?, telephone= ?, profession = ?, id_jury= ? WHERE code_membre =  ?";
			stmt = cn.prepareStatement( sql );
			
			
			
			stmt.setObject( 1, jury.getNom() );
			
			stmt.setObject( 2, jury.getPrenom() );
			
			stmt.setObject( 3, jury.getEmail() );
			
			stmt.setObject( 4, jury.getTelephone() );
			
			stmt.setObject( 5, jury.getProfession() );
			
			stmt.setObject( 6, jury.getId_jury().getId_jury() );
			
			stmt.setObject( 7, jury.getCode_membre() );
			stmt.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
		
	}
	

	
	public Membre_jury retrouver( String i ) {

		Connection			cn 		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM membre_jury WHERE code_membre = ?";
			stmt = cn.prepareStatement( sql );
			stmt.setString(1, i);
			rs = stmt.executeQuery();

			if ( rs.next() ) {
				return construireMembre_jury( rs );
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}


	public List<Membre_jury> listerTout() {

		Connection			cn 		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM hackathon.membre_jury ORDER BY nom";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();

			List<Membre_jury> liste = new LinkedList<>();
			while (rs.next()) {
				liste.add( construireMembre_jury( rs ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	
    /*private Membre_jury construireMembre_jury(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}*/


	public int compterPourPersonne( int idPersonne ) {
    	
		Connection			cn		= null;
		PreparedStatement	stmt 	= null;
		ResultSet 			rs		= null;

		try {
			cn = dataSource.getConnection();
            String sql = "SELECT COUNT(*) FROM jury WHERE idpersonne = ?";
            stmt = cn.prepareStatement( sql );
            stmt.setObject( 1, idPersonne );
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
	
    protected Membre_jury construireMembre_jury( ResultSet rs ) throws SQLException {
		Membre_jury jury = new 	Membre_jury();
		jury.setCode_membre( rs.getObject( 1, String.class ) );
		jury.setNom( rs.getObject( 2, String.class ) );
		jury.setPrenom( rs.getObject( 3, String.class ) );
		jury.setEmail( rs.getObject( 4, String.class ) );
		jury.setTelephone( rs.getObject( 5, String.class ) );
		jury.setProfession( rs.getObject( 6, String.class ) );
		//jury.setId_jury( rs.getObject( 7, String.class ) );
		// Integer idPersonne = rs.getObject( "idpersonne", Integer.class );
//		if ( idPersonne != null ) {
//		    jury.setPersonne( daoPersonne.retrouver( idPersonne ) );
//		}
		return jury;
	}


    public List<Membre_jury> listeJury(){
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet 			rs		= null;
		String				sql;
		//System.out.println("on est ici");
		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM hackathon.jury";
			stmt = cn.prepareStatement( sql );
			rs = stmt.executeQuery();
			List<Membre_jury> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add( construireMembre_jury( rs ) );
			}
			return liste;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}	
	}


	public Membre_jury retrouver(int parseInt) {
		// TODO Auto-generated method stub
		return null;
	}
}
