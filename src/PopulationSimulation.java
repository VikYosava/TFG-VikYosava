import java.util.Random;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;

public class PopulationSimulation {
    public static void main(String[] args) {
    	
        int Nmutaciones = 15; // aumentar este número afecta mucho menos al coste de computación que aumentar el número de rondas
        // número de posibles mutaciones que pueden aparecer en la simulación
        
        short[] IndBase= new short[Nmutaciones];
        
        int TPoblacion = 20;
        short Alimento = (short) TPoblacion;
        int ronda0 = 0;
        float[][] ProbIndividuo= new float[4][Nmutaciones];
        
        //RandomIntegerArray(Nmutaciones);
        float maxPMut=1;
        float maxCRep=TPoblacion*5;
        float maxCAlim=maxCRep;
        float maxCMov=maxPMut;
        
        
        // agresividad del medio: valor por el que puede morir una especie aleatoria cada cierto tiempo
        // ley de los grandes numeros - gauss, bioestadistica
        
        ProbIndividuo[0]=Generados.RandomIntegerArray(Nmutaciones, 0.0f, maxPMut, 2f); // PROB MUTACIÓN
        //ProbIndividuo[1]=Generados.RandomIntegerArray(Nmutaciones, 1f, maxCRep, 0.5f); // CAPACIDAD REPARTO bajar mucho
        ProbIndividuo[2]=Generados.RandomIntegerArray(Nmutaciones, 1f, maxCAlim, 1f); // COSTE ALIMENTO
        ProbIndividuo[3]=Generados.RandomIntegerArray(Nmutaciones, 0.5f, maxCMov, 2f); // CAPACIDAD MOVIMIENTO inhibición por contacto

        
        
        
        Generados GrupoBase= new Generados();
        GrupoBase.addGenerado(TPoblacion, IndBase, ronda0, Alimento);
        
        System.out.println("\nPoblaciones:");
        GrupoBase.imprimirDatosGenerados();
        
        Generados NuevoGrupo1=GrupoBase.clone().Generacion(ProbIndividuo, 1);
        
        int nrondas=10;
        int frecCatastrof=5;
        for(int i=1;i<=nrondas;i++) {
        	//System.out.println("gen i:"+i);
        	NuevoGrupo1.Generacion(ProbIndividuo,i);
        	if(frecCatastrof%nrondas==0) {
        		Random random = new Random();
                int binario = random.nextInt(2);
                int posicion=random.nextInt(Nmutaciones);
                for(int j=0;j<NuevoGrupo1.size();j++) {
                	if(NuevoGrupo1.getEspecieID(j)[posicion]==binario) {
                		NuevoGrupo1.setCantID(j, 0);
                	}
                }
        	}
        }
        
        // Imprimir la información de NuevoGrupo si es necesario
        
        System.out.println("\nPoblaciones:");
        GrupoBase.imprimirDatosGenerados();
        
        System.out.println("\nNuevo Grupo:");
        NuevoGrupo1.imprimirDatosGenerados();
        
        for (int i = 0; i < ProbIndividuo.length; i++) {
            // Iterar sobre las columnas de la matriz
            for (int j = 0; j < ProbIndividuo[i].length; j++) {
                // Imprimir el elemento en la posición i, j
                System.out.print(ProbIndividuo[i][j] + " ");
            }
            // Imprimir un salto de línea después de imprimir cada fila
            System.out.println();
        }
        
        

        
        
        
        
    }

	
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    


        
        
        /*PoblacionEsp[0] = TPoblacion;

        int[][] PoblacionInd = new int[Nmutaciones][NEspecies];

        double[][] ProbIndividuo = new double[3][Nmutaciones];

        TPoblacion = PoblacionEsp[0];
        for (int i = 0; i < PoblacionEsp.length; i++) {
            TPoblacion += PoblacionEsp[i];
        }

        int NGeneraciones = 10;

        int[][] Generados = new int[NGeneraciones][NEspecies];
        Generados = Generacion(PoblacionEsp, PoblacionInd, ProbIndividuo, NGeneraciones, Individuo);

        // Printing Generados and PoblacionInd arrays
        System.out.println("Generados:");
        for (int i = 0; i < Generados.length; i++) {
            for (int j = 0; j < Generados[i].length; j++) {
                System.out.print(Generados[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("\nPoblacionInd:");
        for (int i = 0; i < PoblacionInd.length; i++) {
            for (int j = 0; j < PoblacionInd[i].length; j++) {
                System.out.print(PoblacionInd[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int[][] Generacion(int[] PoblacionEsp, int[][] PoblacionInd, double[][] ProbIndividuo,
                                     int NGeneraciones, int[] Individuo) {
        // Logic for generation evolution goes here
        // This method simulates the generation updates based on population, probabilities, etc.
        // You can implement the logic for the Generacion method following the MATLAB code.
        // Return the updated Generados array
        return new int[NGeneraciones][PoblacionEsp.length];
    }*/

}
