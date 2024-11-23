package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entidades.Perfil;
import entidades.Usuario;

public class UsuarioDAO {

	public UsuarioDAO() {

	}

	public boolean insertarUsuario(Usuario usuario) {
		boolean insertadas = false;
		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		if (comprobarUsuario(usuario) == null) {
			try {
				if (con != null && !con.isClosed()) {

					ps = con.prepareStatement("insert into usuarios (nombre, password, perfil) values (?,?,?)");
					ps.setString(1, usuario.getNombre());
					ps.setString(2, usuario.getPassword());
					ps.setString(3, String.valueOf(usuario.getPerfil()));
					ps.executeUpdate();
					ps.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return insertadas;
	}

	// Comrpueba que el usuario que se le paso por parametro exista, si es asi
	// devuelve el nombre, si no, null
	public String comprobarUsuario(Usuario usuario) {
		String nombre = null;
		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		try {
			ps = con.prepareStatement("select * from usuarios where nombre = ?");
			ps.setString(1, usuario.getNombre());

			ResultSet rs = ps.executeQuery();

			if (rs.next())
				nombre = rs.getString("nombre");
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nombre;
	}

	public Usuario obtenerUsuario(long id) {
		PreparedStatement ps;
		ConexionBD conexion = ConexionBD.getInstance();
		Connection con = conexion.getConnection();
		try {
			ps = con.prepareStatement("select * from usuarios where id = ?");
			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				long idUs = rs.getLong("id");
				String nombre = rs.getString("nombre");
				String password = rs.getString("password");
				String tipoPerfil = rs.getString("perfil");
				Perfil perfilUs = (tipoPerfil.equals("parada")? Perfil.PARADA: Perfil.PEREGRINO); // Perfil.valueOf(tipoPerfil);
				Usuario usuario = new Usuario(idUs, nombre, password, perfilUs);
				return usuario;
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
