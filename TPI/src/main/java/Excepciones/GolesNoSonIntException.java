package Excepciones;

public class GolesNoSonIntException extends Exception {
	private final String lineaError;
	
	public GolesNoSonIntException(String lineaError) {
		this.lineaError=lineaError;
	}

	public void mensajeError() {
		System.out.println("Error: Se intentó declarar una cantidad de goles que no es entera.");
		System.out.println("La linea del error es;");
		System.out.println(lineaError);
	}
}
