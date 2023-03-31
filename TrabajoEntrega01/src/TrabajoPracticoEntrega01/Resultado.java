package TrabajoPracticoEntrega01;

public class Resultado {
private String resultado;
private String equipo;

public Resultado(String resultado, String equipo) {
	super();
	this.resultado = resultado;
	this.equipo = equipo;
}

public String getResultado() {
	return resultado;
}

public void setResultado(String resultado) {
	this.resultado = resultado;
}

public String getEquipo() {
	return equipo;
}

public void setEquipo(String equipo) {
	this.equipo = equipo;

}

@Override
public String toString() {
	return  this.getResultado() + " " +this.getEquipo();
}


}