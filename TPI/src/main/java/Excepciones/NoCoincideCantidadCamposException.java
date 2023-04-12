package Excepciones;

public class NoCoincideCantidadCamposException extends Exception{
	private final String lineaError;
	
	public NoCoincideCantidadCamposException(String lineaError) {
		this.lineaError=lineaError;
	}
	
	public void mensajeError() {
		System.out.println("Error: La cantidad de campos de esta linea es inadecuada");
		System.out.println("La linea del error es: ");
		System.out.println(lineaError);
	}
}
