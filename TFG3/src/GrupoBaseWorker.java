import java.util.LinkedList;
import java.util.Random;

import javax.swing.SwingWorker;
import org.graphstream.ui.view.Viewer;




public class GrupoBaseWorker extends SwingWorker<Generados, Viewer> {
    private Generados nuevoGrupo;
    private float[][] probIndividuo;
    private int nrondas, cantEIni, frecCat;
    private String outputDir;
    private LinkedList<Generados> listOfGenerados;

    public GrupoBaseWorker(Generados nuevoGrupo1, float[][] probIndividuo, int nrondas, int fCantEInicial, String directorio, int frecCatastrof) {
    	this.nuevoGrupo=nuevoGrupo1;
    	this.probIndividuo=probIndividuo;
    	this.nrondas=nrondas;
    	this.cantEIni=fCantEInicial;
    	this.outputDir="./"+directorio+"/";
    	this.frecCat=frecCatastrof;
        LinkedList<Generados> listOfLists = new LinkedList<>();

    	this.listOfGenerados=listOfLists;
    	listOfGenerados.add(nuevoGrupo1.clone());

	}

	@Override
    protected Generados doInBackground() throws Exception {
    	
		int frecCatastrof=frecCat;
		
		Generados nGrupo=nuevoGrupo;
		
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
            Viewer grafoFinal = Funciones.GenerarGrafo(prov, cantEIni);
            // Las capturas se realizan en blanco, debería calcular un evento para saber cuándo se han generado
            
            //Thread.sleep(grafoFinal.getGraphicGraph().getNodeCount());
            String filePath =outputDir+"graph"+i+".png";
            Funciones.captureImage(grafoFinal, filePath, i);

	        //System.out.println(" i: "+ i);
	        listOfGenerados.add(prov);

        }
		
		
		return null;
    }

	protected void done() {
		try {

			Funciones.GenerarLineas(listOfGenerados);
			//Funciones.GenerarGrafo(nuevoGrupo, probIndividuo);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
