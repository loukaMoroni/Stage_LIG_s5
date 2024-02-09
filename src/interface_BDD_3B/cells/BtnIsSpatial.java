package interface_BDD_3B.cells;

import javax.swing.JCheckBox;

public class BtnIsSpatial extends JCheckBox{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public BtnIsSpatial(){
		super("Spatial");
	}
	
	public String toString() {
		return "spatial";
	}
}
