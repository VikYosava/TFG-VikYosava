import java.util.LinkedList;
import java.util.Random;



public class Generados {
	
    private static final float CMovBase = 0.1f; //	[3]	 Minimo 0, máximo 1 (Capacidad de recolección, incluir comer comida dentro del movimiento)
	private static final float CAlimBase = 0f; //	[2]	 Mínimo 0, Nmax=NinicialInd*X siendo X variable por el usuario, 5 de base
	private static final float CRepartoBase = 1f; //[1]	 Mínimo 1, Nmax=NinicialInd*X siendo X variable por el usuario, 5 de base
	private static final float PMutBase = 0.5f; //	[0]	 Minimo 0, máximo 1
	private LinkedList<PoblacionPorEspecie> elements; 
    private int cont;
	private int cantGlobal;
    
    public Generados() {
    	elements= new LinkedList<>();
    	cont=1;
    }

    public void addGenerado(int number, short[] binaryCode, String padre, int generacion, float alimento) {
    	PoblacionPorEspecie element = new PoblacionPorEspecie(number, binaryCode, padre, generacion, alimento, cont);
        elements.add(element);
        cont++;
    }
    
    public void addGenerado(PoblacionPorEspecie element) {
        element.setPosicion(cont);
    	elements.addLast(element);
    	cont++;
    }

    public void imprimirDatosGenerados() {
        for (PoblacionPorEspecie info : elements) {
            info.imprimirInformacion();
        }
    }
    // Usar esto solo si es necesario, en principio no hará falta porque las poblaciones
    // aumentan en la posición en la que aparecen
    /*public Generado removeGenerado(Generado e) {
        return elements.removeIf();
    }*/
    
    public Generados clone() {
        Generados cloned = new Generados();
        for (PoblacionPorEspecie poblacion : this.elements) {
            // Clona cada elemento lo agrega a la lista clonada
            PoblacionPorEspecie clonedPoblacion = new PoblacionPorEspecie(
                    poblacion.getCant(),
                    poblacion.getEspecie(),
                    poblacion.getPadre(),
                    poblacion.getGeneracion(),
                    poblacion.getAlimento(),
                    poblacion.getPosicion()
            );

            cloned.addGenerado(clonedPoblacion);
        }
        return cloned;
    }
    
    public void setCant(int i, int cant) {
    	elements.get(i).setCant(cant);
    	cantGlobal+=cant;
    }

    public PoblacionPorEspecie get(int i) {
        if (i >= 0 && i < elements.size()) {
            return elements.get(i);
        } else {
            return null;
        }
    }
    
    public int getTotal() {
    	int total=0;
    	for (PoblacionPorEspecie poblacion : this.elements) {
            total=total+poblacion.getCant();
        }
    	return total;
    }
    
    public int getPosicion(int randFromTotal) {
    	int pos=0;
    	while(pos<this.elements.size() && randFromTotal>0){
    		randFromTotal=randFromTotal-this.elements.get(pos).getCant();
    		if(randFromTotal>0) {
    			pos++;
    		}
    	}
    	return pos;
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }    

    public int size() {
        return elements.size();
    }
    
    public int getCantID(int index) {
        return elements.get(index).getCant();
    }
    
    public void setCantID(int index, int cant) {
    	elements.get(index).setCant(cant);
    }
    
    public int[] getCant() {
    	int[] cantidades = new int[elements.size()];
    	for(int i=0;i<elements.size();i++) {
    		cantidades[i]=elements.get(i).getCant();
    	}
    	return cantidades;
    }
    
    public String getPadreID(int index) {
    	return elements.get(index).getPadre();
    }
    
    public float getAlimentoID(int index) {
        return elements.get(index).getAlimento();
    }
    
    public void setAlimentoID(int index, float cant) {
    	elements.get(index).setAlimento(cant);
    }
    
    public short[] getEspecieID(int index) {
    	return elements.get(index).getEspecie();
    }
    
    public int getGeneracionID(int index) {
    	return elements.get(index).getGeneracion();
    }
    
    public void setGeneracionID(int index, int gener) {
    	elements.get(index).setGeneracion(gener);
    }
    
    public int getPosicionID(int index) {
    	return elements.get(index).getPosicion();
    }
    
    public static float[] RandomIntegerArray(int nmutaciones, float base, float max, float deviation) {
        float[] array = new float[nmutaciones]; // nmutaciones también se utilizará para dividir cada valor
        //la suma de todos debe ser aproximadamente la media
        Random random = new Random();

        // Parámetros de ajuste para nextGaussian
        float nmean = base; // media

        for (int i = 0; i < nmutaciones; i++) {
            float value;
            do {
                // Generar un número aleatorio basado en una distribución gaussiana
            	
                value = (float) (random.nextGaussian() * deviation + nmean);
            } while (value < -max || value > max); // Asegurar que el valor esté dentro del rango deseado

            array[i] = value;
        }
        return array;
    }
    
	public Generados Generacion(float[][] probIndividuo, int generacion) {
    	Generados nuevoGrupo= this;
    	int tamanioGen=nuevoGrupo.size(); // De esta forma los que nacen nuevos no atacan ni se reproducen hasta la siguiente generación
    	
    	
    	
    	for (int i = 0; i<tamanioGen;i++) {
    		
    		
    		
    		int cantInd=nuevoGrupo.getCantID(i);
    		short[] especieI=nuevoGrupo.getEspecieID(i);
    		///////////////////
    		int gen=generacion;
    		float alimento=nuevoGrupo.getAlimentoID(i);
    		int pos=nuevoGrupo.getPosicionID(i);
    		
    		if(cantInd>0) {
    			
    		
    		float CMov=CMovBase;
    		
			for(int k = 0; k<probIndividuo[3].length; k++) {
				CMov=CMov+probIndividuo[3][k]*nuevoGrupo.getEspecieID(i)[k];
			}
			
			if(CMov<0) {
				CMov=0;
			}
			else if(CMov>1) {
				CMov=1;
				alimento++;
			}
			
    		
    		for (int k = 0; k<cantInd*CMov;k++) {
    			
    			int posAtacado=(int) (Math.random()*(nuevoGrupo.getTotal()-nuevoGrupo.getCantID(i)))+nuevoGrupo.getCantID(i);
    			
    			//System.out.println("i: "+i);
    			//System.out.println("Total: "+nuevoGrupo.getTotal());
    			//int num=nuevoGrupo.getTotal()-i;
    			//System.out.println("Total-i: "+num);
    			//System.out.println("posAtacado: "+posAtacado);
    			
    			int iAtacado=nuevoGrupo.getPosicion(posAtacado);
    			while (nuevoGrupo.getCantID(iAtacado)==0){ // dentro de getpos
    				iAtacado++;
    			}
    			
    			//System.out.println("iAtacado: "+iAtacado);
    			
    			// HACER UN GENERADOR DE NÚMEROS ALEATORIOS QUE ELIJA CON PRIORIDAD LA ESPECIE CON MÁS INDIVIDUOS
    			// 1. OPERACIÓN GENERA UN ARRAY DE LAS CANTIDADES DE CADA ESPECIE array
    			// 2. OpRandom(especieI,array) - if especieI tiene gen de evitar array[x], array[x]=0
    			// 3. OpRandom devuelve la posición del array que es elegida int iAtacado.
    			
        		float alimentoAt=nuevoGrupo.getAlimentoID(iAtacado);
        		int cantIndAt=nuevoGrupo.getCantID(iAtacado);
        		
    			int come=0;
    			come=ComerOComida(nuevoGrupo.get(i),nuevoGrupo.get(iAtacado));
    			
    			if(i==iAtacado) {
    				come=0;
    			}
    			
    			switch (come) { 
    		    case 0:
    		     // ATACANTE COME CON ÉXITO
    		    	cantIndAt--;
    		    	nuevoGrupo.setCantID(iAtacado,cantIndAt); // Alimento variable
    		    	alimento++;
    		    	nuevoGrupo.setAlimentoID(i,alimento);
    		     break;
    		    case 1:
    		    	cantInd--;
    		    	nuevoGrupo.setCantID(i,cantInd);
    		    	alimentoAt++;
    		    	nuevoGrupo.setAlimentoID(iAtacado,alimentoAt);
    		     // ATACANTE ES COMIDO
    		     break;
    		    default:
    		     // NINGUNO SE COME AL OTRO
    		  }
    			
    			
    			// Funciona así????
        		// actualiza la lista por si han muerto individuos
    			
    			// se restan individuos si mueren, se suma alimento si matan o generan de alguna forma
    			// pob.menosCant() si muere alguien;
    			
    		}
    		
    		int cantInd1=cantInd;
    		//
    		
    		// PROCESO DE VIDA, CALCULA CUANTOS SOBREVIVEN Y LA CANTIDAD DE ALIMENTO QUE OBTIENEN DE MUTACIONES
    		// alimentoCoste contiene un numero real que indica la cantidad de alimento necesaria por individuo
    		// se calcula a partir de la matriz de probabilidades
    		

    		float CAlim=CAlimBase;
    		
			for(int k = 0; k<probIndividuo[2].length; k++) {
				CAlim=CAlim+probIndividuo[2][k]*nuevoGrupo.getEspecieID(i)[k];
			}
			
			if(CAlim<0) {
				CAlim=0;
			}
			
			
    		
    		// Este valor indica la cantidad de alimento necesaria por cada célula para reproducirse
    		// Puede aumentar o disminuir según las mutaciones.
			// El valor base será 1, 1 célula inicial proporciona 1 alimento base, como una moneda de cambio.
    		// El valor inicial debería ser 1 o superior ya que las células no pueden reproducirse de la nada, pero la simulación
    		// tendría que ser demasiado grande para mi máquina si quiero que funcione bajo estas condiciones (muchas células iniciales)
    		// lo cual tampoco es realista del todo, porque en teoría deberían formarse solo unas pocas células iniciales en la sopa
    		// primigenia.
    		// En este caso, por tanto, asumiremos que la sopa primigenia aporta alimento extra a todas las células, por lo que el
    		// coste inicial es menor de 1.
			
		
    		//Generados2
			
			//System.out.println("cantInd: "+cantInd);
			//System.out.println("alimento*alimentoCoste: "+alimento*alimentoCoste);
			
			float CReparto=CRepartoBase;
			for(int k = 0; k<probIndividuo[1].length; k++) {
				CReparto=CReparto+probIndividuo[1][k]*nuevoGrupo.getEspecieID(i)[k];
			}
			
			if(CReparto<1) {
				CReparto=1;
			}
			
			
			int Nvivos=(int) (cantInd*CMov*CReparto); // Si cantmov es 0 significa que no han interactuado por cuenta propia
												// Debe haber un caso a parte si tienen forma de generar alimento por si mismos
											// Si reparto es 1, no se da alimento a los individuos que no lo obtengan
										// El núm max de reparto sería el de individuos de la población, pero en la práctica
									// este número no hará falta casi, y es dificil de calcular cada vez, por lo que 
								// es mejor fijar alguna heurística. Nmax=NinicialInd*X siendo X variable por el usuario, 5 de base
							// 
			if(Nvivos>cantInd){
				Nvivos=cantInd;
			}
			
			if(CAlim!=0) {
				if(alimento/CAlim<Nvivos) { // Si hay menos comida de la que es necesaria para sobrevivir, el exceso muere
	    			
					Nvivos=(int) (alimento/CAlim);
					alimento=1;
	    		}
			}
			else {
				Nvivos=cantInd; // Si el coste de vivir es 0, el número de individuos se mantiene igual
			}
			cantInd=Nvivos;
			nuevoGrupo.setAlimentoID(i,alimento);
			nuevoGrupo.setCantID(i,cantInd);
			
			
			
			
			/*System.out.println("Cantidad:"+cantInd);
			System.out.println("Cantidad Antes:"+cantInd1);
			System.out.println("");*/
			
			//System.out.println("Num Reprod:"+cantInd*CMov*CReparto);
			
			
			
			// 
			
			float PMut=PMutBase;
			for(int k = 0; k<probIndividuo[0].length; k++) {
				PMut=PMut+probIndividuo[0][k]*nuevoGrupo.getEspecieID(i)[k];
			}
						
			
			
			if(PMut<0) {
				PMut=0;
			}
			
			float PMuttot=PMut;

			
			if(PMut>1) {
				PMut=1;
			}
			
			
			int Nreprod=Nvivos;
			
			// Se determina cuántos se van a reproducir, se asume que crear un individuo igual cuesta el 
			// mismo alimento que sobrevivir
			if(alimento<Nvivos) {
				Nreprod=(int) alimento;
			}
			alimento=alimento*CAlim;
			
			if(Nreprod>cantInd){
				Nreprod=cantInd;
			}
			
			// Se calcula cuantos se pueden reproducir
			/*System.out.println("cantInd: "+cantInd);
			System.out.println("Nreprod: "+Nreprod);
			System.out.println("Nreprod: "+Nreprod*PMut);*/
			
			for (int j=0; j<((int)(Nreprod*PMut)); j++) {
			    

    			

    			// LA REPRODUCCIÓN AQUI TIENE QUE SER SOLO DE LOS QUE SÍ MUTAN
				short[] Padre=nuevoGrupo.getEspecieID(i).clone();
				
				String padreStr=java.util.Arrays.toString(Padre)+i;
				
    			short[] Hijo=Reproducir(nuevoGrupo.getEspecieID(i),PMuttot);

    			nuevoGrupo.addGenerado(1, Hijo, padreStr, generacion, 1);
    			
    			//int lastPos=nuevoGrupo.size()-1;
    			
    			
    		// i es la posición de la población que se está reproduciendo	
    			//nuevoGrupo.get(lastPos).setPadre(Padre);
    			
    			//System.out.println(nuevoGrupo.get(lastPos).getPadre());
    			// Padre es el código genético, junto con la generación en la
    			
    			//System.out.println("Hijo: " + lastPos);

    			//System.out.println("Padre: " + Padre+i);
    			
    			
    			/*
    			//System.out.println("Anterior:"+java.util.Arrays.toString(nuevoGrupo.get(i).getEspecie()));
    			//System.out.println("NuevoGrp:"+java.util.Arrays.toString(Hijo));

				//System.out.println(mismoGenoma(Hijo,nuevoGrupo.get(i).getEspecie()));
				
    			if(mismoGenoma(Hijo,nuevoGrupo.getEspecieID(i))) {
    				cantInd++;
    				nuevoGrupo.setAlimentoID(i,1); // Calcular Alimento mejor
    				nuevoGrupo.setCantID(i,cantInd);
    				//System.out.println("Nuevo no añadido:" + java.util.Arrays.toString(nuevoGrupo.get(i).getEspecie()));
    				
    			}
    			else{
    				
    				nuevoGrupo.addGenerado(1, Hijo, generacion, 1);
    				//System.out.println("Nuevo añadido");
    				//System.out.println("Generacion: "+generacion);
    			}*/
    			
    		}
			
			
			
			
			nuevoGrupo.setCantID(i,(int) (cantInd+(Nreprod-Nreprod*PMut)));
			
			
			
			
			/*for (int z = 0; z<cantInd*CMov*CReparto;z++) { // Solo se reproducen si hay algún individuo vivo
	    		if(alimento<cantInd*CAlim) { // Si hay menos comida de la que es necesaria para sobrevivir, el exceso muere
	    			cantInd=(int) (alimento*CAlim);
	    			nuevoGrupo.setAlimentoID(i,1);
	    			nuevoGrupo.setCantID(i,cantInd);
	    		}
	    		else {
		    		for (int j=0; j<(alimento-cantInd*CAlim); j++) {
		    			
		    			double PMut=0.1;
		    			for(int k = 0; k<ProbIndividuo[0].length; k++) {
		    				PMut=PMut+ProbIndividuo[0][k]*nuevoGrupo.getEspecieID(i)[k];
		    			}
		    			

		    			// LA REPRODUCCIÓN AQUI TIENE QUE SER SOLO DE LOS QUE SÍ MUTAN
		    			short[] Hijo=Reproducir(nuevoGrupo.getEspecieID(i).clone(),PMut);
		    			//System.out.println("Anterior:"+java.util.Arrays.toString(nuevoGrupo.get(i).getEspecie()));
		    			//System.out.println("NuevoGrp:"+java.util.Arrays.toString(Hijo));

	    				//System.out.println(mismoGenoma(Hijo,nuevoGrupo.get(i).getEspecie()));
	    				
		    			if(mismoGenoma(Hijo,nuevoGrupo.getEspecieID(i))) {
		    				cantInd++;
		    				nuevoGrupo.setAlimentoID(i,1); // Calcular Alimento mejor
		    				nuevoGrupo.setCantID(i,cantInd);
		    				//System.out.println("Nuevo no añadido:" + java.util.Arrays.toString(nuevoGrupo.get(i).getEspecie()));
		    				
		    			}
		    			else{
		    				nuevoGrupo.addGenerado(1, Hijo, generacion, 1);
		    				//System.out.println("Nuevo añadido");
		    			}
		    			
		    		}
	    		}
    		}*/
    		
    		
    		}
    	}
    	
    	//generacion++;
    	
		return nuevoGrupo;
    }

	public static short[] Reproducir(short[] especie, float PMut) {
    short[] hijo=especie.clone();
	
    Random random = new Random();


    	while(PMut>0) {
        
        // Generar un número aleatorio entre 0 y x (ambos inclusive)
    	
        int locmutacion = random.nextInt(especie.length); // LA POSICIÓN DEPENDE DEL TAMAÑO DE LA MATRIZ DE 
        							// COSAS ALEATORIAS, CORTAR A LA ALTURA DE MUTACIONES ESPECÍFICAS
        
        hijo[locmutacion]=1;

    	/*else {
    		hijo[locmutacion]=0;
    	}*/
        PMut--;
    	}

    
    	return hijo;
	}

	/*public static float round(double value, int decimalPlaces) {
    float factor = (float) Math.pow(10, decimalPlaces);
    return Math.round(value * factor) / factor;
}*/

	public static int ComerOComida(PoblacionPorEspecie Atacante, PoblacionPorEspecie Atacado){
		{
			int come=3;
			// Mutaciones específicas
			
			// Tamaño
			// Medio interno que digiere
			// Resistencia a medio interno (contra tamaño)
			// Toxinas
			// Resistencia a Toxinas
			// Detección base (busca cualquier ser)
			// Detección tipo 1 (busca seres con determinado gen)
			// Detección tipo 2 (evita seres con determinado gen)
			
			// Atacante intenta comer Atacado
			// Puede comer
			// Ser comido
			// Nada
			
			// En caso de empate de detecciones (uno huye y el otro persigue) incluir movilidad

			return come;
		}
}
    
	public static boolean mismoGenoma(short[] hijo, short[] s) {
		for (int i = 0; i < hijo.length; i++) {
			if (hijo[i] != s[i]) {
				return false;
			}
		}

		return true;
	}

    
}