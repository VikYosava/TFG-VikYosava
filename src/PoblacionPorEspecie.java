

public class PoblacionPorEspecie {

	private int cant, generacion, posicion;
	private short[] especie;
	private float alimento;
	private String padre;
	
	// cant es la cantidad de individuos que hay en cada especie
	// especie es el array que contiene el código de genes (1 si tiene el gen, 0 si no)
	// alimento es un valor que contiene la cantidad de alimento por especie total, determina la reproducción
	// generacion es un valor que determina en qué iteración mutó y se generó la nueva especie
	
	public PoblacionPorEspecie(int cant, short[] especie, String padre, int generacion, float alimento, int posicion) {
		this.cant = cant;
		this.especie = especie;
		this.generacion=generacion;
		this.alimento=alimento;
		this.posicion=posicion;
		this.padre=padre;
	}
	
	public String getPadre() {
		return padre;
	}
	
	/*public void setPadre(String padre) {
		this.padre=padre;
	}*/
	
	public int getCant() {
        return cant;
    }

    public short[] getEspecie() {
        return especie;
    }
    
    public void setCant(int n) {
    	cant = n;
    }
    
    public void setEspecie(short[] nuevaEsp, int nuevaGen) {
    	especie = nuevaEsp;
    	setGeneracion(nuevaGen);
    }

	public int getGeneracion() {
		return generacion;
	}

	public void setGeneracion(int generacion) {
		this.generacion = generacion;
	}
	
	public float getAlimento() {
		return alimento;
	}
	
	public void setAlimento(float newAlimento) {
		this.alimento=newAlimento;
	}
	
	public void setPosicion(int npos) {
		this.posicion=npos;
	}
	
	public int getPosicion() {
		return posicion;
	}
	
	public void imprimirInformacion() {
        System.out.println("Cantidad: " + cant);
        System.out.println("Especie: " + java.util.Arrays.toString(especie));
        System.out.println("Generación: " + generacion);
        System.out.println("Alimento: " + alimento);
        System.out.println("-------------");
    }
	
	public int compareTo(PoblacionPorEspecie otraPoblacion) {
        return Integer.compare(this.posicion, otraPoblacion.posicion);
    }

}
