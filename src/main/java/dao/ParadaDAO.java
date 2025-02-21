package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import bd.ConexionBD;
import entidades.Parada;
import entidades.Usuario;

public class ParadaDAO {
	
	private static ParadaDAO instance;
	Connection con;

	private ParadaDAO(Connection con) {
		if (this.con == null)
			this.con = con;
	}

	public static ParadaDAO getParadaDAO() {
		if (instance == null)
			instance = new ParadaDAO(ConexionBD.getInstance().getConnection());
		return instance;
	}

	public boolean insertarParada(Parada parada) {
		boolean insertada = false;
		PreparedStatement ps;
		if (!compararParada(parada.getNombre(), parada.getRegion())) {
			try {
				if (con == null || con.isClosed())
					con = ConexionBD.getInstance().getConnection();

				ps = con.prepareStatement("insert into paradas (idResponsable, nombre, region) values (?,?,?)");
				ps.setLong(1, obtenerIdResponsable(parada.getResponsable()));
				ps.setString(2, parada.getNombre());
				ps.setString(3, String.valueOf(parada.getRegion()));
				ps.executeUpdate();

				insertada = true;
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return insertada;
	}

	public boolean compararParada(String nombre, char region) {
		boolean encontrada = false;
		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		try {
			ps = con.prepareStatement("select * from paradas where nombre = ? and region = ?");
			ps.setString(1, nombre);
			ps.setString(2, String.valueOf(region));

			ResultSet rs = ps.executeQuery();
			if (rs.next())
				encontrada = true;

			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return encontrada;
	}

	public long obtenerIdResponsable(String nombre) {
		long id = -1;
		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		try {
			ps = con.prepareStatement("select id from usuarios where nombre = ? ");
			ps.setString(1, nombre);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return id = rs.getLong("id");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}

	public Set<Parada> obtenerParadas() {
		Set<Parada> listaParadas = new HashSet<Parada>();
		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		try {
			ps = con.prepareStatement(
					"select p.id, u.nombre, p.nombre, p.region from paradas p inner join usuarios u on p.idResponsable = u.id");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Parada parada = new Parada();
				parada.setId(rs.getLong("p.id"));
				parada.setResponsable(rs.getString("u.nombre"));
				parada.setNombre(rs.getString("p.nombre"));
				parada.setRegion(rs.getString("p.region").charAt(0));
				listaParadas.add(parada);

			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaParadas;
	}
	
	public Set<Parada> obtenerParadasPeregrino(long id) {
		Set<Parada> listaParadas = new HashSet<Parada>();
		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		try {
			ps = con.prepareStatement(
					"select p.id, u.nombre, p.nombre, p.region from paradas p inner join usuarios u on p.idResponsable = u.id");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Parada parada = new Parada();
				parada.setId(rs.getLong("p.id"));
				parada.setResponsable(rs.getString("u.nombre"));
				parada.setNombre(rs.getString("p.nombre"));
				parada.setRegion(rs.getString("p.region").charAt(0));
				listaParadas.add(parada);

			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listaParadas;
	}

	public Parada obtenerParada(Usuario usuario) {
		Parada parada = new Parada();
		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		try {
			ps = con.prepareStatement(
					"select p.id, u.nombre, p.nombre, p.region from paradas p inner join usuarios u on p.idResponsable = u.id where u.id = ?");
			ps.setLong(1, usuario.getId());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				parada.setId(rs.getLong("p.id"));
				parada.setResponsable(rs.getString("u.nombre"));
				parada.setNombre(rs.getString("p.nombre"));
				parada.setRegion(rs.getString("p.region").charAt(0));

			}
			rs.close();
			ps.close();
			return parada;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Parada seleccionarParada(long id) {
		Parada parada = new Parada();
		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		try {
			ps = con.prepareStatement(
					"select p.id, u.nombre, p.nombre, p.region from paradas p inner join usuarios u on p.idResponsable = u.id where p.id = ?");
			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				parada.setId(rs.getLong("p.id"));
				parada.setResponsable(rs.getString("u.nombre"));
				parada.setNombre(rs.getString("p.nombre"));
				parada.setRegion(rs.getString("p.region").charAt(0));
			}
			return parada;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public long obtenerIdParada() {
		long id = 0;
		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		try {
			ps = con.prepareStatement("select max(id) as id from paradas");
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				id = rs.getLong("id");
			
			rs.close();
			ps.close();
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		id++;
		return id;
	}

}
