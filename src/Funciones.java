import org.graphstream.graph.*;
import org.graphstream.algorithm.generator.BarabasiAlbertGenerator;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;


public class Funciones {


	
	public static void GenerarGrafo(Generados grupo, float[][] probIndividuo) {
        Graph grafoFinal=new SingleGraph("Poblaciones");
		System.setProperty("org.graphstream.ui", "swing");		
		
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
			
			if(j>0) {
				Edge edge=grafoFinal.getEdge(j);
				//edge.setAttribute("ui.length", (grupo.getCantID(j)+grupo.getCantID(j-1)+grupo.getGeneracionID(j))*100);
				edge.setAttribute("ui.style", "fill-color: " + color + ";");
                int lengthEdge = (grupo.getCantID(j - 1))^5;
				edge.setAttribute("ui.length", lengthEdge + "gu");
			}
			
			
		}
		
		

		
		//System.setProperty("org.graphstream.ui", "swing");
		Viewer viewer = grafoFinal.display();
	}
	

}