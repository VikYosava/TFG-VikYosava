import java.awt.EventQueue;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Variables {

	private JFrame frame;
	private JTable tableMutaciones, tablaCantidades;
	int Nmutaciones, CantEInicial, Niteraciones, frecC;
	String txt, direc;
	private DefaultTableModel model, model2;
	

	/**
	 * Launch the application.
	 * @param nIt 
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Variables window = new Variables(fNmutaciones, fCantEInicial);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	public Variables(int fCantEInicial, int fNmutaciones, int nIt, String mtxt, String dir, int frec) {
		this.Nmutaciones=fNmutaciones;
		this.CantEInicial=fCantEInicial;
		this.Niteraciones=nIt;
		this.txt=mtxt;
		this.direc=dir;
		this.frecC=frec;
		initialize(CantEInicial, Nmutaciones, Niteraciones, txt, direc, frecC);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(int fCantEInicial, int fNmutaciones, int fNiteraciones, String txt, String dir, int frec) {
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// IF IMPORT DATA FROM CONFIGINICIAL TRUE, DATA=IMPORTED DATA, else:
		
		Short[][] data = new Short[fCantEInicial][fNmutaciones];
		for (int i = 0; i < fCantEInicial; i++) {
			for (int j = 0; j < fNmutaciones; j++) {
                    data[i][j] = 0;
            }
        }
		
		String[] columnNames = new String[fNmutaciones];
        for (int i = 0; i < fNmutaciones; i++) {
            columnNames[i] = String.valueOf(i + 1);
        }
        
        Short[][] data2=new Short[fCantEInicial][2];
        for (int i = 0; i < fCantEInicial; i++) {
			for (int j = 0; j < 2; j++) {
                data2[i][j] = 20;
			}
        }
        
        String[] columnNames2 = new String[2];
        columnNames2[0]="CI";
        columnNames2[1]="CA";

        
        
       // añadir barra que permita moverse en X también
        // añadir nombres a las filas, no solo a las columnas
		model=new DefaultTableModel(data, columnNames);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		tableMutaciones = new JTable(model);
		tableMutaciones.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		int columnWidth = 20; // set your desired fixed width here
        for (int i = 0; i < tableMutaciones.getColumnCount(); i++) {
            TableColumn column = tableMutaciones.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidth);
        }
		tableMutaciones.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int inc = e.getButton() == MouseEvent.BUTTON1?1:-1;

				int row = tableMutaciones.rowAtPoint(e.getPoint());
				int col = tableMutaciones.columnAtPoint(e.getPoint());
				if(row >=0 && col >=0 ) {		
					
					model.setValueAt(Short.parseShort(model.getValueAt(row, col).toString())+inc,row,col);
				}
			
			
			}
		});
		
		JScrollPane scrollPane=new JScrollPane(tableMutaciones);
        frame.getContentPane().add(scrollPane);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		
		model2= new DefaultTableModel(data2, columnNames2);
		tablaCantidades=new JTable(model2);
		tablaCantidades.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		columnWidth=30;
        for (int i = 0; i < tablaCantidades.getColumnCount(); i++) {
            TableColumn column = tablaCantidades.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidth);
        }
        JScrollPane scrollPane2=new JScrollPane(tablaCantidades);
        frame.getContentPane().add(scrollPane2);
        scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        JSplitPane splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, scrollPane2);
        frame.getContentPane().add(splitPane, BorderLayout.CENTER);
        
        JScrollBar verticalScrollBar1 = scrollPane.getVerticalScrollBar();
        JScrollBar verticalScrollBar2 = scrollPane2.getVerticalScrollBar();
        verticalScrollBar1.addAdjustmentListener(e -> verticalScrollBar2.setValue(e.getValue()));
        verticalScrollBar2.addAdjustmentListener(e -> verticalScrollBar1.setValue(e.getValue()));
        
        splitPane.setDividerLocation(600);
        
        frame.setVisible(true);
		
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		
		
		
		JLabel lblIndBase = new JLabel("Código genético de los Individuos (En Binario)");
		panel.add(lblIndBase);
		
		JPanel panel_1 = new JPanel();
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		/*tableMutaciones.getModel().addTableModelListener(new TableModelListener() {
		    @Override
		    public void tableChanged(TableModelEvent e) {
		        int row = e.getFirstRow();
		        int column = e.getColumn();
		        
		    }
		});*/
		
		//tableMutaciones.add
		
		
		JButton btnOk = new JButton("Ok");
		panel_1.add(btnOk);
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (tableMutaciones.isEditing()) {
		            tableMutaciones.getCellEditor().stopCellEditing();
		        }
				
				if(tablaCantidades.isEditing()) {
		            tablaCantidades.getCellEditor().stopCellEditing();

				}
				
				short[][] IndBase=new short[fCantEInicial][fNmutaciones];

				int[] NPoblacion = new int[fCantEInicial];
		        int[] Alimento = new int[fCantEInicial];
				
				for(int j=0; j<fCantEInicial;j++) {
					
					NPoblacion[j]=Integer.parseInt(model2.getValueAt(j, 0).toString());
					Alimento[j]=Integer.parseInt(model2.getValueAt(j, 1).toString());
					for(int i=0; i<fNmutaciones;i++) {
						Short cellVal=Short.parseShort(model.getValueAt(j, i).toString());

							IndBase[j][i]=((Number) cellVal).shortValue();						
					}
				}
				
				/*char[] charIndBase=fIndBase.getValue().toString().toCharArray();
				
				short[] IndBase = new short[Nmutaciones];
				for(int i=0;i<Nmutaciones;i++) {
					IndBase[i]=(short)charIndBase[i];
				}*/
				
		        // número de posibles mutaciones que pueden aparecer en la simulación
		        
		        
		        
		        int ronda0 = 0;
		        
		        float[][] ProbIndividuo= new float[4][fNmutaciones];
		        // Programamos el default
		        /*private static final double CMovBase = 0.9; //	[3]	 Minimo 0, máximo 1
		    	private static final double CAlimBase = 0; //		[2]	 Mínimo 0, Nmax=NinicialInd*X siendo X variable por el usuario, 5 de base
		    	//private static final int CRepartoBase = 1; //		[1]	 Mínimo 1, Nmax=NinicialInd*X siendo X variable por el usuario, 5 de base
		    	private static final double PMutBase = 0.1; //		[0]	 Minimo 0, máximo 1*/
		        
		        float maxPMut=1;
		        float maxCRep=NPoblacion[0]*5;
		        float maxCAlim=maxCRep;
		        float maxCMov=maxPMut;
		        
		        
		        // agresividad del medio: valor por el que puede morir una especie aleatoria cada cierto tiempo
		        // ley de los grandes numeros - gauss, bioestadistica
		        
		        ProbIndividuo[0]=Generados.RandomIntegerArray(Nmutaciones, 0.4f, maxPMut, 1.5f); // PROB MUTACIÓN
		        //ProbIndividuo[1]=Generados.RandomIntegerArray(Nmutaciones, 1f, maxCRep, 0.5f); // CAPACIDAD REPARTO bajar mucho
		        ProbIndividuo[2]=Generados.RandomIntegerArray(Nmutaciones, 1f, maxCAlim, 1f); // COSTE ALIMENTO
		        ProbIndividuo[3]=Generados.RandomIntegerArray(Nmutaciones, 0.2f, maxCMov, 2f); // CAPACIDAD MOVIMIENTO inhibición por contacto

		        /*float[][] matrix = {
		        		{0.77795243f, -0.10048897f, 0.21726914f, 0.94475794f, -0.5897757f, -0.47095782f, 0.430031f, 0.8286441f, -0.7517947f, -0.118429996f}, 
		        		{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f},
		        		{-0.88806415f, -0.9182074f, -0.18938588f, 0.4610952f, 0.34372267f, 0.7242994f, -1.6275799f, -0.3082422f, -0.35676995f, -0.5694895f}, 
		        		{0.72148937f, 0.5933748f, 0.25488228f, -0.2355796f, 0.982424f, -0.09785442f, 0.92323685f, -0.8414636f, 0.7770461f, 0.5372292f} 
            	};
		        ProbIndividuo=matrix;*/
		        
		        
		        ProbIndividuo=readMatrixFromFile(txt);
		        
		        
		        Generados GrupoBase= new Generados();
		        for(int x=0; x<fCantEInicial;x++) {
		        	String padre=java.util.Arrays.toString(IndBase[x])+x;
		        	GrupoBase.addGenerado(NPoblacion[x], IndBase[x], padre, ronda0, Alimento[x]);
		        }

		        System.out.println("\nPoblaciones:");
		        GrupoBase.imprimirDatosGenerados();
		        
		        Generados NuevoGrupo1=GrupoBase.clone().Generacion(ProbIndividuo, 1);
		        
		        int nrondas=fNiteraciones;
		        
		        String directorio=dir;
		        int frecCat=frec;
		        
		        
		     
		        GrupoBaseWorker worker = new GrupoBaseWorker(NuevoGrupo1,ProbIndividuo,nrondas,fCantEInicial,directorio, frecCat);
		        worker.execute();
		        
		        
		        //Funciones.GenerarGrafo(NuevoGrupo1);
		        /*Graph grafoFinal=new SingleGraph("Poblaciones");
				
				System.setProperty("org.graphstream.ui", "swing");
				
				for(int j=0;j<NuevoGrupo1.size();j++) {
			    	
					// Se almacena la cantidad de individuos para un valor de Y
						int cant=NuevoGrupo1.getCantID(j);
						short[] especie=NuevoGrupo1.getEspecieID(j);
						
						// Cada nodo tendrá el nombre de la especie y un número, para que no se repitan
						
						String padre=NuevoGrupo1.get(j).getPadre();
						//System.out.println("padre: "+padre);
						
						String especieString=java.util.Arrays.toString(especie)+j;
						//System.out.println("especie: "+especieString);
						
						
						grafoFinal.addNode(especieString).setAttribute("Cantidad", cant);
						
						
						if(padre!="No padre") {
							
							grafoFinal.addEdge(padre+especieString, padre, especieString);

						}
						

				}
				
				System.setProperty("org.graphstream.ui", "swing");
				Viewer viewer = grafoFinal.display();
				*/
		        
				for (int i = 0; i < ProbIndividuo.length; i++) {
		            // Iterar sobre las columnas de la matriz
		            for (int j = 0; j < ProbIndividuo[i].length; j++) {
		                // Imprimir el elemento en la posición i, j
		                System.out.print(ProbIndividuo[i][j] + " ");
		            }
		            // Imprimir un salto de línea después de imprimir cada fila
		            System.out.println();
		        }
				
		        
				/*for(int i=1;i<=nrondas;i++) {
		        	//System.out.println("gen i:"+i);
		        	NuevoGrupo1.Generacion(ProbIndividuo,i);
		        }*/
		        
		        
				
			}
		});
		
		JButton btnCancel = new JButton("Cancelar");
		panel_1.add(btnCancel);
		
		JPanel panel_2 = new JPanel();
		frame.getContentPane().add(panel_2, BorderLayout.WEST);
		
		JPanel panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(10);
		frame.getContentPane().add(panel_3, BorderLayout.EAST);
	}
	
	private float[][] readMatrixFromFile(String filename) {
        float[][] matrix = new float[4][];
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int row = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(" ");
                if (row < 4) {
                    matrix[row] = new float[values.length];  // assuming square matrix for simplicity
                
                    for (int col = 0; col < values.length; col++) {
                    	matrix[row][col] = Float.parseFloat(values[col]);
                    }
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matrix;
    }
		


	/*@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
        int column = e.getColumn();
        TableModel model = (TableModel)e.getSource();        
        Integer data = (Integer) model.getValueAt(row, column);
        System.out.println("Table changed row:"+row +" col: "+column+" new value: "+ data);
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Clicked on table with button "+e.getButton());
		JTable table = (JTable)e.getSource();
		//LPJ: podemos sacar la tabla con panel.table porque solo hay una tabla
		//LPJ: Para que incremente si es con el boton izq o decremente con el der.
		int inc = e.getButton() == MouseEvent.BUTTON1?1:-1;
		int row = table.rowAtPoint(e.getPoint());
        int col = table.columnAtPoint(e.getPoint());
		TableModel tableM = (TableModel)table.getModel();
		if(row >=0 && col >=0 ) {			
			tableM.setValueAt(Integer.toString(tableM.getValueAt(row, col)+inc), row, col);
		}
		
	}*/
	

	public void setVisible(boolean b) {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Variables window = new Variables(CantEInicial, Nmutaciones, Niteraciones, txt, direc, frecC);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
