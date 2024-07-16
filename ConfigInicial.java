import java.awt.EventQueue;
import javax.swing.SwingWorker;

import javax.swing.JFrame;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Font;

public class ConfigInicial {

	private JFrame frame;
	private JFormattedTextField fNmutaciones;
	private JFormattedTextField fCantEInicial;
	private JFormattedTextField fNIteraciones;
	private JFormattedTextField fMatrixtxt;
	private JButton btnOkk;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigInicial window = new ConfigInicial();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	

	/**
	 * Create the application.
	 */
	public ConfigInicial() {
		initialize();
	}
	


	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 209, 370);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNmutaciones = new JLabel("Posibles Mutaciones (Nº entero)");
		lblNmutaciones.setBounds(10, 75, 175, 13);
		frame.getContentPane().add(lblNmutaciones);
		
		JLabel lblNespeciesIni = new JLabel("Especies Iniciales (Nº entero)");
		lblNespeciesIni.setBounds(10, 125, 175, 13);
		frame.getContentPane().add(lblNespeciesIni);
		
		fNmutaciones = new JFormattedTextField();
		fNmutaciones.setText("10");
		fNmutaciones.setBounds(10, 100, 175, 19);
		frame.getContentPane().add(fNmutaciones);
		
		fCantEInicial = new JFormattedTextField();
		fCantEInicial.setText("1");
		fCantEInicial.setBounds(10, 150, 175, 19);
		frame.getContentPane().add(fCantEInicial);
		
		fNIteraciones = new JFormattedTextField();
		fNIteraciones.setText("30");
		fNIteraciones.setBounds(10, 200, 175, 19);
		frame.getContentPane().add(fNIteraciones);
		
		fMatrixtxt = new JFormattedTextField();
		fMatrixtxt.setText("matrix.txt");
		fMatrixtxt.setBounds(10, 250, 175, 19);
		frame.getContentPane().add(fMatrixtxt);
		
		btnOkk = new JButton("OK");
		btnOkk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int nMut = Integer.parseInt(fNmutaciones.getText());
				int cEIni = Integer.parseInt(fCantEInicial.getText());
				int nIt = Integer.parseInt(fNIteraciones.getText());
				String txt= fMatrixtxt.getText();
				Variables newVariables = new Variables(cEIni, nMut, nIt, txt);
				//newVariables.setVisible(true);
			}
		});
		btnOkk.setBounds(59, 302, 85, 21);
		frame.getContentPane().add(btnOkk);
		
		JLabel lblNIteraciones = new JLabel("Cantidad Iteraciones (Nº entero)");
		lblNIteraciones.setBounds(10, 175, 175, 13);
		frame.getContentPane().add(lblNIteraciones);
		
		JLabel lblIntrdoduceLasVariables = new JLabel("Intrdoduce las variables de la ");
		lblIntrdoduceLasVariables.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblIntrdoduceLasVariables.setBounds(10, 12, 175, 18);
		frame.getContentPane().add(lblIntrdoduceLasVariables);
		
		JLabel lblNewLabel = new JLabel("simulación o modifica la tabla");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblNewLabel.setBounds(10, 32, 164, 13);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblDeProbabilidades = new JLabel("de probabilidades");
		lblDeProbabilidades.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblDeProbabilidades.setBounds(10, 49, 164, 13);
		frame.getContentPane().add(lblDeProbabilidades);
		
		JLabel lblTablaMut = new JLabel("Tabla Mutaciones (\"nombre.txt\")");
		lblTablaMut.setBounds(10, 225, 175, 13);
		frame.getContentPane().add(lblTablaMut);
		
		
	}
}
