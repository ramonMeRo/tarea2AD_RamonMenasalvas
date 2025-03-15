package controlador;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import dao.UsuarioDAO;
import entidades.Perfil;
import entidades.Usuario;

public class ControladorLogin {
	
public static boolean existeUsuario(String nombreUs) {
		
		if(UsuarioDAO.getUsuarioDAO().comprobarUsuario(nombreUs) != null) 
			return false;
		else
			return true;
		
	}
	
	public static Usuario loginAdmin(String nombre, String password) {

		Properties properties = new Properties();
		FileInputStream fis;
		try {
			fis = new FileInputStream("src/main/resources/application.properties");

			properties.load(fis);
			String useradmin = properties.getProperty("adminapp");
			String claveadmin = properties.getProperty("passadminapp");
			fis.close();
			
			if (nombre.equalsIgnoreCase(useradmin) && password.equalsIgnoreCase(claveadmin)) {

				Usuario usuarioAdmin = new Usuario();
				usuarioAdmin.setId(0L);
				usuarioAdmin.setNombre("Administrador");
				usuarioAdmin.setPerfil(Perfil.ADMIN);
				return usuarioAdmin;

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
}
