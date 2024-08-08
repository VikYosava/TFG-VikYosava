package view;




import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;

// DEBERÍA SER EL MAINFRAME ENCARGADO DE LA VISTA

// HACER UN MAINCONTROLLER ENCARGADO DEL CONTROLADOR

public class MainFrame extends JFrame {

    private ConfigInicial configInicialPanel;
    private Variables variablesPanel;
	
	public MainFrame() {
        setTitle("Simulación");
        setBounds(100, 100, 800, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        configInicialPanel = new ConfigInicial();
        add(configInicialPanel, BorderLayout.CENTER);
    }
	
	private void showConfigInicialPanel() {
		getContentPane().removeAll();
        add(configInicialPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

	public void switchToVariablesPanel(int fCantEInicial, int fNmutaciones, int nIt, String mtxt, String dir, int frec) {
        getContentPane().removeAll();
        this.variablesPanel = new Variables(fCantEInicial, fNmutaciones, nIt, mtxt, dir, frec);
        add(variablesPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
	
	public void showVariablesPanel(Variables variablesPanel) {
        getContentPane().removeAll();
        this.variablesPanel = variablesPanel;
        add(variablesPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
	
	public ConfigInicial getConfigInicialPanel() {
		return configInicialPanel;
	}
	
	public Variables getVariablesPanel() {
		return variablesPanel;
	}

	public void switchToVariablesPanel(Variables variablesPanel2) {
		switchToVariablesPanel(variablesPanel2.getCantEInicial(), 
				variablesPanel2.getNMutaciones(), variablesPanel2.getNIteraciones(), 
				variablesPanel2.getMatrixtxt(), variablesPanel2.getDirect(),
				variablesPanel2.getCatastrof());
		
	}

	
}