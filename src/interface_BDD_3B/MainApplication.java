package interface_BDD_3B;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class MainApplication extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel MainFrame;
	private JLabel lblInfoDisplay;
	private Interpreteur interpreteRegle;
	private FilterField prairieDesFiltre;
	private MapField pageCarte;
	private StatField pageRequette;
	private JTabbedPane tabbedPane;
	private JPanel Traduction;
	private JTextArea traductionText;
	private JTextArea entreeInstruction;
	private JButton BtnTraduction;
	private JPanel BtnTraduirePanel;
	private JLabel lblInstructions;
	private JLabel lblTraduction;
	private JScrollPane tablePane;
	private JTextArea tableDisplay;
	private JPanel panelInstruction;
	private JButton btnInstructions;



	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainApplication frame = new MainApplication();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainApplication(){

		setTitle("Interface 3B");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 1400, 500);
		MainFrame = new JPanel();
		MainFrame.setToolTipText("Interface 3B");
		MainFrame.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(MainFrame);
		MainFrame.setLayout(new BorderLayout(0, 0));




		JPanel barreDInformation = new JPanel();
		MainFrame.add(barreDInformation, BorderLayout.SOUTH);
		barreDInformation.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel lblInfoUtilisateur = new JLabel("Informations: ");
		barreDInformation.add(lblInfoUtilisateur);

		lblInfoDisplay = new JLabel("");
		lblInfoDisplay.setHorizontalAlignment(SwingConstants.LEFT);
		barreDInformation.add(lblInfoDisplay);


		interpreteRegle = new Interpreteur();




		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		MainFrame.add(tabbedPane,  BorderLayout.CENTER);



		prairieDesFiltre = new FilterField();
		tabbedPane.addTab("Filtres", null, prairieDesFiltre, "une zonne créer vos filtres");

		pageCarte = new MapField();
		tabbedPane.addTab("Carte", null, pageCarte, "une zonne placer vos règles spatiales");

		Traduction = new JPanel();
		
		
		tabbedPane.addTab("Traduction", null, Traduction, "une zonne pour traduire votre requète en SQL");
		Traduction.setLayout(new BoxLayout(Traduction, BoxLayout.Y_AXIS));
		
		panelInstruction = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panelInstruction.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		Traduction.add(panelInstruction);
		
		btnInstructions = new JButton("Instructions depuis \"Filtres\"");
		btnInstructions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				entreeInstruction.setText(prairieDesFiltre.toString());
			}
		});
		btnInstructions.setHorizontalAlignment(SwingConstants.LEFT);
		panelInstruction.add(btnInstructions);

		lblInstructions = new JLabel("Instructions");
		Traduction.add(lblInstructions);

		entreeInstruction = new JTextArea();
		Traduction.add(entreeInstruction);

		BtnTraduirePanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) BtnTraduirePanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		Traduction.add(BtnTraduirePanel);

		BtnTraduction = new JButton("Traduire et exécuter");
		BtnTraduction.setHorizontalAlignment(SwingConstants.LEFT);
		BtnTraduction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					traductionText.setText(interpreteRegle.traduit(entreeInstruction.getText()));
					interpreteRegle.executeQuery(traductionText.getText());
					tableDisplay.setText(interpreteRegle.toString());
					lblInfoDisplay.setText("");
				}catch(IllegalArgumentException exception) {
					lblInfoDisplay.setText(exception.getMessage());
				}
			}
		});
		BtnTraduirePanel.add(BtnTraduction);
		
				lblTraduction = new JLabel("Traduction en SQL");
				Traduction.add(lblTraduction);
				lblTraduction.setHorizontalAlignment(SwingConstants.CENTER);


		traductionText = new JTextArea();
		Traduction.add(traductionText);
		
		tablePane = new JScrollPane();
		tablePane.setToolTipText("sert \u00E0 visualiser la table filtr\u00E9e");
		tabbedPane.addTab("Table", null, tablePane, null);
		
		tableDisplay = new JTextArea();
		tablePane.setViewportView(tableDisplay);
		
				pageRequette = new StatField();
				tabbedPane.addTab("Stats", null, pageRequette, "zonne pour sélèctionner les statistiques que vous souhaitez obtenir");
		;

	}

}
