import org.graphstream.graph.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.geom.Point3;
import org.graphstream.ui.graphicGraph.GraphicElement;
import org.graphstream.ui.graphicGraph.GraphicNode;
import org.graphstream.ui.swing_viewer.util.DefaultMouseManager;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Funciones {


	
	public static void GenerarGrafo(Generados grupo) {
        Graph grafoFinal=new SingleGraph("Poblaciones");
		System.setProperty("org.graphstream.ui", "swing");
		
			for(int j=0;j<grupo.size();j++) {
				// Se almacena la cantidad de individuos para un valor de Y
				int cant=grupo.getCantID(j);
				
				//if(cant>1) {
				short[] especie=grupo.getEspecieID(j);
					
				// Cada nodo tendrá el nombre de la especie y un número, para que no se repitan
					
				
				String padre=java.util.Arrays.toString(grupo.getPadreID(j))+(grupo.getPosicionID(j)-1);
				//System.out.println(padre);
				String especieString=java.util.Arrays.toString(especie)+j;
					
				grafoFinal.addNode(especieString).setAttribute("Cant", cant);
				
				grafoFinal.getNode(especieString).setAttribute("ui.style", "size: " +cant+"px;");
				System.out.println(padre);
				System.out.println(especieString);

				if(!padre.equals(especieString)) {
						//org.graphstream.graph.Node parentNode=grafoFinal.getNode("padre");
					Node parentNode=grafoFinal.getNode(padre);
					int cantPadre=((Number) parentNode.getAttribute("Cant")).intValue();
					int generacion=grupo.getGeneracionID(j);
					int base=generacion*10+50;

					
					double radius=(cantPadre+cant)/20+base;
					double angleInc=2*Math.PI;
					double angle = j*angleInc/grupo.size();
					
					
					
					int x=((Number)parentNode.getAttribute("x")).intValue();
					x=(int) (x+radius*Math.cos(angle));
					

					int y=((Number)parentNode.getAttribute("y")).intValue();
					y=(int) (y+radius*Math.sin(angle));
					
					
					grafoFinal.getNode(especieString).setAttribute("x", x);
					grafoFinal.getNode(especieString).setAttribute("y", y);

					
					Edge edge=grafoFinal.addEdge(padre+especieString, padre, especieString);
					//edge.setAttribute("weight", (cantPadre+cant)/20+base);
					//edge.setAttribute("layout.weight", (cantPadre+cant)/20+base);
				}
				else {
					grafoFinal.getNode(especieString).setAttribute("x", j*100);
					grafoFinal.getNode(especieString).setAttribute("y", -j*100);
					System.out.println("xget "+grafoFinal.getNode(especieString).getAttribute("x"));
				}
					
				
			}
			
		/*for(int j=0;j<grupo.size();j++) {
			// Se almacena la cantidad de individuos para un valor de Y
			int cant=grupo.getCantID(j);
			
			//if(cant>1) {
				short[] especie=grupo.getEspecieID(j);
				
				// Cada nodo tendrá el nombre de la especie y un número, para que no se repitan
				
				
				String padre=grupo.get(j).getPadre();
				String especieString=java.util.Arrays.toString(especie)+j;
					
				Node node=grafoFinal.addNode(especieString);
				node.setAttribute("Cant", cant);
				node.setAttribute("ui.style", "size: " +cant+"px;");
				
				if(padre!="No padre") {
					//org.graphstream.graph.Node parentNode=grafoFinal.getNode("padre");
					int cantPadre=((Number) grafoFinal.getNode(padre).getAttribute("Cant")).intValue();
					int generacion=grupo.getGeneracionID(j);
					int base=generacion*10+50;
					Edge edge=grafoFinal.addEdge(padre+especieString, padre, especieString);
					edge.setAttribute("weight", (cantPadre+cant)/20+base);
					edge.setAttribute("layout.weight", (cantPadre+cant)/20+base);
				}
				else {
					node.setAttribute("xy", -j * 100, 50*Math.cos(j*Math.PI));
				}
				
				//grafoFinal.setAttribute("layout.force", 2);
		        //grafoFinal.setAttribute("layout.quality", 4);
			
		}
		

		/*
		BarabasiAlbertGenerator generator=new BarabasiAlbertGenerator();
		generator.addSink(grafoFinal);
		generator.begin();
		
		for(int j=0;j<grupo.size();j++) {
			generator.nextEvents();
		}
		
		int length=grupo.getEspecieID(0).length;		
		generator.end();
		
		for(int j=0;j<grupo.size();j++) {
			Node nodo=grafoFinal.getNode(j);
			int cant=grupo.getCantID(j);
			
			short[] esp=grupo.getEspecieID(j);
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

			nodo.setAttribute("ui.style", "size: " + cant + "px; fill-color: "+color+";");
			//System.out.println(java.util.Arrays.toString(esp));
			//nodo.setAttribute("ui.label", java.util.Arrays.toString(esp));
			
			
			
			if(j>0) {
				Edge edge=grafoFinal.getEdge(j);
				//edge.setAttribute("ui.length", (grupo.getCantID(j)+grupo.getCantID(j-1)+grupo.getGeneracionID(j))*100);
				edge.setAttribute("ui.style", "fill-color: " + color + ";");
                int lengthEdge = (grupo.getCantID(j - 1))^5;
				edge.setAttribute("ui.length", lengthEdge + "gu");
			}
			
			//nodo.setAttribute("ui.class", "hoverable");
			
		}
		*/
		//Node firstNode=grafoFinal.getNode(0);
		//firstNode.setAttribute("xyz", 0,0,0);
		
		
		
	    grafoFinal.setAttribute("layout.force", 0); // Desactivar el layout automático

		Viewer viewer = grafoFinal.display();
		//viewer.enableAutoLayout();
		//viewer.enableXYZfeedback(true);
        //viewer.getDefaultView().setMouseManager(new NodeClickMouseManager(viewer));
		//viewer.getDefaultView().getCamera().setViewCenter(0, 0, 0);
		
		
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
	
	private static class NodeClickMouseManager extends DefaultMouseManager {
        private Viewer viewer;
        public NodeClickMouseManager(Viewer viewer) {
            this.viewer = viewer;
        }
		
		@Override
        public void mouseClicked(MouseEvent event) {
            //System.out.println("click "+event.getX());
            
            Node closestNode=null;
            double minDistance=Double.MAX_VALUE;
            
            
            for(Node node:viewer.getGraphicGraph()) {
            	event.getX();
            	event.getXOnScreen();
            	viewer.getDefaultView().allGraphicElementsIn(getManagedTypes(), minDistance, minDistance, minDistance, minDistance);
            	//double nodeX = viewer.getDefaultView().getCamera().transformGuToPx(node.getAttribute("x"), node.getAttribute("y"),node.getAttribute("z"));
            	         	
            	//double nodeZ = viewer.getDefaultView().getCamera().getViewCenter().z;            	

            	//Point3 nodeP= viewer.getDefaultView().getCamera().transformGuToPx(nodeX, nodeY, nodeZ);
            	//System.out.println("Node: "+node.getAttribute("x"));

            	
            	/*double distance=calculateDistance(nodeP.x, nodeP.y, event.getX(), event.getY());
            	if (distance < minDistance) {
                    minDistance = distance;
                    closestNode = node;
                }*/
            }
        	//System.out.println("click: "+event.getX());
            
            
        }
    }
	
	private static double calculateDistance(double nx, double ny, int x, int y) {

        return (double)Math.sqrt(Math.pow(nx - x, 2) + Math.pow(ny - y, 2));
    }

}