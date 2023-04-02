package TrabajoPracticoEntrega01;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import TrabajoPracticoEntrega01.puntos;

class puntosTest {
	public static List <Resultado> resultados=new ArrayList<Resultado>();
	public static List <Pronostico> pronosticos=new ArrayList<Pronostico>();
    int total;
	int totalEsperado;
	puntos punto;
	

	@BeforeEach
	public void setUp() {
		punto=new puntos();
		
		
	}
	

	@Test
	void testCompararResultados() {
		 Pronostico pronostico=new Pronostico("Gana", "Argentina", "Juan");
		 Resultado resultado =new Resultado("Gana","Argentina");
		 Pronostico pronostico1=new Pronostico("Gana", "Alemania", "Juan");
		 Resultado resultado1 =new Resultado("Gana","Chile");
		 Pronostico pronostico2=new Pronostico("Gana", "Paraguay", "Juan");
		 Resultado resultado2 =new Resultado("Empate"," ");
		 resultados.add(resultado);
		 resultados.add(resultado1);
		 resultados.add(resultado2);
		 pronosticos.add(pronostico);
		 pronosticos.add(pronostico1);
		 pronosticos.add(pronostico2);
		 
		 
		 total=punto.compararResultados(pronosticos, resultados);
		 totalEsperado=1;
		 assertEquals(totalEsperado, total);
		 
	}

}
