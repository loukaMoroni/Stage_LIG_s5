package interface_BDD_3B.cells;

public class CriteriaCell extends Cell{


	private static final long serialVersionUID = 1L;

	public CriteriaCell() {
		super(new String[] {
				"---",
				"Lieu -- naissance", "Lieu -- résidence", "Lieu -- travail",
				"Épisode -- familial","Épisode -- résidentiel","Épisode -- travail",
				"Évènement -- premier logement", "Évènement -- déménagement", 
				"Évènement -- premier emploi", 
				"Évènement -- changement d'emploi","Évènement -- étude",
				"Évènement -- mariage","Évènement -- naissance", 
				"Évènement -- naissance enfant","Évènement -- décés parent", 
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
