package entidades;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Parada {

	private Long id;
	private String nombre;
	private char region;
	private String responsable;
	private Set<Peregrino> numPeregrinos = new HashSet<Peregrino>();

	public Parada() {

	}

	public Parada(Long id, String nombre, char region, String responsable) {
		this.id = id;
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
	}

	public Parada(Long id, String nombre, char region, String responsable, Set<Peregrino> numPeregrinos) {
		this.id = id;
		this.nombre = nombre;
		this.region = region;
		this.responsable = responsable;
		this.numPeregrinos = numPeregrinos;
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

	public char getRegion() {
		return region;
	}

	public void setRegion(char region) {
		this.region = region;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public Set<Peregrino> getNumPeregrinos() {
		return numPeregrinos;
	}

	public void setNumPeregrinos(Set<Peregrino> numPeregrinos) {
		this.numPeregrinos = numPeregrinos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nombre, numPeregrinos, region, responsable);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Parada other = (Parada) obj;
		return Objects.equals(id, other.id) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(numPeregrinos, other.numPeregrinos) && region == other.region
				&& Objects.equals(responsable, other.responsable);
	}

	@Override
	public String toString() {
		return "Parada [id=" + id + ", nombre=" + nombre + ", region=" + region + ", responsable=" + responsable
				+ ", numPeregrinos=" + numPeregrinos + "]";
	}

}
