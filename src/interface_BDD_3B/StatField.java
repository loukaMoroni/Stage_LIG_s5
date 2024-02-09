package interface_BDD_3B;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;


public class StatField extends JScrollPane{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int nbDeLignes;
	int nbDeColonnes;
	JPanel main;
	JPanel[][] panelHolder;

	public StatField() {
		nbDeLignes=2;
		nbDeColonnes=2;
		main=new JPanel();
		setViewportView(main);
		panelHolder = new JPanel[nbDeLignes][nbDeColonnes];    
		main.setLayout(new GridLayout(nbDeLignes,nbDeColonnes,0,0));

		for(int m = 0; m < nbDeLignes; m++) {
			for(int n = 0; n < nbDeColonnes; n++) {
				panelHolder[m][n] = new JPanel();
				panelHolder[m][n].setBorder(new LineBorder(new Color(0, 0, 0)));
				main.add(panelHolder[m][n]);
			}
		}

		panelHolder[0][0].add( new JLabel ("taille de l'échantillon"));	   
		panelHolder[1][0].add( new RowSelectorCell());
		panelHolder[0][1].add( new ColumnSelectorCell());
		

	}



	public class ColumnSelectorCell extends JComboBox<String>{

		private static final long serialVersionUID = 1L;
		boolean firstTimeOppened;

		public ColumnSelectorCell() {
			super(new String[] {
					"---",
					"nombre de lieux de travail", "nombre de lieux d'habitation",
					"nombre d'épisodes familiaux","nombre d'épisodes résidentiels",
					"nombre d'épisodes de travails","nombre d'années de travail", 
					"nombres d'années d'études ", "nombre de mariages", 
					"nombre d'anées de mariage","nombre d'enfants",
					"nombre de parents vivants", "âge au moment de l'étude",
					"Lieu de naissance","premier logement", "premier emploi"
			});


			firstTimeOppened = true;

			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
					if (firstTimeOppened&&getSelectedIndex()>0) {

						nbDeColonnes++;
						JPanel[][] panelHolderTemp = new JPanel[nbDeLignes][nbDeColonnes];
						main.removeAll();
						main.setLayout(new GridLayout(nbDeLignes,nbDeColonnes,0,0));

						for(int row=0; row<nbDeLignes; row++) {
							for(int column=0; column<nbDeColonnes; column++) {
								if(column<nbDeColonnes-1) {							
									panelHolderTemp[row][column]=panelHolder[row][column];
								}else {
									panelHolderTemp[row][column]= new JPanel();
									panelHolderTemp[row][column].setBorder(new LineBorder(new Color(0, 0, 0)));
								}
								main.add(panelHolderTemp[row][column]);								
							}							
						}
						
						panelHolderTemp[0][nbDeColonnes-1].add(new ColumnSelectorCell());
						panelHolder=panelHolderTemp;
						firstTimeOppened=false;
					}
				}
			});
		}

	}

	public class RowSelectorCell extends JComboBox<String>{

		private static final long serialVersionUID = 1L;
		boolean firstTimeOppened;

		public RowSelectorCell() {
			super(new String[] {
					"---","moyenne","médiane","quartile","mode","étendue",
					"écart type","intervalle de confiance",
					"proportion dans la population","proportion dans l'échantillon"
			});


			firstTimeOppened =true;

			addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {	
					if (firstTimeOppened&&getSelectedIndex()>0) {

						nbDeLignes++;
						JPanel[][] panelHolderTemp = new JPanel[nbDeLignes][nbDeColonnes];
						main.removeAll();
						main.setLayout(new GridLayout(nbDeLignes,nbDeColonnes,0,0));

						for(int row=0; row<nbDeLignes; row++) {
							for(int column=0; column<nbDeColonnes; column++) {
								if(row<nbDeLignes-1) {							
									panelHolderTemp[row][column]=panelHolder[row][column];
								}else {
									panelHolderTemp[row][column]= new JPanel();
									panelHolderTemp[row][column].setBorder(new LineBorder(new Color(0, 0, 0)));
								}
								main.add(panelHolderTemp[row][column]);								
							}							
						}
						
						panelHolderTemp[nbDeLignes-1][0].add(new RowSelectorCell());
						panelHolder=panelHolderTemp;
						firstTimeOppened=false;
					}
				}
			});
		}

	}

}



