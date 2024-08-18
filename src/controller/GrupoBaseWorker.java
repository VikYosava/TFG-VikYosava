package controller;
import java.awt.BorderLayout;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;

import javax.swing.SwingWorker;

import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.images.Resolutions;
import org.graphstream.ui.swing_viewer.ViewPanel;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

import model.Funciones;
import model.Generados;
import model.MyChartMultiline;
import view.MainFrame;

import org.graphstream.graph.Graph;
import org.graphstream.stream.file.FileSinkImages;



public class GrupoBaseWorker extends SwingWorker<List<Generados>, Graph> {
    private Generados nuevoGrupo;
    private float[][] probIndividuo;
    private int nrondas, cantEIni, frecCat;
    private String outputDir;

    public GrupoBaseWorker(Generados nuevoGrupo1, float[][] probIndividuo, int nrondas, int fCantEInicial, String directorio, int frecCatastrof) {
    	this.nuevoGrupo=nuevoGrupo1;
    	this.probIndividuo=probIndividuo;
    	this.nrondas=nrondas;
    	this.cantEIni=fCantEInicial;
    	this.outputDir="./"+directorio+"/";
    	this.frecCat=frecCatastrof;
	}

	@Override
    protected List<Generados> doInBackground() throws Exception {
		
		System.setProperty("org.graphstream.ui", "swing");
		FileSinkImages f = FileSinkImages.createDefault();
        f.setOutputType(FileSinkImages.OutputType.PNG);
        f.setResolution(Resolutions.UHD_4K);
        f.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
        
		LinkedList<Generados> listOfGenerados=new LinkedList<>();
		
		int frecCatastrof=frecCat;
		
		Generados nGrupo=nuevoGrupo;
    	listOfGenerados.add(nGrupo.clone());
    	String filePath=null;
		// Recrea una generación por cada ronda
		for(int i=1;i<=nrondas;i++) {
        	//System.out.println("gen i:"+i);
			nGrupo.Generacion(probIndividuo,i);
        	
        	// Recorre cada población de una generación y guarda el número de individuos de la población

	        //System.out.println("gen i:"+i);
	        	if(i%frecCatastrof==0) {

	        		Random random = new Random();
	                int binario = random.nextInt(2);
	                int posicion=random.nextInt(nGrupo.getEspecieID(0).length);
	                for(int j=0;j<nGrupo.size();j++) {

	                	if(nGrupo.getEspecieID(j)[posicion]==binario) {
	                		nGrupo.setCantID(j, 0);
	                		//System.out.println("Eliminado en ronda: "+i);
	                		nGrupo.get(j).imprimirInformacion();
	                	}
	                
	                }
	        	}
	        	
	        Generados prov=nGrupo.clone();
	        
	        Graph grafoprueba = Funciones.GenerarGrafoG(nGrupo, cantEIni);
	        publish(grafoprueba);
	        
	        // LAURA: captura de las imagenes 1 a nrondas
	         filePath =outputDir+"graph"+i+".png";			
	        f.writeAll(grafoprueba, filePath);
	        
	        // LAURA:
	        //FileSinkImages f=new FileSinkImages(OutputType.PNG,Resolutions.VGA);
	        //f.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
			//String filePath =outputDir+"graph"+cont+".png";
			//Graph grafoprueba = Funciones.GenerarGrafoG(prov, cantEIni);
	        //f.writeAll(grafoprueba, filePath);
	        
	        
            // Las capturas se realizan en blanco, debería calcular un evento para saber cuándo se han generado
            
	        listOfGenerados.add(prov);

        }
		nGrupo.imprimirDatosGenerados("C:/Users/vikto/git/TFGVY/TFG3/Imagenes/poblaciones.arff", probIndividuo);
		
		return listOfGenerados;
    }

	protected void done() {
		try {
			
			
			Funciones.GenerarLineas((LinkedList<Generados>) get());
			//MainFrame.showChartPanel(Funciones.GenerarLineas((LinkedList<Generados>) get()));
			//Funciones.GenerarGrafo(nuevoGrupo, probIndividuo);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*protected void process(List<Graph> lista) {
		for(Graph g:lista) {
			Viewer viewer = g.display();
	        viewer.getDefaultView().enableMouseOptions();
		}
	}*/
}
