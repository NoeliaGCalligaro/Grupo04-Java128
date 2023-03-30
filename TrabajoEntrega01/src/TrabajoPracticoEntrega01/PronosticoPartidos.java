
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
	public static List <Jugador> jugadores=new ArrayList<Jugador>();
	static int puntos1=0;
	static int puntos2=0;
	

	public static void main(String[] args) throws FileNotFoundException {
		
		//Leer-Cargar los datos de los partidos en el objeto partido
		
		cargarPartidos();
		
		
		//Asignamos en el objeto resultado los resultados de los partidos 
		
		cargarResultados();
		
		
		
		//leemos-cargamos los pronosticos de los jugadores en el objeto pronostico 
		
		
		leerPronosticos();
		
	
		
		

			
		//leer / cargar a los jugadores en la clase jugador 
			
			//comparamos los resultados con los pronosticos por cada jugador
		//mostramos el puntaje
			
			//comparar los puntajes de los jugadores para que los muestre ordenados.
		//compararResultados();

	}


	


	private static void leerPronosticos() throws FileNotFoundException {
		String rutaPronosticos = "/home/noelia/GitHub/Grupo04-Java128/TrabajoEntrega01/src/TrabajoPracticoEntrega01/Pronosticos.txt";
		  
			
			File file = new File(rutaPronosticos);
			  try (Scanner scanner = new Scanner(file)) {            
				  while (scanner.hasNext()) {
                String linea = scanner.nextLine();
                System.out.println(linea);
                String jugador =linea.split(" ")[0]; 
        	    String equipo =linea.split(" ")[1]; 
        	 
				String resultado =linea.split(" ")[2]; 
			 Pronostico pronostico=new Pronostico(equipo, resultado, jugador);
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
		
	Iterator ite= partidos.iterator();
	while(ite.hasNext()) {
		Partido partido=(Partido) ite.next();
		if(partido.getResultado1()>partido.getResultado2()) {
			Iterator iter= resultados.iterator();
			while(iter.hasNext()) {
				Resultado resultado=(Resultado) ite.next();
				resultado.setResultado("Gana");
				resultado.setEquipo(partido.getEquipo1());
			     
		}
	}else if (partido.getResultado2()>partido.getResultado1()) {
		Iterator iter= resultados.iterator();
		while(iter.hasNext()) {
			Resultado resultado=(Resultado) ite.next();
			resultado.setResultado("Gana");
			resultado.setEquipo(partido.getEquipo2());
		     
	}
}else if(partido.getResultado1()==partido.getResultado2()) {
	Iterator iter= resultados.iterator();
	while(iter.hasNext()) {
		Resultado resultado=(Resultado) ite.next();
		resultado.setResultado("Empate");
		resultado.setEquipo(null);
	     
}
}
		}
		
	}


	private static void cargarPartidos() throws FileNotFoundException {
		
		String rutaResultados= "/home/noelia/GitHub/Grupo04-Java128/TrabajoEntrega01/src/TrabajoPracticoEntrega01/Resultados.txt";
		

		File file = new File(rutaResultados);
        try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNext()) {
			   String linea = scanner.nextLine();
			   String equipo1 =linea.split(" ")[0]; 
			    int resultados1 =Integer.parseInt(linea.split(" ")[1]); 
			    System.out.println(linea);
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
        
		 		
    
	}
	
}
