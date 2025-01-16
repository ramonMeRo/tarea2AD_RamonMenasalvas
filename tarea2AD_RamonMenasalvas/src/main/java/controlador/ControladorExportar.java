package controlador;

import java.io.File;
import java.time.LocalDate;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

import dao.EstanciaDAO;
import dao.ParadaDAO;
import dao.PeregrinoDAO;
import entidades.Estancia;
import entidades.Parada;
import entidades.Peregrino;
import entidades.Usuario;

public class ControladorExportar {

	public static void ExportarCarnet(Peregrino p) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder constructorDocument;
			constructorDocument = dbf.newDocumentBuilder();
			DOMImplementation implementa = constructorDocument.getDOMImplementation();

			// Crea documento XML y elemento documento
			Document documento = implementa.createDocument(null, "Carnet", null);
			Element carnet = documento.getDocumentElement();
			// Instrucciones de procesamiento
			documento.setXmlVersion("1.0");
			ProcessingInstruction instruc = documento.createProcessingInstruction("xml-stylesheet",
					"type=\"text/xml\" href=\"test.xsl\"");
			documento.insertBefore(instruc, carnet);

			// Nodos
			Element id, fechaExp, expedidoEn, peregrino, nombre, nacionalidad, hoy, distanciaTotal, paradas, parada,
					orden, nombreP, region, estancias, estancia, idE, fecha, paradaE, vip;
			Text idT, fechaExpT, expedidoEnT, nombreT, nacionalidadT, hoyT, distanciaTotalT, ordenT, nombrePT, regionT,
					estanciaT, idET, fechaT, paradaET, vipT;

			// Creado elementos
			id = documento.createElement("id");
			carnet.appendChild(id);
			idT = documento.createTextNode(p.getCarnet().getId().toString());
			id.appendChild(idT);
			fechaExp = documento.createElement("fechaExp");
			carnet.appendChild(fechaExp);
			fechaExpT = documento.createTextNode(p.getCarnet().getFechaExp().toString());
			fechaExp.appendChild(fechaExpT);
			expedidoEn = documento.createElement("expedidoEn");
			carnet.appendChild(expedidoEn);
			expedidoEnT = documento.createTextNode(p.getCarnet().getParadaInicial().toString());
			expedidoEn.appendChild(expedidoEnT);
			peregrino = documento.createElement("peregrino");
			carnet.appendChild(peregrino);
			nombre = documento.createElement("nombre");
			peregrino.appendChild(nombre);
			nombreT = documento.createTextNode(p.getNombre());
			nombre.appendChild(nombreT);
			nacionalidad = documento.createElement("nacionalidad");
			peregrino.appendChild(nacionalidad);
			nacionalidadT = documento.createTextNode(p.getNacionalidad());
			nacionalidad.appendChild(nacionalidadT);
			hoy = documento.createElement("hoy");
			carnet.appendChild(hoy);
			hoyT = documento.createTextNode(LocalDate.now().toString());
			hoy.appendChild(hoyT);
			distanciaTotal = documento.createElement("distanciaTotal");
			carnet.appendChild(distanciaTotal);
			distanciaTotalT = documento.createTextNode(p.getCarnet().getDistancia().toString());
			distanciaTotal.appendChild(distanciaTotalT);
			paradas = documento.createElement("paradas");
			carnet.appendChild(paradas);
			int cont = 1;
			for (Parada paradasIndice : p.getParadas()) {
				parada = documento.createElement("parada");
				paradas.appendChild(parada);
				orden = documento.createElement("orden");
				parada.appendChild(orden);
				ordenT = documento.createTextNode(String.valueOf(cont));
				orden.appendChild(ordenT);
				nombreP = documento.createElement("nombreP");
				parada.appendChild(nombreP);
				nombrePT = documento.createTextNode(paradasIndice.getNombre());
				nombreP.appendChild(nombrePT);
				region = documento.createElement("region");
				parada.appendChild(region);
				regionT = documento.createTextNode(String.valueOf(paradasIndice.getRegion()));
				region.appendChild(regionT);
				cont++;
			}
			estancias = documento.createElement("estancias");
			carnet.appendChild(estancias);
			for (Estancia estanciasIndice : p.getEstancias()) {
				estancia = documento.createElement("estancia");
				estancias.appendChild(estancia);
				estanciaT = documento.createTextNode("");
				estancia.appendChild(estanciaT);
				idE = documento.createElement("idE");
				estancia.appendChild(idE);
				idET = documento.createTextNode(estanciasIndice.getId().toString());
				idE.appendChild(idET);
				fecha = documento.createElement("fecha");
				estancia.appendChild(fecha);
				fechaT = documento.createTextNode(estanciasIndice.getFecha().toString());
				fecha.appendChild(fechaT);
				paradaE = documento.createElement("paradaE");
				estancia.appendChild(paradaE);
				paradaET = documento.createTextNode(estanciasIndice.getParada().getNombre());
				paradaE.appendChild(paradaET);
				if (estanciasIndice.isVip()) {
					vip = documento.createElement("vip");
					estancia.appendChild(vip);
					vipT = documento.createTextNode("");
					vip.appendChild(vipT);
				}

			}
			System.out.println("----- Generando el fichero XML");
			Source fuente = new DOMSource(documento);
			File fichero = new File(p.getNombre() + "_peregrino.xml");
			Result resultado = new StreamResult(fichero);
			TransformerFactory fabricaTransformador = TransformerFactory.newInstance();
			Transformer transformador = fabricaTransformador.newTransformer();
			transformador.transform(fuente, resultado);
		} catch (ParserConfigurationException ex) {
			System.out.println("Error: " + ex.getMessage());
		} catch (TransformerConfigurationException ex) {
			System.out.println("Error: " + ex.getMessage());
		} catch (TransformerException ex) {
			System.out.println("Error: " + ex.getMessage());
		}

	}

	public static void ExportarCarnetManualmente(Usuario usuario) {
		Peregrino p = new Peregrino();
		p = PeregrinoDAO.getPeregrinoDAO().obtenerPeregrinoConUsuario(usuario);
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder constructorDocument;
			constructorDocument = dbf.newDocumentBuilder();
			DOMImplementation implementa = constructorDocument.getDOMImplementation();

			// Crea documento XML y elemento documento
			Document documento = implementa.createDocument(null, "Carnet", null);
			Element carnet = documento.getDocumentElement();
			// Instrucciones de procesamiento
			documento.setXmlVersion("1.0");
			ProcessingInstruction instruc = documento.createProcessingInstruction("xml-stylesheet",
					"type=\"text/xml\" href=\"test.xsl\"");
			documento.insertBefore(instruc, carnet);

			// Nodos
			Element id, fechaExp, expedidoEn, peregrino, nombre, nacionalidad, hoy, distanciaTotal, paradas, parada,
					orden, nombreP, region, estancias, estancia, idE, fecha, paradaE, vip;
			Text idT, fechaExpT, expedidoEnT, nombreT, nacionalidadT, hoyT, distanciaTotalT, ordenT, nombrePT, regionT,
					estanciaT, idET, fechaT, paradaET, vipT;

			// Creado elementos
			id = documento.createElement("id");
			carnet.appendChild(id);
			idT = documento.createTextNode(p.getCarnet().getId().toString());
			id.appendChild(idT);
			fechaExp = documento.createElement("fechaExp");
			carnet.appendChild(fechaExp);
			fechaExpT = documento.createTextNode(p.getCarnet().getFechaExp().toString());
			fechaExp.appendChild(fechaExpT);
			expedidoEn = documento.createElement("expedidoEn");
			carnet.appendChild(expedidoEn);
			expedidoEnT = documento.createTextNode(p.getCarnet().getParadaInicial().toString());
			expedidoEn.appendChild(expedidoEnT);
			peregrino = documento.createElement("peregrino");
			carnet.appendChild(peregrino);
			nombre = documento.createElement("nombre");
			peregrino.appendChild(nombre);
			nombreT = documento.createTextNode(p.getNombre());
			nombre.appendChild(nombreT);
			nacionalidad = documento.createElement("nacionalidad");
			peregrino.appendChild(nacionalidad);
			nacionalidadT = documento.createTextNode(p.getNacionalidad());
			nacionalidad.appendChild(nacionalidadT);
			hoy = documento.createElement("hoy");
			carnet.appendChild(hoy);
			hoyT = documento.createTextNode(LocalDate.now().toString());
			hoy.appendChild(hoyT);
			distanciaTotal = documento.createElement("distanciaTotal");
			carnet.appendChild(distanciaTotal);
			distanciaTotalT = documento.createTextNode(p.getCarnet().getDistancia().toString());
			distanciaTotal.appendChild(distanciaTotalT);
			paradas = documento.createElement("paradas");
			carnet.appendChild(paradas);
			int cont = 1;
			for (Parada paradasIndice : p.getParadas()) {
				parada = documento.createElement("parada");
				paradas.appendChild(parada);
				orden = documento.createElement("orden");
				parada.appendChild(orden);
				ordenT = documento.createTextNode(String.valueOf(cont));
				orden.appendChild(ordenT);
				nombreP = documento.createElement("nombreP");
				parada.appendChild(nombreP);
				nombrePT = documento.createTextNode(paradasIndice.getNombre());
				nombreP.appendChild(nombrePT);
				region = documento.createElement("region");
				parada.appendChild(region);
				regionT = documento.createTextNode(String.valueOf(paradasIndice.getRegion()));
				region.appendChild(regionT);
				cont++;
			}
			estancias = documento.createElement("estancias");
			carnet.appendChild(estancias);
			for (Estancia estanciasIndice : p.getEstancias()) {
				estancia = documento.createElement("estancia");
				estancias.appendChild(estancia);
				estanciaT = documento.createTextNode("");
				estancia.appendChild(estanciaT);
				idE = documento.createElement("idE");
				estancia.appendChild(idE);
				idET = documento.createTextNode(estanciasIndice.getId().toString());
				idE.appendChild(idET);
				fecha = documento.createElement("fecha");
				estancia.appendChild(fecha);
				fechaT = documento.createTextNode(estanciasIndice.getFecha().toString());
				fecha.appendChild(fechaT);
				paradaE = documento.createElement("paradaE");
				estancia.appendChild(paradaE);
				paradaET = documento.createTextNode(estanciasIndice.getParada().getNombre());
				paradaE.appendChild(paradaET);
				if (estanciasIndice.isVip()) {
					vip = documento.createElement("vip");
					estancia.appendChild(vip);
					vipT = documento.createTextNode("");
					vip.appendChild(vipT);
				}

			}
			System.out.println("----- Generando el fichero XML");
			Source fuente = new DOMSource(documento);
			File fichero = new File(p.getNombre() + "_peregrino.xml");
			Result resultado = new StreamResult(fichero);
			TransformerFactory fabricaTransformador = TransformerFactory.newInstance();
			Transformer transformador = fabricaTransformador.newTransformer();
			transformador.transform(fuente, resultado);
		} catch (ParserConfigurationException ex) {
			System.out.println("Error: " + ex.getMessage());
		} catch (TransformerConfigurationException ex) {
			System.out.println("Error: " + ex.getMessage());
		} catch (TransformerException ex) {
			System.out.println("Error: " + ex.getMessage());
		}

	}

	public static void exportarParada(Usuario usuario, LocalDate fechaInicio, LocalDate fechaFin) {
		if (fechaInicio.isBefore(fechaFin)) {
			Parada parada = ParadaDAO.getParadaDAO().obtenerParada(usuario);
			Set<Estancia> lista = EstanciaDAO.getEstanciaDAO().obtenerEstanciasEnParada(parada);
			System.out.println(parada.toString() + "\n");
			for (Estancia estancia : lista) {
				if (estancia.getFecha().isAfter(fechaInicio) && estancia.getFecha().isBefore(fechaFin))
					System.out.println(estancia.toString() + "\n");
			}
			System.out.println("entre: " + fechaInicio + " y " + fechaFin);
		}
	}
}
