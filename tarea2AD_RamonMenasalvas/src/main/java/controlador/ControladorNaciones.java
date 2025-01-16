package controlador;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ControladorNaciones {


	private static final File naciones = new File("..\\tarea1AD_RamonMenasalvas\\src\\main\\resources\\paises.xml");
	
	public static void MostrarNaciones() {
	    try {
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder constructorDocumento = dbf.newDocumentBuilder();
	        Document documento = constructorDocumento.parse(naciones);

	        NodeList listaPais = documento.getElementsByTagName("pais");
	        Element pais, id, nombre;
	        
	        int indicePais = 0;

	        while (indicePais < listaPais.getLength()) {
	            pais = (Element) listaPais.item(indicePais);

	            id = (Element) pais.getElementsByTagName("id").item(0);
	            nombre = (Element) pais.getElementsByTagName("nombre").item(0);

	            System.out.println("Pais: "+indicePais);
	            System.out.println("Id: " + id.getTextContent());
	            System.out.println("Nombre: " + nombre.getTextContent() + "\n");

	            indicePais++;
	        }

	    } catch (ParserConfigurationException e) {
	    	// TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (SAXException e) {
	    	// TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (IOException e) {
	    	// TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	}


	public static String ComprobarNacion(int nacion) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder constructorDocumento = dbf.newDocumentBuilder();
			Document documento = constructorDocumento.parse(naciones);

			NodeList listaPais = documento.getElementsByTagName("pais");

			for (int i = 0; i < listaPais.getLength(); i++) {
				Element pais = (Element) listaPais.item(i);

				Node nodoNombre = pais.getElementsByTagName("nombre").item(0);
				if (nodoNombre != null) {
					String nombre = nodoNombre.getTextContent();

					if (i == nacion) {
						return nombre;
					}
				}
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	

	
}
