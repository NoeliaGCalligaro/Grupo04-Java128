package Clases;

import java.util.ArrayList;
import java.util.List;

public class Ronda {
	
	private List<Partido> partidos;
	private int numRonda;
	
	public Ronda(int numRonda) {
		this.partidos = new ArrayList<Partido>();
		this.numRonda=numRonda;
	}
	
	public List<Partido> getPartidos() {
		return this.partidos;
	}
	
	public int getNumRonda() {
		return this.numRonda;
	}
	
	
}
