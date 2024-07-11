import java.util.LinkedList;
import java.util.Random;

import javax.swing.SwingWorker;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.view.Viewer;

public class GrupoBaseWorker extends SwingWorker<Void, Void> {
    private Generados nuevoGrupo;
    private float[][] probIndividuo;
    private int nrondas;
    private Viewer viewer;
    private LinkedList<Generados> listOfGenerados;

    public GrupoBaseWorker(Generados nuevoGrupo1, float[][] probIndividuo, int nrondas) {
    	this.nuevoGrupo=nuevoGrupo1;
    	this.probIndividuo=probIndividuo;
    	this.nrondas=nrondas;
        LinkedList<Generados> listOfLists = new LinkedList<>();

    	this.listOfGenerados=listOfLists;
    	listOfGenerados.add(nuevoGrupo1.clone());

	}

	@Override
    protected Void doInBackground() throws Exception {
    	
		int frecCatastrof=5;

		// Recrea una generación por cada ronda
		for(int i=1;i<=nrondas;i++) {
        	//System.out.println("gen i:"+i);
        	nuevoGrupo.Generacion(probIndividuo,i);
        	
        	// Recorre cada población de una generación y guarda el número de individuos de la población

	        //System.out.println("gen i:"+i);
	        	/*if(i%frecCatastrof==0) {

	        		Random random = new Random();
	                int binario = random.nextInt(2);
	                int posicion=random.nextInt(nuevoGrupo.getEspecieID(0).length);
	                for(int j=0;j<nuevoGrupo.size();j++) {

	                	if(nuevoGrupo.getEspecieID(j)[posicion]==binario) {
	                		nuevoGrupo.setCantID(j, 0);
	                		System.out.println("Eliminado en ronda: "+i);
	                		nuevoGrupo.get(j).imprimirInformacion();
	                	}
	                
	                }
	        	}*/
	        	
	        Generados prov=nuevoGrupo.clone();
	        //System.out.println(" i: "+ i);
	        for(int j=0;j<nuevoGrupo.size();j++) {
	    		String padre=nuevoGrupo.get(j).getPadre();

	    		prov.get(j).setPadre(padre);;
	    	}
	        listOfGenerados.add(prov);

        }
		
		/*grafoFinal.addNode("A" ).setAttribute("xy", 1, 6);
		grafoFinal.addNode("B" ).setAttribute("xy", 2, 4);
		grafoFinal.addNode("C" ).setAttribute("xy", 3, 5);
		grafoFinal.addEdge("AB", "A", "B");
		grafoFinal.addEdge("BC", "B", "C");
		Graph grafoFinal=new SingleGraph("Poblaciones");
		        System.setProperty("org.graphstream.ui", "swing");
				
		        System.out.println(GrupoBase.size());
		        System.out.println(GrupoBase.getCantID(0));
		        int cant=GrupoBase.getCantID(0);
				String nombre=String.valueOf(cant);
				grafoFinal.addNode("1").setAttribute("xy", cant, 0);
				grafoFinal.addNode("2").setAttribute("xy", GrupoBase.getCantID(1), 1);

				/*for(int j=1;j<GrupoBase.size();j++) {
			    	
					// Se almacena la cantidad de individuos para un valor de Y
						int cant=GrupoBase.getCantID(j);
						String nombre=String.valueOf(cant);
						System.out.println(cant);
						grafoFinal.addNode(nombre).setAttribute("xy", cant, j);
					
					}
				
				System.setProperty("org.graphstream.ui", "swing");
				Viewer viewer = grafoFinal.display();
				*/
		/*Viewer viewer=grafoFinal.display();
		viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
		*/
        
        //System.out.println("\nNuevo Grupo:");
        //nuevoGrupo.imprimirDatosGenerados();
		return null;
    }
	/*private static void addGraphToConcatenatedGraph(Graph concatenatedGraph, Graph graph, int i) {
        for (org.graphstream.graph.Node node : graph) {
            concatenatedGraph.addNode(node.getId());
        }
        /*for (org.graphstream.graph.Edge edge : graph.getEachEdge()) {
            concatenatedGraph.addEdge(edge.getId() + "_" + i, edge.getSourceNode().getId(), edge.getTargetNode().getId());
        }
    }*/
	
	protected void done() {
		try {
			for(Generados todos:listOfGenerados) {
				Funciones.GenerarGrafo(todos);

			}
			Funciones.GenerarLineas(listOfGenerados);
			//Funciones.GenerarGrafo(nuevoGrupo, probIndividuo);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
