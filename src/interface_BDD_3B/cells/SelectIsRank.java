package interface_BDD_3B.cells;



import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SelectIsRank extends Cell{

	protected JSpinner quantity =new JSpinner();
	int quantityValue=0;
	protected JTextField nomField;	
	protected String nom;
	protected BtnIsSpatial btnSpatial;
	boolean isSpatial;





	private static final long serialVersionUID = 1L;


	public SelectIsRank(String s){
		this(new String[] {"---","et pas de rang particulier","et le rang de l'"+s+" est"});
	}

	protected SelectIsRank(String[] items) {
		super(items);



		quantity.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				quantityValue=(int) quantity.getValue();
			}
		});

		quantity.setValue(quantityValue);



		isSpatial=false;
		btnSpatial = new BtnIsSpatial();
		btnSpatial.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				isSpatial=btnSpatial.isSelected();
				addName(isSpatial);
			}
		});

		nomField=new JTextField();
		nomField.setColumns(10);
		nom="";
		nomField.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				nom=nomField.getText();			
			}
		});


	}


	private void addName(Boolean v) {
		if(v) {
			getParent().add(new JLabel(" nom de la règle: "));
			getParent().add(nomField);
		}else {
			Component[] cells = getParent().getComponents();
			getParent().remove(cells[cells.length-1]);
			getParent().remove(cells[cells.length-2]);

		}
	}

	@Override
	protected void openNextCell(int selected, int rank) {			
		if (selected==1) {

			
			getParent().add(btnSpatial);

		}else if (selected==2) {

			Cell mathChild = new ArithmeticOperatorCell(false);
			mathChild.setRank(rank);

			getParent().add(mathChild);
			getParent().add(quantity);
			getParent().add(btnSpatial);
		}


	}

	@Override
	public  String toString() {

		if (isSpatial) {
			nom=nomField.getText();
			return getSelectedItem()+" -> "+quantityValue+" ["+nom+"] ";
		}
		return getSelectedItem()+" -> "+quantityValue;
	}

}
