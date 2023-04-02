package TrabajoPracticoEntrega01;

import java.util.Iterator;
import java.util.List;

public class puntos {
	
	
	
public int compararResultados(List<Pronostico> pronosticos, List<Resultado> resultados) {
		
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
	    

		//System.out.println("El puntaje del jugador "+ pronosticos.get(0).getJugador()+  " es: " + puntos); 
		
		return puntos;
	}
	
		
}
		
	


