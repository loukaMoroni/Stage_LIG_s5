package interface_BDD_3B.cells;


import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class IntervalOperatorCell extends ArithmeticOperatorCell{



	//the inherited quantity is equivalent to the start value
	JSpinner end =new JSpinner();
	private boolean valueIsInterval;
	private int endValue=0;



	private static final long serialVersionUID = 1L;




	protected IntervalOperatorCell (){
		super(new String[]{
				"---", "fini avant", "commence aprés", "commence à", 
				"fini à", "commence avant", "fini aprés", "est", 
				"chevauche", "contient", "est chevauché", 
				"est contenu par", "préfixe", "est préfixé par", 
				"suffixe","est suffixé par"}
		);


		//on update pour voir si la case sélèctionée à besoin d'un deuxième spinner
		addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				valueIsInterval=getSelectedIndex()>6;
			}
		});



		end.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				endValue=(int) end.getValue();
			}
		});

		end.setValue(endValue);

	}


	@Override
	protected void openNextCell(int selected, int rank) {
		Container parent=getParent();	

		parent.add(quantity);
		if (valueIsInterval) {
			parent.add(new JLabel(" - "));
			parent.add(end);
		}


		if (selected==0) {
		}else if (isBeforeRank) {

			Cell rankChild =new SelectIsRank("épisode");
			if(valueIsInterval) {
				rankChild.setRank(rank+3);
			}else {
				rankChild.setRank(rank+1);
			}
			getParent().add(rankChild);
		}else {
			getParent().add(quantity);
			getParent().add(btnSpatial);
		}
	}

	@Override
	public  String toString() {
		if (isSpatial) {
			nom=nomField.getText();
			if(valueIsInterval) {
				return getSelectedItem()+" -> "+quantityValue+":"+endValue+" ["+nom+"] ";
			}else {
				return getSelectedItem()+" -> "+quantityValue+" ["+nom+"] ";
			}
		}
		if(valueIsInterval) {
			return getSelectedItem()+" -> "+quantityValue+":"+endValue;
		}else {
			return getSelectedItem()+" -> "+quantityValue+" ";
		}
	}

}
