package interface_BDD_3B.cells;

import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;



/**
 * This is a class that all the cells in a rule will extends,it defines them as 
 * a ComboBox and forces them to have the OpenNextCell method
 */
public abstract class Cell extends JComboBox<String> {

	// must be set by the container, supposed to start at 1
	private int rank;
	

	public void setRank(int i) {
		rank=i;
	}
	public int getRank() {
		return rank;
	}


	private static final long serialVersionUID = 1L;

	public Cell(String[] items) {
		super(items);
	
		
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			

				Component[] cells = getParent().getComponents();
				for (int i=rank+1;i<cells.length; i++) {
					getParent().remove(cells[i]);							
				}
				
				if (getSelectedIndex()>0) {
					openNextCell(getSelectedIndex(),rank+1);
				}
			}
		});
	}
	
	protected abstract void openNextCell(int selected, int rank) ;

	@Override
	public  String toString() {
		return (String)getSelectedItem();
	}
}