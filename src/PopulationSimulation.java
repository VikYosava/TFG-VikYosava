import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;


public class PopulationSimulation {
    public static void main(String[] args) {
    	
        int Nmutaciones = 10; // aumentar este número afecta mucho menos al coste de computación que aumentar el número de rondas
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
        LinkedList<Generados> ListOfLists = new LinkedList<>();
        
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
        
        ListOfLists.add(GrupoBase);
        ListOfLists.add(NuevoGrupo1.clone());

        
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
        	ListOfLists.add(NuevoGrupo1.clone());
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


        createAndShowGUI(ListOfLists);
        
    }

    private static void createAndShowGUI(LinkedList<Generados> listOfGenerados) {
    	 JFrame frame = new JFrame("Simulación de Poblaciones");
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setSize(800, 600);
         frame.setLayout(new GridLayout(1, 1)); // Single panel layout

         // Assuming each Generados object has population data accessible
         int steps = listOfGenerados.size(); // Number of generations
         MyChartMultiline chart = new MyChartMultiline(steps);

         
         //listOfGenerados.size es el número de generaciones
         
         // cada lista listOfGenerados.get(i) contiene 1 valor de todas las poblaciones
         
         // cada linea del array debe contener todos los valores de 1 generación
         
         int sizeGeneradosFinal=listOfGenerados.get(steps-1).size();
         //System.out.println("control");
         //listOfGenerados.get(steps-1).imprimirDatosGenerados();
         //System.out.println("control");

         /*for (int i = 0; i < steps; i++) {
    		 List<Integer> populationData = new ArrayList<>();

        	 for (int j = 0; j < listOfGenerados.get(i).size(); j++) {
             	System.out.println(listOfGenerados.get(i).getCantID(j));

                 populationData.add(listOfGenerados.get(i).getCantID(j));

        	 }
             chart.updateList(i, populationData);

         }*/

         

         for (int j = 0; j < listOfGenerados.get(steps-1).size(); j++) {
    		 List<Integer> populationData = new ArrayList<>();

        	 for (int i = 0; i < steps; i++) {
        		 
        		 if(j<listOfGenerados.get(i).size()) {
        			 //System.out.println(listOfGenerados.get(i).getCantID(j));
            		 populationData.add(listOfGenerados.get(i).getCantID(j));
        		 }

        	 }
             chart.updateList(j, populationData);

         }

         JScrollPane scrollPane = new JScrollPane(chart);
         frame.add(scrollPane);
         frame.setVisible(true);
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

