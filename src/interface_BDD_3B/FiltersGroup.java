package interface_BDD_3B;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class FiltersGroup extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int nbFilters;
	private JTextField nomField;	
	private String nom;
	private boolean isSpatial;

	private JPanel boutonsCreationDeRegles;
	private JPanel zoneDesFiltres;
	JComboBox<String> etEtpas;

	public FiltersGroup() {
		this(0);
	}


	public FiltersGroup(int CoucheGroup){
		setBorder(new EmptyBorder(10, 10, 10, 10));
		setLayout(new BorderLayout(0,0));


		boutonsCreationDeRegles = new JPanel();
		boutonsCreationDeRegles.setAlignmentY(Component.TOP_ALIGNMENT);
		boutonsCreationDeRegles.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(boutonsCreationDeRegles,BorderLayout.NORTH);
		boutonsCreationDeRegles.setLayout(new BoxLayout(boutonsCreationDeRegles, BoxLayout.X_AXIS));


		boutonsCreationDeRegles.addContainerListener(new ContainerAdapter() {
			@Override
			public void componentRemoved(ContainerEvent e) {
				boutonsCreationDeRegles.updateUI();
			}
			@Override
			public void componentAdded(ContainerEvent e) {
				boutonsCreationDeRegles.updateUI();
			}
		});

		JScrollPane ScrollPaneDesFiltres = new JScrollPane();
		add(ScrollPaneDesFiltres, BorderLayout.CENTER);

		zoneDesFiltres = new JPanel();
		zoneDesFiltres.addContainerListener(new ContainerAdapter() {
			@Override
			public void componentRemoved(ContainerEvent e) {
				zoneDesFiltres.updateUI();
			}
			@Override
			public void componentAdded(ContainerEvent e) {
				zoneDesFiltres.updateUI();
			}
		});
		zoneDesFiltres.setLayout(new BoxLayout(zoneDesFiltres, BoxLayout.Y_AXIS));
		Filter filter = new Filter(CoucheGroup+1);
		zoneDesFiltres.add(filter);		
		nbFilters=1;


		ScrollPaneDesFiltres.setViewportView(zoneDesFiltres);



		JButton btnsupr = new JButton("-");
		boutonsCreationDeRegles.add(btnsupr);	
		btnsupr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteThis();

			}
		});

		etEtpas= new JComboBox<String>((CoucheGroup%2==1)?new String[]{"---","Suffisant","Suffisant que non"}:new String[]{"---","Nécessaire","Nécessaire que non"});
		boutonsCreationDeRegles.add(etEtpas);




		JButton btnNewFilter = new JButton("add filter");
		btnNewFilter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nbFilters++;
				zoneDesFiltres.add("filtre"+nbFilters,new Filter(CoucheGroup+1));
			}
		});		
		boutonsCreationDeRegles.add(btnNewFilter);

		JButton btnNewFilterGroup = new JButton("add group");
		btnNewFilterGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoneDesFiltres.add(new FiltersGroup(CoucheGroup+1));
			}
		});
		boutonsCreationDeRegles.add(btnNewFilterGroup);
	}


	private void deleteThis() {
		getParent().remove(this);	
	}


	public String toString() {
		String s=etEtpas.getSelectedItem()+" -> (\r\n";
		Component[] cells = zoneDesFiltres.getComponents();
		for(int i=0; i<cells.length; i++) {
			s+=cells[i].toString()+"\r\n";
		}
		s+=")";

		if (isSpatial) {
			nom=nomField.getText();
			return s+" ["+nom+"] ";
		}else{
			return s;
		}


	}

}
