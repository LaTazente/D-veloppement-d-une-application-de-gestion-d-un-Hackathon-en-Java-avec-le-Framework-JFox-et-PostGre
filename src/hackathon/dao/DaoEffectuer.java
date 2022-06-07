package hackathon.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.sql.DataSource;

import hackathon.data.Activite;
import hackathon.data.Groupe;
import jfox.jdbc.UtilJdbc;

public class DaoEffectuer {
	
	@Inject
	private DataSource		dataSource;
	@Inject 
	private DaoGroupe daoGroupe;
	@Inject
	private DaoActivite daoActivite;
	
	//Actions
	public void insererPourGroupe( Groupe g,List<Activite> l)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "INSERT INTO hackathon.effectuer (id_groupe, id_activite) VALUES (?,?)";
			stmt = cn.prepareStatement( sql );
			for( Activite a : l ) {
				//System.out.println(a);
				stmt.setObject( 1, g.getId_groupe() );
				stmt.setObject( 2, a.getId_activite() );
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	
	public void supprimerPourGroupe( Groupe g,List<Activite> l)  {

		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		String				sql;

		try {
			cn = dataSource.getConnection();
			sql = "DELETE FROM hackathon.effectuer WHERE id_groupe = ? AND id_activite = ?";
			stmt = cn.prepareStatement( sql );
			for( Activite a : l ) {
				//System.out.println(a);
				stmt.setObject( 1, g.getId_groupe() );
				stmt.setObject( 2, a.getId_activite() );
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( stmt, cn );
		}
	}
	
	public List<Groupe> getGroupeDefi(Activite a){
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet			rs		= null;
		String				sql;
		
		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM hackathon.groupe WHERE id_groupe IN (SELECT id_groupe FROM hackathon.effectuer WHERE id_activite = ?)";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, a.getId_activite());
			rs = stmt.executeQuery();

			List<Groupe> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add(daoGroupe.construireGroupe(rs)  );
			}
			return liste;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public List<Activite> getDefisGroupe(Groupe g){
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet			rs		= null;
		String				sql;
		
		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM hackathon.activite WHERE id_activite NOT IN (SELECT id_activite FROM hackathon.effectuer WHERE id_groupe = ?) AND type = ?";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, g.getId_groupe());
			stmt.setObject(2, "défi");
			rs = stmt.executeQuery();

			List<Activite> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add(daoActivite.construireActivite(rs) );
			}
			return liste;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}
	
	public List<Activite> getDefisChoisisGroupe(Groupe g){
		Connection			cn		= null;
		PreparedStatement	stmt	= null;
		ResultSet			rs		= null;
		String				sql;
		
		try {
			cn = dataSource.getConnection();
			sql = "SELECT * FROM hackathon.activite WHERE id_activite IN (SELECT id_activite FROM hackathon.effectuer WHERE id_groupe = ?) AND type = ?";
			stmt = cn.prepareStatement(sql);
			stmt.setObject(1, g.getId_groupe());
			stmt.setObject(2, "défi");
			rs = stmt.executeQuery();

			List<Activite> liste = new ArrayList<>();
			while (rs.next()) {
				liste.add(daoActivite.construireActivite(rs) );
			}
			return liste;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			UtilJdbc.close( rs, stmt, cn );
		}
	}

}
