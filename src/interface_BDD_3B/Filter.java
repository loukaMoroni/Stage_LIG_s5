package interface_BDD_3B;


import java.awt.event.*;
import javax.swing.*;

import interface_BDD_3B.cells.*;

import java.awt.Component;
import java.awt.FlowLayout;

//a lign of cell that filters
public class Filter extends JPanel{

	private static final long serialVersionUID = 1L;
	JPanel ruleContainer;
	private int nbOfCell;



	public Filter() {
		this(0);
	}
	public Filter(int isInGroup) {




		ruleContainer = new JPanel();
		ruleContainer.setAlignmentY(Component.TOP_ALIGNMENT);
		ruleContainer.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(ruleContainer);


		setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JButton btnsupr = new JButton("-");
		ruleContainer.add(btnsupr);	
		btnsupr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteThis();

			}
		});



		Cell cell1Bool = new BooleanCell(isInGroup);
		ruleContainer.add(cell1Bool);		
		nbOfCell=1;
		cell1Bool.setRank(nbOfCell);

		ruleContainer.addContainerListener(new ContainerAdapter() {
			@Override
			public void componentAdded(ContainerEvent e) {
				ruleContainer.updateUI();
				nbOfCell++;
			}
		});

		ruleContainer.addContainerListener(new ContainerAdapter() {
			public void componentRemoved(ContainerEvent e) {
				ruleContainer.updateUI();
				nbOfCell--;
			}
		});




	}

	public boolean isSpatial() {
		Component[] cells = ruleContainer.getComponents();
		String s = cells[cells.length-1].toString();
		if(s.charAt(s.length()-1)==']') {
			return true;
		}
		System.out.println(s.substring(s.length()-2, s.length()));
		return false;
	}


	public String toString() {
		String s="";
		Component[] cells = ruleContainer.getComponents();
		for(int i=1; i<cells.length; i++) {
			if (cells[i] instanceof Cell) {
				s+=cells[i].toString()+" -> ";
			}
		}
		return s.substring(0, s.length()-3);		
	}



	private void deleteThis() {
		getParent().remove(this);	
	}


}
