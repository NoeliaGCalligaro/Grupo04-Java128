package Clases;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Persona {
	
	private int codigo;
	private String nombre;
	private List<Pronostico> pronostico;
	private HashMap<Integer, Integer> puntaje;
	
	public Persona(int cod, String nombre) {
		this.codigo=cod;
		this.nombre=nombre;
		this.pronostico = new ArrayList<Pronostico>();
		this.puntaje=new HashMap<Integer, Integer>();
	}
	
	public int getCodigo() {
		return this.codigo;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public HashMap<Integer, Integer> getPuntaje() {
		return this.puntaje;
	}
	
	public int getPuntaje(int ronda) {
		return this.puntaje.get(ronda);
	}
	
	public List<Pronostico> getPronostico() {
		return this.pronostico;
	}
	
	public void acerto(int ronda, int punto) {
		puntaje.replace(ronda, puntaje.get(ronda) + punto);
	}
	
	public int getPuntajeTotal() {
		int cont=0;
		for (int pun : puntaje.values()) {
			cont+=pun;
		}
		return cont;
	}
}
