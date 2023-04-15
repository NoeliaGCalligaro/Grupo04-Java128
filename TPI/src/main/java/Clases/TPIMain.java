package Clases;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import Excepciones.*;

import java.io.IOException;


public class TPIMain {

    public static void main(String[] args) {
    	String resultados = "src\\main\\java\\Archivos\\partidos.txt";
    	String pronosticos = "src\\main\\java\\Archivos\\pronostico.txt"; 
    	int puntosXVictoria = 1;
    	int puntosXEmpate = 2;
    	int puntosXRonda = 3;
    	int puntosXFase = 3;
    	
    	List<Ronda> rondas = cargaResultados(resultados);
    	List<Persona> jugadores = cargaJugadores(pronosticos);
    	cargaPuntos(rondas, jugadores, puntosXVictoria, puntosXEmpate);
    	puntosExtra(rondas, jugadores, puntosXRonda, puntosXFase);
    	print(rondas, jugadores);
    }
    
    public static List<Ronda> cargaResultados(String ruta) {
    	List<Ronda> rondas = new ArrayList<Ronda>();
    	int numRonda=0;
    	Ronda ronda = null; //esto es para que no me de error el if despues
    	try {
    		//tengo que crear todas las rondas primero para evitar ConcurrentModificationException
    		for (String linea: Files.readAllLines(Paths.get(ruta))) {
    			   String[] partes = linea.split(";");
    			   //Aprovecho y reviso que todo esté en orden
    			   chequeoCantCampos(partes, 5, linea);
    			   chequeoGolesInt(partes[3], partes[4], linea);
    			   if (Integer.valueOf(partes[0])!=numRonda) {
    				   numRonda=Integer.valueOf(partes[0]);
    				   ronda = new Ronda(numRonda);
    				   rondas.add(ronda);
    				   
    			   }
    		   }
    		//ahora agrego los partidos a cada ronda
    		for (String linea: Files.readAllLines(Paths.get(ruta))) {
 			   String[] partes = linea.split(";");
 			   
 			   for (Ronda rond : rondas) {
 				   if (Integer.valueOf(partes[0])==rond.getNumRonda()) {
 					  Partido partido = new Partido (partes[1],partes[2],Integer.valueOf(partes[3]),Integer.valueOf(partes[4])); 
 		 			  rond.getPartidos().add(partido);
 				   }
 			   }
 		   }
    	} catch (NoCoincideCantidadCamposException e){
    		e.mensajeError();
    	} catch (GolesNoSonIntException e) {
    		e.mensajeError();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return rondas;
    }
    
    public static List<Persona> cargaJugadores(String ruta) {
    	//insertar pronosticos
    	//se asume que los pronosticos vienen ordenados por numero de persona
    	List<Persona> personas = new ArrayList<Persona>();
    	int codPersona = 0;
    	Persona persona = null;
    	try {
    		for (String linea: Files.readAllLines(Paths.get(ruta))) {
    			   String[] partes = linea.split(";");
    			   
    			   //Chequeo si existe la persona, si no la creo
    			   if (codPersona!=Integer.valueOf(partes[0])) {
    				   persona = new Persona(Integer.valueOf(partes[0]), partes[1]);
    				   personas.add(persona);
    				   codPersona = Integer.valueOf(partes[0]);
    			   }
    		   }
    		
    		for (String linea: Files.readAllLines(Paths.get(ruta))) {
 			   String[] partes = linea.split(";");
 			   
 			   for (Persona pers : personas) {
 				   if (Integer.valueOf(partes[0])==pers.getCodigo()) {
 					  Pronostico pronostico = new Pronostico(Integer.valueOf(partes[2]), partes[3], partes[4], partes[5]);
 		 			  pers.getPronostico().add(pronostico);
 				   }
 			   }
 			   
 		   }
    		
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	return personas;
    }
    
    public static void chequeoCantCampos(String[] partes, int cantEsperada, String linea) throws NoCoincideCantidadCamposException{
    	if (partes.length!=cantEsperada) {
    		throw new NoCoincideCantidadCamposException(linea);
    	}
    }
    
    public static void chequeoGolesInt(String gol1, String gol2, String lineaError) throws GolesNoSonIntException{
    	String enteros= "0123456789";
    	for (int i=0;i<gol1.length();i++) {
    		if (!enteros.contains(gol1.subSequence(0, gol1.length()))) {
    			throw new GolesNoSonIntException(lineaError);
    		}
    	}
    	for (int i=0;i<gol2.length();i++) {
    		if (!enteros.contains(gol2.subSequence(0, gol2.length()))) {
    			throw new GolesNoSonIntException(lineaError);
    		}
    	}	
    }
    
    public static void cargaPuntos(List<Ronda> rondas, List<Persona> personas, int puntosXVictoria, int puntosXEmpate) {
    	//ahora veo si acertaron
    	for (Persona persona : personas) {
    		for (Pronostico pronostico : persona.getPronostico()) {
    			for (Ronda ronda : rondas) {
    				if (!persona.getPuntaje().containsKey(ronda.getNumRonda())) {
    					persona.getPuntaje().put(ronda.getNumRonda(), 0);
    				}
    				
    				if (pronostico.getRonda()==ronda.getNumRonda()) {
    					for (Partido partido : ronda.getPartidos()) {
    						//me fijo si es el partido correcto
    						if (partido.getParticipante1().equals(pronostico.getParticipante1()) && partido.getParticipante2().equals(pronostico.getParticipante2())) {
    							if (partido.getGanador().equalsIgnoreCase(pronostico.getGanador())) {
    								if(pronostico.getGanador().equals("Empate")) {
    									persona.acerto(ronda.getNumRonda(),puntosXEmpate);
    								}else {
    									persona.acerto(ronda.getNumRonda(),puntosXVictoria);	
    								}
    								pronostico.setAcierto();
        						}
    						}
    					}
    				}
    			}
    		}		
    	}
    	
    }
    
    public static void puntosExtra(List<Ronda> rondas, List<Persona> personas, int valorXRonda, int valorXFase ) {
    	List<Ronda> fase1 = new ArrayList<Ronda>();
    	List<Ronda> fase2 = new ArrayList<Ronda>();
    	int length = rondas.size();
    	for(int i=0; i < length; i++ ) {
    		if(i < Math.ceil(length/2)) {
    			fase1.add(rondas.get(i));
    		}
    		else {
    			fase2.add(rondas.get(i));
    		}
    	}	
    	
    	for(Persona persona : personas) {
    		int aciertosF = 0;
    		for(Ronda ronda : fase1) {
    			int aciertosR = 0;
    			for(Pronostico pronostico : persona.getPronostico()) {
    				if(ronda.getNumRonda() == pronostico.getRonda()) {
        				if(pronostico.getAcierto() == true) {
        					aciertosR++;
    					}
    				}
    			}
    			if(ronda.getPartidos().size() == aciertosR) {
    				persona.acerto(ronda.getNumRonda(), valorXRonda);
    				aciertosF++;
    			}
    		}
    		if(aciertosF == fase1.size()) {
    			persona.acerto(aciertosF, valorXFase);
    		}
    	}
    	
    	for(Persona persona : personas) {
    		int aciertosF = 0;
    		for(Ronda ronda : fase2) {
    			int aciertosR = 0;
    			for(Pronostico pronostico : persona.getPronostico()) {
    				if(ronda.getNumRonda() == pronostico.getRonda()) {
        				if(pronostico.getAcierto() == true) {
        					aciertosR++;
    					}
    				}
    			}
    			if(ronda.getPartidos().size() == aciertosR) {
    				persona.acerto(ronda.getNumRonda(), valorXRonda);
    				aciertosF++;
    			}
    		}
    		if(aciertosF == fase2.size()) {
    			persona.acerto(aciertosF, valorXFase);
    		}
    	}	
    }
    
    public static void print(List<Ronda> rondas, List<Persona> personas) {
    	System.out.println("Resultados:");
    	for(Ronda ronda : rondas) {
    		System.out.println("   Ronda " + ronda.getNumRonda());
    		for(Partido partido : ronda.getPartidos()) {
    			if (partido.getGanador().toUpperCase().equals("EMPATE")) {
					System.out.println("    -" + partido.encuentro() + "Resultado: " + partido.getGanador());
				}
				else {
					System.out.println("    -" + partido.encuentro() + "Resultado: GANADOR (" + partido.getGanador() +")");
				}
    		}
    		System.out.println("");
    	}
    	System.out.println("Puntuacion:");
    	for (Ronda ronda : rondas) {
    		System.out.println("   Ronda "+ ronda.getNumRonda());
    		for (Persona persona : personas) {
    			System.out.println("    -El jugador "+persona.getCodigo()+" ("+persona.getNombre()+") obtuvo "+persona.getPuntaje(ronda.getNumRonda())+" puntos.");
    		}
    	}
    	
    	System.out.println("");
    	System.out.println("Puntuacion total:");
    	for(Persona persona : personas) {
    		System.out.println("    -El jugador "+persona.getCodigo()+" ("+persona.getNombre()+") obtuvo "+persona.getPuntajeTotal()+" puntos.");
    	}
    }
}