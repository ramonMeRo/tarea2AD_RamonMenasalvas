package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import entidades.Estancia;
import entidades.Parada;
import entidades.Peregrino;

public class EstanciaDAO {

	public EstanciaDAO() {

	}

	public boolean insertarEstancia(Estancia estancia, Peregrino peregrino, Parada parada) {
		boolean insertada = false;
		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		try {
			ps = con.prepareStatement(
					"insert into estancias (id, idPeregrino, idParada, fecha, vip) values (?, ?, ?, ?, ?)");
			ps.setLong(1, estancia.getId());
			ps.setLong(2, peregrino.getId());
			ps.setLong(3, parada.getId());
			ps.setDate(4, Date.valueOf(estancia.getFecha()));
			ps.setBoolean(5, estancia.isVip());
			
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
		ParadaDAO parada = new ParadaDAO();
		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		try {
			ps = con.prepareStatement("select * from estancias where idPeregrino = ?");
			ps.setLong(1, peregrino.getId());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Estancia estancia = new Estancia();
				estancia.setId(rs.getLong("id"));
				estancia.setPeregrino(peregrino);
				estancia.setParada(parada.obtenerParada(rs.getLong("idParada")));
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
