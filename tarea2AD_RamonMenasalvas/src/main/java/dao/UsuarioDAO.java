package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bd.ConexionBD;
import entidades.Perfil;
import entidades.Usuario;

public class UsuarioDAO {
	
	private static UsuarioDAO instance;
	Connection con;

	private UsuarioDAO(Connection con) {
		if (this.con == null)
			this.con = con;
	}

	public static UsuarioDAO getUsuarioDAO() {
		if (instance == null)
			instance = new UsuarioDAO(ConexionBD.getInstance().getConnection());
		return instance;
	}


	public boolean insertarUsuario(Usuario usuario) {
		boolean insertadas = false;
		PreparedStatement ps;
		if (comprobarUsuario(usuario.getNombre()) == null) {
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


	public String comprobarUsuario(String nombreUs) {
		String nombre = null;
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select * from usuarios where nombre = ?");
			ps.setString(1, nombreUs);

			ResultSet rs = ps.executeQuery();

			if (rs.next())
				nombre = rs.getString("nombre");
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nombre;
	}

	public Usuario obtenerUsuario(String nombre) {
		PreparedStatement ps;
		Usuario usuario = new Usuario();
		try {
			ps = con.prepareStatement("select * from usuarios where nombre = ?");
			ps.setString(1, nombre);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				long idUs = rs.getLong("id");
				String nombreU = rs.getString("nombre");
				String passwordU = rs.getString("password");
				String tipoPerfil = rs.getString("perfil");
				Perfil perfilUs = (tipoPerfil.equals("parada")? Perfil.PARADA: Perfil.PEREGRINO); 
				usuario = new Usuario(idUs, nombreU, passwordU, perfilUs);
				return usuario;
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public long obtenerIdUsuario() {
		PreparedStatement ps;
		try {
			ps = con.prepareStatement("select max(id) as id from usuarios");
			ResultSet rs = ps.executeQuery();
			
			if(rs.next())
				return rs.getLong("id");
			
			rs.close();
			ps.close();
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}


}
