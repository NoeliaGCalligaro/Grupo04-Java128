package Clases;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Excepciones.*;

import java.io.IOException;


public class TPIMain {



    public static void main(String[] args) {
      
    	List<Ronda> rondas = new ArrayList<Ronda>();
    	int numRonda=0;
    	Ronda ronda = null; //esto es para que no me de error el if despues
    	try {
    		//tengo que crear todas las rondas primero para evitar ConcurrentModificationException
    		for (String linea: Files.readAllLines(Paths.get("src\\main\\java\\Archivos\\","partidos.txt"))) {
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
    		for (String linea: Files.readAllLines(Paths.get("src\\main\\java\\Archivos\\","partidos.txt"))) {
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
    	
    	//insertar pronosticos
    	//se asume que los pronosticos vienen ordenados por numero de persona
    	List<Persona> personas = new ArrayList<Persona>();
    	int codPersona = 0;
    	Persona persona = null;
    	try {
    		for (String linea: Files.readAllLines(Paths.get("src\\main\\java\\Archivos\\","pronostico.txt"))) {
    			   String[] partes = linea.split(";");
    			   
    			   //Chequeo si existe la persona, si no la creo
    			   if (codPersona!=Integer.valueOf(partes[0])) {
    				   persona = new Persona(Integer.valueOf(partes[0]), partes[1]);
    				   personas.add(persona);
    				   codPersona = Integer.valueOf(partes[0]);
    			   }
    		   }
    		
    		for (String linea: Files.readAllLines(Paths.get("src\\main\\java\\Archivos\\","pronostico.txt"))) {
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
    	
    	//ahora veo si acertaron
    	for (Persona per : personas) {
    		for (Pronostico pro : per.getPronostico()) {
    			for (Ronda ron : rondas) {
    				if (!per.getPuntaje().containsKey(ron.getNumRonda())) {
    					per.getPuntaje().put(ron.getNumRonda(), 0);
    				}
    				
    				if (pro.getRonda()==ron.getNumRonda()) {
    					for (Partido par : ron.getPartidos()) {
    						//me fijo si es el partido correcto
    						if (par.getParticipante1().equals(pro.getParticipante1()) && par.getParticipante2().equals(pro.getParticipante2())) {
    							if (par.getGanador().equalsIgnoreCase(pro.getGanador())) {
        							per.acerto(ron.getNumRonda());
        						}
    						}
    						
    						
    					}
    				}
    			}
    		}
    		
    		
    	}

    	System.out.println("Puntuacion:   (Cada punto es un acierto)");
    	for (Ronda ron : rondas) {
    		System.out.println("   Ronda "+ron.getNumRonda());
    		for (Persona per : personas) {
    			System.out.println("    -El jugador "+per.getCodigo()+" ("+per.getNombre()+") obtuvo "+per.getPuntaje(ron.getNumRonda())+" puntos.");
    		}
    	}
    	
    	System.out.println("");
    	System.out.println("Puntuacion total:");
    	for(Persona per : personas) {
    		System.out.println("    -El jugador "+per.getCodigo()+" ("+per.getNombre()+") obtuvo "+per.getPuntajeTotal()+" puntos.");
    	}

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

}