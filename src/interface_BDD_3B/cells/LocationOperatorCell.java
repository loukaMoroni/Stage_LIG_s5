package interface_BDD_3B.cells;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class LocationOperatorCell extends Cell{


	JTextField nomField;	
	String nom;




	private static final long serialVersionUID = 1L;

	public LocationOperatorCell(){
		super(new String[] {
				"---", "contient", "ne contient pas"
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


	@Override
	protected void openNextCell(int selected, int rank) {		
		getParent().add(new JLabel(" nom de la règle: "));
		getParent().add(nomField);		
	}

	@Override
	public  String toString() {
		nom=nomField.getText();
		return getSelectedItem()+" ["+nom+"] ";
	}


}
