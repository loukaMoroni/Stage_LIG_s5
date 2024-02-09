package interface_BDD_3B.cells;


public class PeriodCell extends Cell{


	public static final int Épisode =0;
	public static final int ÉvenementChangementDEmploi =1;
	private boolean criteriaIsEvent;


	private static final long serialVersionUID = 1L;


	public PeriodCell(boolean criteriaIsEvent /*vrai pour les events et faux pour les épisodesboolean*/) {
		super((criteriaIsEvent)?
				new String[] {"---","âge","année"}
				:new String[] {"---","Pèriode -- âge","Pèriode -- année", "Pèriode -- durée"}
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