package Vista;

import dao.CarnetDAO;
import entidades.Carnet;
import entidades.Parada;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CarnetDAO cDAO = new CarnetDAO();
		Parada p = new Parada(1l, "gijon", 'a', "carla");
		Carnet c = new Carnet(1l,p);
	
		cDAO.insertarCarnet(c);
		
	}

}
