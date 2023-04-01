
package TrabajoPracticoEntrega01;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class PronosticoPartidos {

	public static List <Partido> partidos=new ArrayList<Partido>();
	public static List <Resultado> resultados=new ArrayList<Resultado>();
	public static List <Pronostico> pronosticos=new ArrayList<Pronostico>();
	public static List <Pronostico> pronosticos1=new ArrayList<Pronostico>();
	public static List <Pronostico> pronosticos2=new ArrayList<Pronostico>();
	public static List <Jugador> jugadores=new ArrayList<Jugador>();


	

	public static void main(String[] args) throws FileNotFoundException {
		
		//Leer-Cargar los datos de los partidos en el objeto partido
		System.out.println("Resultados de los partidos:  \n");
		cargarPartidos();
		
		
		//Asignamos en el objeto resultado los resultados de los partidos 
		
		cargarResultados();
		
		
		
		//leemos-cargamos los pronosticos de los jugadores en el objeto pronostico 
		
		System.out.println("\n\n\nPronosticos de los jugadores:  \n");
		leerPronosticos();
		
		
		separarJugadores();
		
			
		//leer / cargar a los jugadores en la clase jugador 
			
			//comparamos los resultados con los pronosticos por cada jugador
		//mostramos el puntaje
			
			//comparar los puntajes de los jugadores para que los muestre ordenados.
		System.out.println("\n\n\nPuntajes:  \n");
		compararResultados(pronosticos1);
		compararResultados(pronosticos2);
		
		

	}


	


	





	private static void compararResultados(List<Pronostico> pronosticos) {
		
		 int puntos=0;
		 int i=0;
		Iterator<Pronostico> iter= pronosticos.iterator();
		 
		 
	    while(iter.hasNext()) {
        Pronostico pronostico= iter.next();
       
        String resultado=resultados.get(i).getResultado();
       
        String equipo=resultados.get(i).getEquipo();
        String pro=pronostico.getResultado();
        
        String equipopro=pronostico.getEquipo();
        
       
      
       
       
        if(resultado.equals(pro)&& (equipo.equals(equipopro))) {
        		 puntos++;
        		 
        	}
      
        i++;
        	
        	}
	    

		System.out.println("El puntaje del jugador "+ pronosticos.get(0).getJugador()+  " es: " + puntos); 
		
	
	}
      
        
        	
		

        
		
		
	


      private static void separarJugadores() {
    	  Iterator<Pronostico> ite= pronosticos.iterator();
    	  int i=0;
    	while(ite.hasNext()) {
    		
    			Pronostico pronostico= ite.next();
    			if(i<3) {
    				
    					
        				String jugador =pronostico.getJugador();
                	    String equipo =pronostico.getEquipo();
        				String resultado =pronostico.getResultado();
        				
        			 Pronostico pronostico1=new Pronostico(resultado, equipo, jugador);
        				pronosticos1.add(pronostico1);
    				
        				     
        			     } else if ((i>=3)&&(i<6)) {
         					
        	    				String jugador =pronostico.getJugador();
        	            	    String equipo =pronostico.getEquipo();
        	    				String resultado =pronostico.getResultado();
        	    				
        	    			 Pronostico pronostico2=new Pronostico(resultado, equipo, jugador);
        	    			pronosticos2.add(pronostico2);
    				
    			}
    			
    	    					
		
	         
    			i++;
    		}
      }


	private static void leerPronosticos() throws FileNotFoundException {
		String rutaPronosticos = "src/TrabajoPracticoEntrega01/Pronosticos.txt";
		  
			
			File file = new File(rutaPronosticos);
		
			
			  try (Scanner scanner = new Scanner(file)) {            
				while (scanner.hasNext()) {
                String linea = scanner.nextLine();
               
                String jugador =linea.split(" ")[0]; 
                String resultado =linea.split(" ")[1]; 
                String equipo= "  ";
                try {
                  equipo =linea.split(" ")[2]; 
              
                }catch(ArrayIndexOutOfBoundsException e) {
                	 equipo= " - ";
                }catch(NullPointerException e) {
                  equipo= " - ";
                }
        	   
        	 
				
				
			 Pronostico pronostico=new Pronostico(resultado, equipo, jugador);
				pronosticos.add(pronostico);
				
              
            }
            scanner.close();
		 }catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		        
	        
	 	
		pronosticos.forEach(System.out::println); 

		
	}


	private static void cargarResultados() {	
		
		
		Iterator<Partido> ite= partidos.iterator();
	while(ite.hasNext()) {
		Partido partido= ite.next();
		
		if(partido.getResultado1()>partido.getResultado2()) {
				
				String resultado1="Gana";
				String equipo1=partido.getEquipo1();
				
				Resultado resultado =new Resultado(resultado1,equipo1);
				resultados.add(resultado);
			     
		     }else if (partido.getResultado2()>partido.getResultado1()) {
	    	String resultado1="Gana";
			String equipo1=partido.getEquipo2();
			
			Resultado resultado =new Resultado(resultado1,equipo1);
			resultados.add(resultado);
		     
	         
        }else if(partido.getResultado1()==partido.getResultado2()) {
        	String resultado1="Empate";
			String equipo1=" - ";
			
			Resultado resultado =new Resultado(resultado1,equipo1);
			resultados.add(resultado);
	     
             }
        }
		}
	
	
 
	


	private static void cargarPartidos() throws FileNotFoundException {
		
		String rutaResultados= "src/TrabajoPracticoEntrega01/Resultados.txt";
		

		File file = new File(rutaResultados);
        try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNext()) {
			   String linea = scanner.nextLine();
			   String equipo1 =linea.split(" ")[0]; 
			    int resultados1 =Integer.parseInt(linea.split(" ")[1]); 
			    
				int resultados2 =Integer.parseInt(linea.split(" ")[2]);
				String equipo2 =linea.split(" ")[3]; 
				Partido partido =new Partido(equipo1, equipo2,resultados1,resultados2);
				partidos.add(partido);
			  
			}
			scanner.close();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        partidos.forEach(System.out::println); 
		 		
    
	}
	
}
