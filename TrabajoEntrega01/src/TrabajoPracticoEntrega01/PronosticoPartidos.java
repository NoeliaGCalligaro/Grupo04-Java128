
package TrabajoPracticoEntrega01;
import java.io.IOException;
import java.nio.file.*;

public class PronosticoPartidos {

	

	public static void main(String[] args) {
		
		 Partido partido[]=new Partido[2];
		 int puntos=0;
		
		Path rutaResultados = Paths.get("/home/noelia/eclipse-workspace/EjerciciosJava/src/TrabajoPracticoEntrega01/Resultados.txt");
		Path rutaPronosticos = Paths.get("/home/noelia/eclipse-workspace/EjerciciosJava/src/TrabajoPracticoEntrega01/Pronosticos.txt");

		try {
			
			 
			
			for(String lineaArchivo:Files.readAllLines(rutaResultados)) { 
				
				String partidos[] = lineaArchivo.split(" ");
				int i=0;
				String equipo1 =partidos [0]; 
			    int resultados1 =Integer.parseInt(partidos[1]); 
				int resultados2 =Integer.parseInt(partidos[2]);
				String equipo2 =partidos [3]; 
				
				partido[i]=new Partido(equipo1,equipo2,resultados1,resultados2);
		        i++;
			
		} 
		}catch (IOException e) {
			
			e.printStackTrace();
		}
		
			
			 Resultado resultado[]=new Resultado[2];
			
			for(int i=0;i>3;i++) {
				if (partido[i].getResultado2()>partido[i].getResultado2()) {
					resultado[i].setResultado("Gana");
					resultado[i].setEquipo(partido[i].getEquipo1());
				}else if (partido[i].getResultado2()>partido[i].getResultado1()) {
					resultado[i].setResultado("Gana");
					resultado[i].setEquipo(partido[i].getEquipo2());
				}else if (partido[i].getResultado2()==partido[i].getResultado1()) {
					resultado[i].setResultado("Empate");
					
				}
				}
					
			Pronostico pronostico[]=new Pronostico[2];
				
			try {
				
				for(String lineaArchivo:Files.readAllLines(rutaPronosticos)) { 
					
					String pronosticos[] = lineaArchivo.split(" ");
					int i=0;
					String equipo =pronosticos [0]; 
				    String resultado1 =pronosticos[1];
					pronostico[i]=new Pronostico(equipo,resultado1);
			        i++;
				}
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		
		for(int i=0; i<3;i++) {
			if((resultado[i].getResultado()==pronostico[i].getResultado()) && (resultado[i].getEquipo()==pronostico[i].getEquipo())){
				puntos+=1;
			}
		}
			
		
			
		System.out.print("Su puntaje es: "+ puntos); 
	

	
	
			


	}}
