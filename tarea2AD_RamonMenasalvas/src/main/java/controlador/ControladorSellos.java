package controlador;

import java.time.LocalDate;

import dao.CarnetDAO;
import dao.EstanciaDAO;
import dao.ParadaDAO;
import entidades.Estancia;
import entidades.Parada;
import entidades.Peregrino;
import entidades.Usuario;

public class ControladorSellos {
	
	public static boolean sellarCarnet(Peregrino peregrino, boolean estancia) {

		if (CarnetDAO.getCarnetDAO().actualizarCarnet(peregrino.getId(), estancia))
			return true;
		else
			return false;
	}
	
	public static boolean guardarEstancia(Peregrino peregrino, Usuario user, String tipo, LocalDate fecha) {
		Estancia estancia = new Estancia();
		Parada parada = ParadaDAO.getParadaDAO().obtenerParada(user);
		estancia.setFecha(fecha);
		if(tipo.equalsIgnoreCase("si"))
		estancia.setVip(true);
		else
			estancia.setVip(false);
		
		return EstanciaDAO.getEstanciaDAO().insertarEstancia(estancia, peregrino, parada);
		
	}
}
