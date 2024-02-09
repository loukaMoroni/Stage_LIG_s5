package interface_BDD_3B;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class FilterField extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int nbFilters;
	private JPanel zoneDesFiltres;
	

	public FilterField(){
		
		setAlignmentY(Component.TOP_ALIGNMENT);
		setLayout(new BorderLayout(0,0));
		
		nbFilters=0;
		
		JPanel boutonsCreationDeRegles = new JPanel();
		boutonsCreationDeRegles.setAlignmentY(Component.TOP_ALIGNMENT);
		boutonsCreationDeRegles.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(boutonsCreationDeRegles,BorderLayout.NORTH);
		boutonsCreationDeRegles.setLayout(new BoxLayout(boutonsCreationDeRegles, BoxLayout.X_AXIS));
		
		
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
		Filter filter = new Filter();
		zoneDesFiltres.add(filter);
		
		
		
		
		
		

		nbFilters++;
		ScrollPaneDesFiltres.setViewportView(zoneDesFiltres);



		
		
		
		JButton btnNewFilter = new JButton("ajouter un filtre");
		btnNewFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	nbFilters++;
            	zoneDesFiltres.add("filtre"+nbFilters,new Filter());
            	}
        });		
		boutonsCreationDeRegles.add(btnNewFilter);
		
		JButton btnNewFilterGroup = new JButton("ajouter un groupe");
		btnNewFilterGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				zoneDesFiltres.add(new FiltersGroup());
			}
		});
		boutonsCreationDeRegles.add(btnNewFilterGroup);

		
	}
	
	public String toString() {
		String s="";
		Component[] cells = zoneDesFiltres.getComponents();
		for(int i=0; i<cells.length; i++) {
			s+=cells[i].toString()+"\r\n";

		}
		
		return s;		
	}

}
