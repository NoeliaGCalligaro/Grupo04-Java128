package Clases;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import Excepciones.*;

import java.io.IOException;


public class TPIMain {

    public static void main(String[] args) {
    	String resultados = "src/main/java/Archivos/partidos.txt";
    	String pronosticos = "src/main/java/Archivos/pronostico.txt";
    	String configuracion = "src/main/java/Archivos/Configuracion.txt";
    	int puntosXVictoria = 0;
    	int puntosXEmpate = 0;
    	int puntosXRonda = 0;
    	int puntosXFase = 0;
    	String url = "";
    	String user = "";
    	String password = "";
    	try {
			for(String linea : Files.readAllLines(Paths.get(configuracion))) {
				puntosXVictoria = Integer.parseInt(linea.split(";")[0]);
				puntosXEmpate = Integer.parseInt(linea.split(";")[1]);
				puntosXRonda = Integer.parseInt(linea.split(";")[2]);
				puntosXFase = Integer.parseInt(linea.split(";")[3]);
				url = linea.split(";")[4];
				user = linea.split(";")[5];
				password = linea.split(";")[6];
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Error en la configuracion.");
		}

    	
    	List<Ronda> rondas = cargaResultados(resultados);
    	//el programa puede leer del archivo con cargaJugadores(pronosticos), o de la BD con cargaJugadores()
    	List<Persona> jugadores = cargaJugadores(url,user,password, pronosticos);
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
    
    public static List<Persona> cargaJugadores(String url, String user,String password, String pronosticos) {
        try {  
        	
        	//para mysql:
            //Class.forName("com.mysql.cj.jdbc.Driver"); 
            //Connection con=DriverManager.getConnection(url,user,password);

            //para postgresql:
            Class.forName("org.postgresql.Driver");
            Connection con=DriverManager.getConnection(url,user,password);  
            
            Statement stmt=con.createStatement();  
            System.out.println("Conectado a la base de datos"); 

            //esto es solo para cargar datos a la BD. No usar mas de 1 vez por equipo sin vaciar la BD!
            //cargarTabla(pronosticos, stmt);
            
            
            List<Persona> personas = new ArrayList<Persona>();
            int codPersona = 0;
            ResultSet rs=stmt.executeQuery("select * from Pronosticos");  
            ResultSetMetaData rsmd=rs.getMetaData();
            while (rs.next()) {
                System.out.println(rs.getInt(2)+"  "+rs.getString(3));
                System.out.println(rsmd.getColumnCount());

                if (codPersona != rs.getInt(2)) {
                    Persona persona = new Persona(rs.getInt(2), rs.getString(3));
                    personas.add(persona);
                    codPersona = rs.getInt(2);
                }
            }

            for (Persona pers : personas) {
            	//usando rs.beforeFirst() me tira una excepcion, porque el puntero no puede ir para atras. Lo vuelvo a crear
            	rs=stmt.executeQuery("select * from Pronosticos");
                while (rs.next()) {
                    if (rs.getInt(1) == pers.getCodigo()) {
                        Pronostico pronostico = new Pronostico(rs.getInt(6), rs.getString(4), rs.getString(5), rs.getString(7));
                        pers.getPronostico().add(pronostico);
                    }
                }
            }

            con.close();  
            return personas;
        } catch(Exception e) {
            System.out.println(e);
            return null;
        }
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
    				if(ronda.getNumRonda() != pronostico.getRonda()) {
        				continue;
    				}
    				else {
    					if(pronostico.getAcierto() == true) {
        					aciertosR++;
    					}
    				}
    			}
    			if(ronda.getPartidos().size() == aciertosR) {
    				if(!persona.getPuntaje().containsKey(777)) {
    					persona.getPuntaje().put(777, 0);
    				}
    				persona.acerto(777, valorXRonda);
					aciertosF++;
    			}
    		}
    		if(aciertosF == fase1.size()) {
    			persona.acerto(777, valorXFase);
    		}
    	}
    	
    	for(Persona persona : personas) {
    		int aciertosF = 0;
    		for(Ronda ronda : fase2) {
    			int aciertosR = 0;
    			for(Pronostico pronostico : persona.getPronostico()) {
    				if(ronda.getNumRonda() != pronostico.getRonda()) {
        				continue;
    				}
    				else {
    					if(pronostico.getAcierto() == true) {
        					aciertosR++;
    					}
    				}
    			}
    			if(ronda.getPartidos().size() == aciertosR) {
    				if(!persona.getPuntaje().containsKey(777)) {
    					persona.getPuntaje().put(777, 0);
    				}
    				persona.acerto(777, valorXRonda);
					aciertosF++;
    			}
    		}
    		if(aciertosF == fase2.size()) {
    			persona.acerto(777, valorXFase);
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
    	System.out.println("\n" + "Puntos Extra:");
    	for(Persona persona: personas) {
    		if(persona.getPuntaje().containsKey(777)) {
    			System.out.println("    -El jugador "+persona.getCodigo()+" ("+persona.getNombre()+") obtuvo "+persona.getPuntaje(777)+" puntos.");
    		}
    	}
    	System.out.println("");
    	System.out.println("Puntuacion total:");
    	for(Persona persona : personas) {
    		System.out.println("    -El jugador "+persona.getCodigo()+" ("+persona.getNombre()+") obtuvo "+persona.getPuntajeTotal()+" puntos.");
    	}
    }
    
    //Esta funcion sirve para cargar los datos de pronostico.txt a la base de datos automaticamente. Debería ejecutarse una vez en la maquina y después comentarse
    public static void cargarTabla(String pronosticos, Statement stmt) {
		try {
			for (String linea : Files.readAllLines(Paths.get(pronosticos)))
			{
				String[] partes = linea.split(";");
				partes[2] = "'"+partes[2]+"'";
				partes[3] = "'"+partes[3]+"'";
				partes[4] = "'"+partes[4]+"'";
				partes[6] = "'"+partes[6]+"'";
				String datosInsert = partes[0]+", "+partes[1]+", "+partes[2]+", "+partes[3]+", "+partes[4]+", "+partes[5]+", "+partes[6];
				try {
					int result = stmt.executeUpdate("INSERT INTO public.pronosticos(\r\n"
							+ "	\"idPronostico\", \"codJugador\", \"nombreJugador\", participante1, participante2, ronda, ganador)\r\n"
							+ "	VALUES ("+datosInsert+");");
				} catch (SQLException e) {
					System.out.println("Error en el insert");
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			System.out.println("Error al cargar datos del archivo a la base de datos");
			e.printStackTrace();
		}
    }
}