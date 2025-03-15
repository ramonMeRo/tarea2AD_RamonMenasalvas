package Vista;

import dao.ParadaDAO;
import entidades.Parada;
import entidades.Usuario;

public class Presentaciones {
	
	public static void mostrarParada(Usuario usuario) {
		Parada parada =ParadaDAO.getParadaDAO().obtenerParada(usuario);
		System.out.println(parada.toString());
	}

}
