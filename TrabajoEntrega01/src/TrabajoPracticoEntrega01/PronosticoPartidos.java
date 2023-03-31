
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
	static int puntos1;
	static int puntos2;
	

	public static void main(String[] args) throws FileNotFoundException {
		
		//Leer-Cargar los datos de los partidos en el objeto partido
		
		cargarPartidos();
		
		
		//Asignamos en el objeto resultado los resultados de los partidos 
		
		cargarResultados();
		
		resultados.forEach(System.out::println); 
		
		//leemos-cargamos los pronosticos de los jugadores en el objeto pronostico 
		
		
		leerPronosticos();
		
	
		
		

			
		//leer / cargar a los jugadores en la clase jugador 
			
			//comparamos los resultados con los pronosticos por cada jugador
		//mostramos el puntaje
			
			//comparar los puntajes de los jugadores para que los muestre ordenados.
		compararResultados();
		
		

	}


	


	private static void compararResultados() {
		
		
		Iterator<Pronostico> iter= pronosticos.iterator();
		 int i=0;
	    while(iter.hasNext()) {
        Pronostico pronostico= iter.next();
       
        String resultado=resultados.get(i).getResultado();
        System.out.println(resultado);
        String equipo=resultados.get(i).getEquipo();
        System.out.println(equipo);
        i++;
        if(pronostico.getJugador()=="Jugador1"){
          
        	if((resultado==pronostico.getResultado())&&(equipo==pronostico.getEquipo())) {
        		 puntos1 +=1;
        	}else  if(pronostico.getJugador()=="Jugador2"){
                
            	if((resultados.get(i).getResultado()==pronostico.getResultado())&&(resultados.get(i).getEquipo()==pronostico.getEquipo())) {
            		 puntos2 +=1;
            	}
        	
        	
        	
        	}}
      
        }
        	
		/*for (int i=0;i<3;i++) {
		  if((resultados.get(i).getResultado()==pronostico.getResultado())&&(resultados.get(i).getEquipo()==pronostico.getEquipo())) {
				 if(pronostico.getJugador()=="Jugador1"){
					 puntos1 +=1;
				 }else if(pronostico.getJugador()=="Jugador2"){
					 puntos2 +=1;
				 }
						
		  }
		}
			

			
		}*/

		System.out.println("El puntaje del jugador 1 es: "+ puntos1); 
		System.out.println("El puntaje del jugador 2 es: "+ puntos2); 
		
		
	}





	private static void leerPronosticos() throws FileNotFoundException {
		String rutaPronosticos = "src/TrabajoPracticoEntrega01/Pronosticos.txt";
		  
			
			File file = new File(rutaPronosticos);
			  try (Scanner scanner = new Scanner(file)) {            
				  while (scanner.hasNext()) {
                String linea = scanner.nextLine();
               
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
			String equipo1=" ";
			
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
