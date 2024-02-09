package interface_BDD_3B.cells;



public class BooleanCell extends Cell{


	private static final long serialVersionUID = 1L;

	
	public BooleanCell(int isInGroup) {
		super((isInGroup%2==1)?new String[]{"---","Suffisant","Suffisant que non"}:new String[]{"---","Nécessaire","Nécessaire que non"});
	}
	

	protected void openNextCell(int selected, int rank) {
		Cell CriteriaCell = new CriteriaCell();
		CriteriaCell.setRank(rank);
		getParent().add(CriteriaCell);
		
	}
	


}
