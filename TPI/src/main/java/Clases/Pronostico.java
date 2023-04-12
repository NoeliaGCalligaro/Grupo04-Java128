package Clases;

public class Pronostico {

	private String participante1;
	private String participante2;
	private int ronda;
	private String ganador;
	
	public Pronostico(int ronda, String participante1, String participante2, String ganador) {
		this.participante1 = participante1;
		this.participante2 = participante2;
		this.ronda = ronda;
		this.ganador = ganador;
	}
	public String getParticipante1() {
		return participante1;
	}
	public void setParticipante1(String participante1) {
		this.participante1 = participante1;
	}
	public String getParticipante2() {
		return participante2;
	}
	public void setParticipante2(String participante2) {
		this.participante2 = participante2;
	}
	public int getRonda() {
		return ronda;
	}
	public void setRonda(int ronda) {
		this.ronda = ronda;
	}
	public String getGanador() {
		return ganador;
	}
	public void setGanador(String ganador) {
		this.ganador = ganador;
	}
	
	
}
