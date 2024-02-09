package interface_BDD_3B.cells;

public class CriteriaCell extends Cell{


	private static final long serialVersionUID = 1L;

	public CriteriaCell() {
		super(new String[] {
				"---",
				"Lieu -- naissance", "Lieu -- r�sidence", "Lieu -- travail",
				"�pisode -- familial","�pisode -- r�sidentiel","�pisode -- travail",
				"�v�nement -- premier logement", "�v�nement -- d�m�nagement", 
				"�v�nement -- premier emploi", 
				"�v�nement -- changement d'emploi","�v�nement -- �tude",
				"�v�nement -- mariage","�v�nement -- naissance", 
				"�v�nement -- naissance enfant","�v�nement -- d�c�s parent", 
				});	
	}

	protected void openNextCell(int selected,int rank) {

		if(selected==0) {
			
		}else if (selected<4) { // lieu
			Cell locationChild = new LocationOperatorCell();
			locationChild.setRank(rank);
			getParent().add(locationChild);
			
		}else if (selected<7) { //espisode
			Cell periodEpisodeChild = new PeriodCell(false);
			periodEpisodeChild.setRank(rank);
			getParent().add(periodEpisodeChild);
		}else { //evenement
			Cell periodEventChild = new PeriodCell(true);
			periodEventChild.setRank(rank);
			getParent().add(periodEventChild);
		}		

		
	}
	


}
