

import org.graphstream.graph.*;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import org.graphstream.graph.Node;
import org.graphstream.ui.swing_viewer.ViewPanel;
import java.awt.GridLayout;

public class Funciones {


	
	public static Viewer GenerarGrafo(Generados grupo, int cantEini) {
        Graph grafoFinal=new SingleGraph("Poblaciones");
		System.setProperty("org.graphstream.ui", "swing");
		// Se crean los nodos padre
		int cantmax=40;	
		int cantmin=3;
	
		
		for(int i=0;i<cantEini;i++) {
			int cant=grupo.getCantID(i);
			short[] especie=grupo.getEspecieID(i);
			String especieString=java.util.Arrays.toString(especie)+i;
			Node node=grafoFinal.addNode(especieString);
			node.setAttribute("Cant", cant);
			if(cant<cantmin) {
				cant=cantmin;
			}else if(cant>cantmax) {
				cant=cantmax;
			}
			
			String color=chooseColor(grupo, i);
			node.setAttribute("ui.style", "size: " + cant + "; fill-color: "+color+";");
			
			node.setAttribute("x", i*50);
			node.setAttribute("y", -i*50);
			//System.out.println("xget "+grafoFinal.getNode(especieString).getAttribute("x"));
		}
		double parte=2;
		for(int j=cantEini;j<grupo.size();j++) {
			// Se construye un nuevo nodo, determinamos su tamaño segun la cantidad de individuos
			// de la especie, y el nombre por el código genético+la posición en la lista de especies
			int cant=grupo.getCantID(j);
			short[] especie=grupo.getEspecieID(j);
			String especieString=java.util.Arrays.toString(especie)+j;
			Node node=grafoFinal.addNode(especieString);
			node.setAttribute("Cant", cant);
			if(cant<cantmin) {
				cant=cantmin;
			}else if(cant>cantmax) {
				cant=cantmax;
			}
			
			
			String color=chooseColor(grupo, j);
			node.setAttribute("ui.style", "size: " + cant + "; fill-color: "+color+";");			
			String padre=grupo.getPadreID(j);
			// Comprobamos si el nodo es padre o hijo
			if(padre!=null) {
				// si es hijo, buscamos a su padre y lo unimos con un edge
				Node parentNode=grafoFinal.getNode(padre);
				int cantPadre=((Number) parentNode.getAttribute("Cant")).intValue();
				
				double x=((Number)parentNode.getAttribute("x")).doubleValue();
				double y=((Number)parentNode.getAttribute("y")).doubleValue();
				double radius=cantmax/4;
				
				double angleInc=(j-cantEini)*parte*Math.PI/(grupo.size()-cantEini);
				parte=parte*3.1415/Math.PI;
				double angle = angleInc;
				
				/*String padrePadre=grupo.getPadreID(j);
				

				if(padrePadre!=null) {
					Node padrePadreNode=grafoFinal.getNode(padrePadre);
					double xpp=((Number)padrePadreNode.getAttribute("x")).doubleValue();
					//System.out.println(padrePadreNode);
					double ypp=((Number)padrePadreNode.getAttribute("y")).doubleValue();
					double dac=xpp-x;
					double dab=Math.sqrt((xpp-x)*(xpp-x)+(ypp-y)*(ypp-y));
					
					if(dab!=0){
						double angPadrePadre=Math.acos(dac/dab);
						//System.out.println(dab);
						//System.out.println(angPadrePadre);
						if(angPadrePadre!=0) {
							angle+=angPadrePadre;
						}
					}
				}*/
				
				x=x+radius*Math.cos(angle);
				y=y+radius*Math.sin(angle);
				
				
				node.setAttribute("x", x);
				node.setAttribute("y", y);
				
				Edge edge=grafoFinal.addEdge(padre+especieString, padre, especieString);
				edge.setAttribute("ui.length", cantPadre+cant);
				edge.setAttribute("ui.style", "fill-color: " + color + ";");
			}

		}
		
		//Node firstNode=grafoFinal.getNode(0);
		//firstNode.setAttribute("xyz", 0,0,0);
		
	    //grafoFinal.setAttribute("layout.force", 1); // Desactivar el layout automático

		Viewer viewer = grafoFinal.display();
		//viewer.disableAutoLayout();
		//viewer.enableXYZfeedback(true);
        viewer.getDefaultView().enableMouseOptions();
        //viewer.getDefaultView().setMouseManager(new NodeClickMouseManager(viewer, grafoFinal));
        //double centx=((Number)grafoFinal.getNode(0).getAttribute("x")).doubleValue();
        //double centy=((Number)grafoFinal.getNode(0).getAttribute("y")).doubleValue();
		
        return viewer;
        
		
		//viewer.getDefaultView().getCamera().setViewCenter(centx, centy, 0);
		        
	}
	
	
	public static void captureImage(Viewer viewer, String filePath, int i) {
        try {
            Thread.sleep((long) (250*Math.pow(i, 1.5)));
        	
            ViewPanel view = (ViewPanel) viewer.getDefaultView();

            // Get the graph rendering as a BufferedImage
            SwingUtilities.invokeAndWait(() -> {
                try {
                	BufferedImage image = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_INT_ARGB);
                    view.paint(image.getGraphics());

                    // Save the image to a file
                    File file = new File(filePath);
					ImageIO.write(image, "png", file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            });
            
            
            //System.out.println("Graph image saved to " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static void GenerarLineas(LinkedList<Generados> listOfGenerados) {
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
	
	private static String chooseColor(Generados grupo, int j){
		short[] esp=grupo.getEspecieID(j);
		int length=grupo.getEspecieID(0).length;
		float valmut=0;
		float valalim=0;
		float valmov=0;
		for(int z=0;z<length;z++) {
			if(z<length/3) {
				valmut+=esp[z];
			}
			else if(z<2*length/3) {
				valalim+=esp[z];
			}
			else {
				valmov+=esp[z];	
			}
		}
		
		int r = (int) (255 * valmut/length*2.5);
	    int g = (int) (255 * valalim/length*2.5);
	    int b = (int) (255 * valmov/length*2.5);
	    
	    String color = String.format("#%02x%02x%02x", r, g, b);

	    return color;
	}
	

	
	private static double calculateDistance(double nx, double ny, double d, double e) {

        return (double)Math.sqrt(Math.pow(nx - d, 2) + Math.pow(ny - e, 2));
    }

}