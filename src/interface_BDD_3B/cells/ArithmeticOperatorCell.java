package interface_BDD_3B.cells;



import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ArithmeticOperatorCell extends Cell{

	protected JSpinner quantity =new JSpinner();
	int quantityValue=0;
	protected JTextField nomField;	
	protected String nom;
	protected BtnIsSpatial btnSpatial;
	boolean isSpatial;
	boolean isBeforeRank;





	private static final long serialVersionUID = 1L;

	public ArithmeticOperatorCell(){
		this(true);
	}

	public ArithmeticOperatorCell(boolean isBeforeRank){
		this(new String[] {"---","=", ">", "<", ">=", "<=", "=/="}, isBeforeRank);

	}
	protected ArithmeticOperatorCell(String[] items) {
		this(items, true);
	}

	protected ArithmeticOperatorCell(String[] items, boolean isBeforeRank) {
		super(items);

		quantity.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				quantityValue=(int) quantity.getValue();
			}
		});

		quantity.setValue(quantityValue);


		this.isBeforeRank=isBeforeRank;


		isSpatial=false;


		if(!isBeforeRank) {
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
	}


	private void addName(Boolean isSpatial) {
		if(isSpatial) {
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
		getParent().add(quantity);
		if (selected==0) {
		}else if (isBeforeRank) {

			Cell rankChild =new SelectIsRank("événement");
			rankChild.setRank(rank+1);
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
			return getSelectedItem()+" -> "+quantityValue+" ["+nom+"] ";
		}
		return getSelectedItem()+" -> "+quantityValue;
	}

}
