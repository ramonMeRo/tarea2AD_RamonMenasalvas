package entidades;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Peregrino {
	private Long id;
	private String nombre;
	private String nacionalidad;
	private Carnet carnet;
	private Set<Estancia> estancias = new HashSet<Estancia>();
	private Set<Parada> paradas = new HashSet<Parada>();

	public Peregrino() {

	}

	public Peregrino(Long id, String nombre, String nacionalidad, Carnet carnet, Set<Estancia> numEstancias,
			Set<Parada> numParadas) {
		this.id = id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.carnet = carnet;
		this.estancias = numEstancias;
		this.paradas = numParadas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public Carnet getCarnet() {
		return carnet;
	}

	public void setCarnet(Carnet carnet) {
		this.carnet = carnet;
	}

	public Set<Estancia> getEstancias() {
		return estancias;
	}

	public void setEstancias(Set<Estancia> estancias) {
		this.estancias = estancias;
	}

	public Set<Parada> getParadas() {
		return paradas;
	}

	public void setParadas(Set<Parada> paradas) {
		this.paradas = paradas;
	}

	@Override
	public int hashCode() {
		return Objects.hash(carnet, id, nacionalidad, nombre, estancias, paradas);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Peregrino other = (Peregrino) obj;
		return Objects.equals(carnet, other.carnet) && Objects.equals(id, other.id)
				&& Objects.equals(nacionalidad, other.nacionalidad) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(estancias, other.estancias) && Objects.equals(paradas, other.paradas);
	}

	@Override
	public String toString() {
		return "Peregrino [id=" + id + ", nombre=" + nombre + ", nacionalidad=" + nacionalidad + ", carnet=" + carnet
				+ ", estancias=" + estancias + ", paradas=" + paradas + "]";
	}
	
	
	
}