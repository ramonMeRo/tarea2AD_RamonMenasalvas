package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import bd.ConexionBD;
import entidades.Estancia;
import entidades.Parada;
import entidades.Peregrino;

public class EstanciaDAO {
	
	private static EstanciaDAO instance;
	Connection con;

	private EstanciaDAO(Connection con) {
		if (this.con == null)
			this.con = con;
	}

	public static EstanciaDAO getEstanciaDAO() {
		if (instance == null)
			instance = new EstanciaDAO(ConexionBD.getInstance().getConnection());
		return instance;
	}


	public boolean insertarEstancia(Estancia estancia, Peregrino peregrino, Parada parada) {
		boolean insertada = false;
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(
					"insert into estancias (idPeregrino, idParada, fecha, vip) values (?, ?, ?, ?)");
			ps.setLong(1, peregrino.getId());
			ps.setLong(2, parada.getId());
			ps.setDate(3, Date.valueOf(estancia.getFecha()));
			ps.setBoolean(4, estancia.isVip());
			
			ps.executeUpdate();
			ps.close();
			insertada = true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertada;
	}
	
	public Set<Estancia> obtenerEstanciasDePeregrino(Peregrino peregrino){
		Set<Estancia> listaEstancias = new HashSet<Estancia>();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select * from estancias where idPeregrino = ?");
			ps.setLong(1, peregrino.getId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Estancia estancia = new Estancia();
				estancia.setId(rs.getLong("id"));
				estancia.setPeregrino(peregrino);
				estancia.setParada(ParadaDAO.getParadaDAO().seleccionarParada(rs.getLong("idParada")));
				estancia.setFecha(rs.getDate("fecha").toLocalDate());
				estancia.setVip(rs.getBoolean("vip"));
				
				listaEstancias.add(estancia);
				
			}
			rs.close();
			ps.close();
			return listaEstancias;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Set<Estancia> obtenerEstanciasEnParada(Parada parada){
		Set<Estancia> listaEstancias = new HashSet<Estancia>();
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select id, idPeregrino, fecha, vip from estancias where idParada = ?");
			ps.setLong(1, parada.getId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Estancia estancia = new Estancia();
				estancia.setId(rs.getLong("id"));
				estancia.setPeregrino(PeregrinoDAO.getPeregrinoDAO().seleccionarPeregrinoEstancia(rs.getLong("idPeregrino")));
				estancia.setFecha(rs.getDate("fecha").toLocalDate());
				estancia.setVip(rs.getBoolean("vip"));
				
				listaEstancias.add(estancia);
				
			}
			rs.close();
			ps.close();
			return listaEstancias;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	

}
