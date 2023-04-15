package Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Clases.*;

class PersonaTest {

	Persona persona;
	List<Ronda> rondas;
	
	@BeforeEach
	public void setUp() {
		persona = new Persona(0, "Johann");
		rondas = new ArrayList<Ronda>();
		
	}
	
	@Test
	void testGetPuntaje() {
		persona.getPronostico().add(new Pronostico(1, "Argentina", "Mexico", "Argentina"));
		persona.getPronostico().add(new Pronostico(1, "Argentina", "Polonia", "Argentina"));
		persona.getPronostico().add(new Pronostico(1, "Argentina", "Brasil", "Argentina"));
		
		persona.getPronostico().add(new Pronostico(2, "Argentina", "Mexico", "Argentina"));
		persona.getPronostico().add(new Pronostico(2, "Argentina", "Paraguay", "Paraguay"));
		persona.getPronostico().add(new Pronostico(2, "Argentina", "Chile", "Empate"));
		
		rondas.add(new Ronda(1));
		rondas.add(new Ronda(2));
		
		for (Ronda ron : rondas) {
			if (ron.getNumRonda()==1) {
				ron.getPartidos().add(new Partido("Argentina", "Mexico", 2, 0));
				ron.getPartidos().add(new Partido("Argentina", "Polonia", 3, 0));
				ron.getPartidos().add(new Partido("Argentina", "Brasil", 4, 0));
			}
			
			if (ron.getNumRonda()==2) {
				ron.getPartidos().add(new Partido("Argentina", "Mexico", 2, 0));
				ron.getPartidos().add(new Partido("Argentina", "Paraguay", 1, 0));
				ron.getPartidos().add(new Partido("Argentina", "Chile", 0, 0));
			}
		}
		//Acá copié el codigo del main
		for (Pronostico pro : persona.getPronostico()) {
			for (Ronda ron : rondas) {
				if (!persona.getPuntaje().containsKey(ron.getNumRonda())) {
					persona.getPuntaje().put(ron.getNumRonda(), 0);
				}
				
				if (pro.getRonda()==ron.getNumRonda()) {
					for (Partido par : ron.getPartidos()) {
						//me fijo si es el partido correcto
						if (par.getParticipante1().equals(pro.getParticipante1()) && par.getParticipante2().equals(pro.getParticipante2())) {
							if (par.getGanador().equalsIgnoreCase(pro.getGanador())) {
    							persona.acerto(ron.getNumRonda(), 1);
    						}
						}
						
						
					}
				}
			}
		}
		
		assertTrue(persona.getPuntaje(1)==3);
		assertTrue(persona.getPuntaje(2)==2);
		assertTrue(persona.getPuntajeTotal()==5);
	}

}
