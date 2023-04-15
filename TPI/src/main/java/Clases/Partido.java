package Clases;

public class Partido {
	
	private String participante1;
	private String participante2;
	private int golesPar1;
	private int golesPar2;
	private String ganador;
	
	public Partido(String par1, String par2, int gol1, int gol2) {
		this.participante1=par1;
		this.participante2=par2;
		this.golesPar1=gol1;
		this.golesPar2=gol2;
		this.ganador=this.quienGano();
	}
	
	public String quienGano() {
		if (this.golesPar1>this.golesPar2) {
			return participante1;
		} else if(this.golesPar1<this.golesPar2) {
			return participante2;
		} else {
			return "Empate";
		}
	}

	public String getParticipante1() {
		return participante1;
	}

	public String getParticipante2() {
		return participante2;
	}

	public int getGolesPar1() {
		return golesPar1;
	}

	public int getGolesPar2() {
		return golesPar2;
	}

	public String getGanador() {
		return ganador;
	}
	
	public String encuentro() {
		return "Partido: " + getParticipante1() + " - " + getParticipante2() + " |";
	}
}
