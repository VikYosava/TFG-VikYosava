package model;

import java.io.BufferedWriter;
import java.io.IOException;

public class PoblacionPorEspecie {

	private int cant, generacion, posicion;
	private short[] especie;
	private float alimento;
	private String padre;
	
	private static final float CMovBase = 0.1f; //	[3]	 Minimo 0, máximo 1 (Capacidad de recolección, incluir comer comida dentro del movimiento)
	private static final float CAlimBase = 0f; //	[2]	 Mínimo 0, Nmax=NinicialInd*X siendo X variable por el usuario, 5 de base
	private static final float CRepartoBase = 1f; //[1]	 Mínimo 1, Nmax=NinicialInd*X siendo X variable por el usuario, 5 de base
	private static final float PMutBase = 0.5f;
	
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

	public void escribirDatos(BufferedWriter writer, String filePath, float[][] probIndividuo) throws IOException {
		writer.write("\""+java.util.Arrays.toString(especie)+"\", ");
		int leng=especie.length;
		for(int i=0;i<leng;i++) {
			writer.write(especie[i]+", ");
		}
		float movementCap=CMovBase;
		float alimentCost=CAlimBase;
		float refoodCap=CRepartoBase;
		float mutationProb=PMutBase;
		for(int k = 0; k<leng; k++) {
			movementCap=movementCap+probIndividuo[3][k]*especie[k];
		}
		for(int k = 0; k<leng; k++) {
			alimentCost=alimentCost+probIndividuo[2][k]*especie[k];
		}
		for(int k = 0; k<leng; k++) {
			refoodCap=refoodCap+probIndividuo[1][k]*especie[k];
		}
		for(int k = 0; k<leng; k++) {
			mutationProb=mutationProb+probIndividuo[0][k]*especie[k];
		}		
		writer.write(mutationProb+", ");
		writer.write(refoodCap+", ");
		writer.write(alimentCost+", ");
		writer.write(movementCap+", ");
		writer.write(generacion+", ");
		writer.write(cant+"\n");		
	}

}
