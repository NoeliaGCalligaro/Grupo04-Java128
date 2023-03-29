package TrabajoPracticoEntrega01;
//Cambio para ver en eclipse 
public class Pronostico {
	private String resultado;
	private String equipo;
	private String jugador;

	public Pronostico(String resultado, String equipo, String jugador) {
		super();
		this.resultado = resultado;
		this.equipo = equipo;
		this.jugador=jugador;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getEquipo() {
		return equipo;
	}

	public void setEquipo(String equipo) {
		this.equipo = equipo;

	}
	
	public String getJugador() {
		return jugador;
	}

	public void setJugador(String jugador) {
		this.jugador = jugador;

	}

	

}
