package interface_BDD_3B.cells;


public class PeriodCell extends Cell{


	public static final int �pisode =0;
	public static final int �venementChangementDEmploi =1;
	private boolean criteriaIsEvent;


	private static final long serialVersionUID = 1L;


	public PeriodCell(boolean criteriaIsEvent /*vrai pour les events et faux pour les �pisodesboolean*/) {
		super((criteriaIsEvent)?
				new String[] {"---","�ge","ann�e"}
				:new String[] {"---","P�riode -- �ge","P�riode -- ann�e", "P�riode -- dur�e"}
		);
		this.criteriaIsEvent=criteriaIsEvent;
	}

	protected void openNextCell(int selected,int rank) {

		if (selected==0) {
		}else {	
			Cell setOperatorChild = (criteriaIsEvent)?new ArithmeticOperatorCell():new IntervalOperatorCell();
			setOperatorChild.setRank(rank);
			getParent().add(setOperatorChild);}
	}
}