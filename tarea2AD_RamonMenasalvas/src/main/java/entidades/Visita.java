package entidades;

import java.time.LocalDate;
import java.util.Objects;

public class Visita {
	
	private long idPeregrino;
	private long idParada;
	private LocalDate fecha;
	
	public Visita() {
	}

	public Visita(long idPeregrino, long idParada, LocalDate fecha) {
		this.idPeregrino = idPeregrino;
		this.idParada = idParada;
		this.fecha = fecha;
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
		return Objects.hash(fecha, idParada, idPeregrino);
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
		return Objects.equals(fecha, other.fecha) && idParada == other.idParada && idPeregrino == other.idPeregrino;
	}

	@Override
	public String toString() {
		return "Visita [idPeregrino=" + idPeregrino + ", idParada=" + idParada + ", fecha=" + fecha + "]";
	}
	
	
	
	
}
