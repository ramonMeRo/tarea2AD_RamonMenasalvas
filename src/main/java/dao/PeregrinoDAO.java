package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import entidades.Peregrino;

public class PeregrinoDAO {

	public PeregrinoDAO() {

	}

	public boolean insertarPeregrino(Peregrino peregrino, long idUs, long idCa) {
		boolean insertado = false;
		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		UsuarioDAO user = new UsuarioDAO();
		CarnetDAO carnet = new CarnetDAO();
		try {
			ps = con.prepareStatement(
					"insert into peregrinos (id, idUsuario, idCarnet, nombreCompleto, nacionalidad) values (?, ?, ?, ?, ?)");
			ps.setLong(1, peregrino.getId());
			ps.setLong(2, user.obtenerUsuario(idUs).getId());
			ps.setLong(3, carnet.obtenerCarnet(idCa).getId());
			ps.setString(4, peregrino.getNombre());
			ps.setString(5, peregrino.getNacionalidad());
			ps.executeUpdate();
			
			ps.close();
			insertado = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return insertado;
	}
	
	public Set<Peregrino> obtenerPeregrinos() {
		Set<Peregrino> listaPeregrinos = new HashSet<Peregrino>();
		CarnetDAO carnet = new CarnetDAO();
		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		try {
			ps = con.prepareStatement("select * from peregrinos");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Peregrino peregrino = new Peregrino();
				peregrino.setId(rs.getLong("id"));
				peregrino.setNombre(rs.getString("nombreCompleto"));
				peregrino.setNacionalidad(rs.getString("nacionalidad"));
				peregrino.setCarnet(carnet.obtenerCarnet(rs.getLong("idCarnet")));
				listaPeregrinos.add(peregrino);
			}
			rs.close();
			ps.close();
			return listaPeregrinos;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Peregrino seleccionarPeregrino(long id) {
		Peregrino peregrino = new Peregrino();
		CarnetDAO carnet = new CarnetDAO();
		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		try {
			ps = con.prepareStatement("select * from peregrinos where id = ?");
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				peregrino.setId(rs.getLong("id"));
				peregrino.setNombre(rs.getString("nombreCompleto"));
				peregrino.setNacionalidad(rs.getString("nacionalidad"));
				peregrino.setCarnet(carnet.obtenerCarnet(rs.getLong("idCarnet")));
				
			}
			rs.close();
			ps.close();
			return peregrino;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
