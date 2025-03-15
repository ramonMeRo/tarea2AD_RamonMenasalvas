package entidades;

import java.time.LocalDate;
import java.util.Objects;

public class Visita {

	private long id;
	private long idPeregrino;
	private long idParada;
	private LocalDate fecha;

	public Visita() {
	}

	public Visita(long id, long idPeregrino, long idParada, LocalDate fecha) {
		this.id = id;
		this.idPeregrino = idPeregrino;
		this.idParada = idParada;
		this.fecha = fecha;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdPeregrino() {
		return idPeregrino;
	}

	public void setIdPeregrino(long idPeregrino) {
		this.idPeregrino = idPeregrino;
	}

	public long getIdParada() {
		return idParada;
	}

	public void setIdParada(long idParada) {
		this.idParada = idParada;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	@Override
	public int hashCode() {
		return Objects.hash(fecha, id, idParada, idPeregrino);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Visita other = (Visita) obj;
		return Objects.equals(fecha, other.fecha) && id == other.id && idParada == other.idParada
				&& idPeregrino == other.idPeregrino;
	}

	@Override
	public String toString() {
		return "Visita [id=" + id + ", idPeregrino=" + idPeregrino + ", idParada=" + idParada + ", fecha=" + fecha
				+ "]";
	}

}
