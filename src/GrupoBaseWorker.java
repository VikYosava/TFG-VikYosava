import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingWorker;

import org.graphstream.stream.file.FileSinkImages.LayoutPolicy;
import org.graphstream.stream.file.FileSinkImages.OutputType;
import org.graphstream.stream.file.images.Resolutions;
import org.graphstream.ui.view.Viewer;
import org.graphstream.graph.Graph;
import org.graphstream.stream.file.FileSinkImages;



public class GrupoBaseWorker extends SwingWorker<List<Generados>, Generados> {
    private Generados nuevoGrupo;
    private float[][] probIndividuo;
    private int nrondas, cantEIni, frecCat;
    private String outputDir;
    private Viewer view;
    private int cont=0;

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
		
		
		LinkedList<Generados> listOfGenerados=new LinkedList<>();
		
		int frecCatastrof=frecCat;
		
		Generados nGrupo=nuevoGrupo;
    	listOfGenerados.add(nGrupo.clone());

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
	        
            Thread.sleep((long) (250*Math.pow(i, 1.5)));
	        publish(prov);
	        
	        // LAURA:
	        //FileSinkImages f=new FileSinkImages(OutputType.PNG,Resolutions.VGA);
	        //f.setLayoutPolicy(LayoutPolicy.COMPUTED_FULLY_AT_NEW_IMAGE);
			//String filePath =outputDir+"graph"+cont+".png";
			//Graph grafoprueba = Funciones.GenerarGrafoG(prov, cantEIni);
	        //f.writeAll(grafoprueba, filePath);
	        
	        
            // Las capturas se realizan en blanco, debería calcular un evento para saber cuándo se han generado
            
	        listOfGenerados.add(prov);

        }
		
		
		return listOfGenerados;
    }

	protected void done() {
		try {
			
			Funciones.GenerarLineas((LinkedList<Generados>) get());
			//Funciones.GenerarGrafo(nuevoGrupo, probIndividuo);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void process(List<Generados> lista) {
		if(view!=null) {
			String filePath =outputDir+"graph"+cont+".png";
	        Funciones.captureImage(view, filePath, 0);
		}
		for(Generados g:lista) {
			view = Funciones.GenerarGrafo(g, cantEIni);
			// hacer captura del anterior
			cont++;
		}
	}
}
