package view;

import java.awt.EventQueue;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Component;

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

public class Variables extends JPanel{

	private JPanel panelPrincipal, panel, panel_1, panel_2, panel_3;
	private JFrame frame;
	private JTable tableMutaciones, tablaCantidades;
	private JScrollPane scrollPane, scrollPane2;
	private JScrollBar verticalScrollBar1, verticalScrollBar2;
	private JLabel lblIndBase;
	private JButton btnOk, btnCancel;
	private FlowLayout flowLayout;
	private JSplitPane splitPane;
	
	int Nmutaciones, CantEInicial, Niteraciones, frecC;
	String txt, direc;
	
	private DefaultTableModel model, model2;

	public Variables(int fCantEInicial, int fNmutaciones, int nIt, String mtxt, String dir, int frec) {
		this.Nmutaciones=fNmutaciones;
		this.CantEInicial=fCantEInicial;
		this.Niteraciones=nIt;
		this.txt=mtxt;
		this.direc=dir;
		this.frecC=frec;
		initialize(CantEInicial, Nmutaciones, Niteraciones, txt, direc, frecC);
	}

	private void initialize(int fCantEInicial, int fNmutaciones, int fNiteraciones, String txt, String dir, int frec) {
		setLayout(new BorderLayout(0, 0));
		
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
        
        String[] columnNames2 = {"CI", "CA"};
        
		model=new DefaultTableModel(data, columnNames);
		tableMutaciones = new JTable(model);
		tableMutaciones.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		int columnWidth = 20; // set your desired fixed width here
        for (int i = 0; i < tableMutaciones.getColumnCount(); i++) {
            TableColumn column = tableMutaciones.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidth);
        }
        
        // CONTROLLER
		/*tableMutaciones.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int inc = e.getButton() == MouseEvent.BUTTON1?1:-1;

				int row = tableMutaciones.rowAtPoint(e.getPoint());
				int col = tableMutaciones.columnAtPoint(e.getPoint());
				if(row >=0 && col >=0 ) {		
					
					model.setValueAt(Short.parseShort(model.getValueAt(row, col).toString())+inc,row,col);
				}
			
			
			}
		});*/
		
		scrollPane=new JScrollPane(tableMutaciones);
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
        
        scrollPane2=new JScrollPane(tablaCantidades);
        scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        splitPane=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane, scrollPane2);        
        add(splitPane, BorderLayout.CENTER);
        splitPane.setDividerLocation(600);
        verticalScrollBar1 = scrollPane.getVerticalScrollBar();
        verticalScrollBar2 = scrollPane2.getVerticalScrollBar();
        verticalScrollBar1.addAdjustmentListener(e -> verticalScrollBar2.setValue(e.getValue()));
        verticalScrollBar2.addAdjustmentListener(e -> verticalScrollBar1.setValue(e.getValue()));        		
		
		panel = new JPanel();
		add(panel, BorderLayout.NORTH);
		lblIndBase = new JLabel("Código genético de los Individuos (En Binario)");
		panel.add(lblIndBase);
		
		panel_1 = new JPanel();
		add(panel_1, BorderLayout.SOUTH);

		btnOk = new JButton("Ok");
		panel_1.add(btnOk);
		/*btnOk.addActionListener(new ActionListener() {
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
		        
		        int ronda0 = 0;
		        
		        float[][] ProbIndividuo= new float[4][fNmutaciones];

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

		        
				for (int i = 0; i < ProbIndividuo.length; i++) {
		            // Iterar sobre las columnas de la matriz
		            for (int j = 0; j < ProbIndividuo[i].length; j++) {
		                // Imprimir el elemento en la posición i, j
		                System.out.print(ProbIndividuo[i][j] + " ");
		            }
		            // Imprimir un salto de línea después de imprimir cada fila
		            System.out.println();
		        }
				
			}
		});*/
		
		btnCancel = new JButton("Cancelar");
		panel_1.add(btnCancel);
		
		panel_2 = new JPanel();
		add(panel_2, BorderLayout.WEST);
		
		panel_3 = new JPanel();
		flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setVgap(10);
		flowLayout.setHgap(10);
		add(panel_3, BorderLayout.EAST);
	}
	
	/*private float[][] readMatrixFromFile(String filename) {
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
    }*/
		


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
	public JButton getBtnOk() {
        return btnOk;
    }
	
	public JButton getBtnCancel() {
		return btnCancel;
	}

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

	public int getCantEInicial() {
		// TODO Auto-generated method stub
		return CantEInicial;
	}

	public int getNMutaciones() {
		// TODO Auto-generated method stub
		return Nmutaciones;
	}

	public int getNIteraciones() {
		// TODO Auto-generated method stub
		return Niteraciones;
	}

	public String getMatrixtxt() {
		// TODO Auto-generated method stub
		return txt;
	}

	public String getDirect() {
		// TODO Auto-generated method stub
		return direc;
	}

	public int getCatastrof() {
		// TODO Auto-generated method stub
		return frecC;
	}

	public DefaultTableModel getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	public DefaultTableModel getModel2() {
		// TODO Auto-generated method stub
		return model2;
	}

	public Component getTableMutaciones() {
		// TODO Auto-generated method stub
		return tableMutaciones;
	}
	
	public int getRow(MouseEvent e) {
		return tableMutaciones.rowAtPoint(e.getPoint());
	}
	
	public int getColumn(MouseEvent e) {
		return tableMutaciones.columnAtPoint(e.getPoint());
	}

}
