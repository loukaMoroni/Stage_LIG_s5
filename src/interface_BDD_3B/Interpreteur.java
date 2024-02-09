package interface_BDD_3B;

import java.sql.*;


public class Interpreteur {
	protected Statement stmt;
	protected ResultSet res;
	protected Connection conn;
	protected DatabaseMetaData metadata;
	protected String tableNames;
	private String from;
	private int numLigne;




	public Interpreteur() {
		try {
			Class.forName("org.postgresql.Driver");


			String url = "jdbc:postgresql://localhost:5432/TER_db_38";
			String user = "postgres";
			String passwd = "postgres";

			conn = DriverManager.getConnection(url, user, passwd);

			stmt = conn.createStatement();
			metadata=conn.getMetaData();


			ResultSet rs = metadata.getTables(null, null, "%", null);
			tableNames="";
			while (rs.next()) {
				String s=rs.getString(3);
				if(s.substring(s.length()-5,s.length()).equals("_pkey")) {
					tableNames+=s.substring(0,s.length()-5)+", ";
				}else break;
			}
			tableNames=tableNames.substring(0, tableNames.length()-2);

		} catch (Exception e) {
			e.printStackTrace();
		}
		from= "FROM personne";
	}


	/**
	 * Method which translate descriptive String(parametter) of a filter in a SQL Query (return)
	 */
	public String traduit(String filterDescription) throws IllegalArgumentException{

		String rest=filterDescription;
		String select="SELECT DISTINCT pk_personne_id";
		from="\r\nFROM personne";
		String where="\r\nWHERE ";
		String order="\r\nORDER by pk_personne_id";

		numLigne=0;
		boolean isFirst=true;
		int i=0;
		while(rest.length()>2) {
			i=0;

			i=nextLineIdx(0,rest);

			//on évalue la prochaine ligne de l'instruction
			int endIdxGroup=detectGroup(i,rest);
			if(endIdxGroup>=0) {
				//si c'est un groupe on traduit le groupe sélèctioné
				where+=traduitGroupe(rest.substring(0, endIdxGroup), isFirst);
				rest=rest.substring(endIdxGroup);
			}else {				
				//si ce n'est pas un groupe on traduit la regle
				where+=traduitFiltre(rest.substring(0, i), isFirst);
				rest=rest.substring(i);
			}
			isFirst=false;
		}


		return select+" "+from+" "+where+" "+order;
	}



	private String traduitFiltre(String regleDescription, boolean isFirst) throws IllegalArgumentException{

		String rest= regleDescription;
		String retour = "";


		int i=0;
		int j;

		//TODO traiter le cas spatial
		while (i<rest.length()) {
			j=i;

			i=nextTokenIdx(i, rest);
			/*on évalue le token courent : le token qui commence à l'indice du token
			 * courent et fini avant la flèche du prochain token
			 *TODO les code parse mal (retour sert à rien actuellement, et il manque de la structure
			 */


			switch (rest.substring(j,i-4)){
			case "Nécessaire" : 
				retour+=(isFirst)?"(":"AND (";
				break;
			case "Nécessaire que non" : 
				retour+=(isFirst)?"(":"AND NOT(";
				break;
			case "Suffisant" : 
				retour+=(isFirst)?"(":"OR (";
				break;
			case "Suffisant que non" : 
				retour+=(isFirst)?"(":"OR NOT(";
				break;				
			case "---":
				throw new IllegalArgumentException("une des regles n'est pas finalisée");			
			case "Lieu -- naissance":
				//TODO
				break;
			case "Lieu -- résidence":
				//TODO
				break;
			case "Lieu -- travail":
				//TODO
				break;
			case "Épisode -- familial":				
				retour+=composeClauseEpisode(rest.substring(i),"familial_episode");
				i=rest.length();
				break;
			case "Épisode -- résidentiel":
				retour+=composeClauseEpisode(rest.substring(i),"residential_episode");
				i=rest.length();
				break;
			case "Épisode -- travail":
				retour+=composeClauseEpisode(rest.substring(i),"professionnal_episode");
				i=rest.length();
				break;
			case "Évènement -- premier logement":
				retour+=composeClauseEvent(rest.substring(i),"residential_event","premier logement");
				i=rest.length();
				break;
			case "Évènement -- déménagement":
				retour+=composeClauseEvent(rest.substring(i),"residential_event","demenagement");
				i=rest.length();
				break;
			case "Évènement -- premier emploi":
				retour+=composeClauseEvent(rest.substring(i),"professionnal_event","premier emploi");
				i=rest.length();
				break;
			case "Évènement -- changement d'emploi":
				retour+=composeClauseEvent(rest.substring(i),"professionnal_event","changement emploi");
				i=rest.length();
				break;
			case "Évènement -- étude":
				retour+=composeClauseEvent(rest.substring(i),"professionnal_event","Etude");
				i=rest.length();
				break;
			case "Évènement -- mariage":
				retour+=composeClauseEvent(rest.substring(i),"familial_event","Mariage");
				i=rest.length();
				break;
			case"Évènement -- naissance":
				retour+=composeClauseEvent(rest.substring(i),"familial_event","Naissance ego");
				i=rest.length();
				break;
			case "Évènement -- naissance enfant":
				retour+=composeClauseEvent(rest.substring(i),"familial_event","Naissance enfant");
				i=rest.length();
				break;
			case "Évènement -- décés parent":
				retour+=composeClauseEvent(rest.substring(i),"familial_event","Décès parent");
				i=rest.length();
				break;
			default:
				throw new IllegalArgumentException("la règle contient des éléments non valide: \""
						+ rest.substring(j,i-3)+"\" entre les caractères numéro "
						+j+" et "+i+" de la ligne "+numLigne);
			}

		}


			return retour+')';


	}



	private String traduitGroupe(String groupDescription, boolean isFirst) throws IllegalArgumentException{
		String rest= groupDescription;
		String retour = "";


		int idxCourant=0;


		idxCourant=nextTokenIdx(idxCourant, rest);
		switch (rest.substring(0,idxCourant-4)){
		case "Nécessaire" : 
			retour+=(isFirst)?"(":"AND (";
			break;
		case "Nécessaire que non" : 
			retour+=(isFirst)?"(":"AND NOT(";
			break;
		case "Suffisant" : 
			retour+=(isFirst)?"(":"OR (";
			break;
		case "Suffisant que non" : 
			retour+=(isFirst)?"(":"OR NOT(";
			break;
		default:
			throw new IllegalArgumentException("la règle contient des éléments non valide: à la ligne "+numLigne);

		}

		idxCourant=nextLineIdx(idxCourant,rest);
		rest=rest.substring(idxCourant);

		boolean first=true;		
		while(rest.length()>3) {
			//on cherche la fin de la ligne
			idxCourant=nextLineIdx(0,rest);
			//si c'est un groupe,	
			int endIdxGroup=detectGroup(idxCourant,rest);
			if(endIdxGroup>=0) {
				//on traduit le groupe sélèctioné
				retour+=traduitGroupe(rest.substring(0, endIdxGroup), first);
				rest=rest.substring(endIdxGroup);
			}else {				
				//si ce n'est pas un groupe on traduit la regle
				retour+=traduitFiltre(rest.substring(0, idxCourant), first);	
				rest=rest.substring(idxCourant);
			}
			first=false;
		}

			return retour+')';

	}


	private int nextTokenIdx(int currentIdx, String s) {

		while(currentIdx+1<s.length()&& !(s.charAt(currentIdx)=='-'&& s.charAt(currentIdx+1)=='>')) {
			currentIdx++;
		}
		//aprés avoir dectecté la position de la flèche, on se place aprés l'espace d'aprés la fleche
		return currentIdx+3;

	}


	private int nextLineIdx(int currentIdx, String s) {

		while(currentIdx+2<s.length()&&s.charAt(currentIdx)!='\r'&&s.charAt(currentIdx+1)!='\n') {
			currentIdx++;
		}

		numLigne++;
		if(currentIdx+2<s.length()) {
			//System.out.println("@interprete line@260, currentIdx: "+(currentIdx+2)+" , rest:      "+s+"        substring from currentIdx : "+s.substring(currentIdx+2));
			return currentIdx+2;
		}else {
			//System.out.println("end of string interprete @247");
			return currentIdx;
		}

	}


	private int detectGroup(int currentIdx, String rest) {	

		if (currentIdx>1&&rest.charAt(currentIdx-3)=='(') {
			int parenthesisCounter=1;
			//on cherche la fin du groupe
			for(;currentIdx<rest.length()&&parenthesisCounter!=0;currentIdx++) {
				if (rest.charAt(currentIdx)==')') {
					parenthesisCounter--;
				}else if (rest.charAt(currentIdx)=='(') {
					parenthesisCounter++;
				}
			}
			return currentIdx;
		}else {
			return -1;
		}
	}

	//TODO faire 
	private String composeClauseEpisode(String rest,String table) {

		String retour="";


		if (!from.contains(" JOIN "+table)) {
			from+=" JOIN "+table+" ON "+table+".fk_personne_id=personne.pk_personne_id";
		}


		//représente l'indice de fin du token courent
		int endIdx=nextTokenIdx(0, rest);
		//le prochain token étant une pèriode on la traduit p
		String periodeId=rest.substring(0,endIdx-4);

		//on passe au token suivant
		rest=rest.substring(endIdx);
		endIdx=nextTokenIdx(0, rest);
		//le prochain token étant un opérateur on le traduit 
		String operateurId=rest.substring(0,endIdx-4);

		//on passe au token suivant qui est le début de l'intervale
		rest=rest.substring(endIdx);
		endIdx=0;
		int value[]=new int[2];
		while(endIdx<rest.length()&&rest.charAt(endIdx)!=':'&&rest.charAt(endIdx)!=' '&&rest.charAt(endIdx)!='\r') {
			endIdx++;
		}
		value[0]=Integer.parseInt((rest.substring(0,endIdx)));
		int startIdx=endIdx;
		//on évalue le deuxième nombre
		while(endIdx<rest.length()&&rest.charAt(endIdx)!=' '&&rest.charAt(endIdx)!='\r') {
			endIdx++;
		}
		//si il n'y a pas de deuxième nombre les deux indexe sont égaux
		value[1]=(endIdx==startIdx)?value[0]:Integer.parseInt((rest.substring(startIdx+1,endIdx)));




		String dateDebut ="cast(CASE WHEN "+table+".date_debut != \'NA\' THEN "+table+".date_debut END as int)";	
		String dateFin="cast(CASE WHEN "+table+".date_fin != \'NA\' THEN "+table+".date_fin END as int)";

		//un tableau qui formalise la pèriode avec Idx 0 = début et Idx 1 = fin
		String[] periode= new String[2];

		switch (periodeId){

		case "Pèriode -- âge":
			String anneeNaissance= "cast(CASE WHEN personne.annee_naissance != \'NA\' THEN personne.annee_naissance END as int)";

			periode= new String[] {"("+dateDebut+"-"+anneeNaissance+")","("+dateFin+"-"+anneeNaissance+")"};
			break;
		case "Pèriode -- année":
			periode= new String[] {dateDebut,dateFin};
			break;
		case "Pèriode -- durée":

			periode= new String[] {"("+dateDebut+"-"+dateFin+")","("+dateDebut+"-"+dateFin+")"};
			break;
		default:
			throw new IllegalArgumentException("le filtre contient des éléments non valide: à la ligne "+numLigne);
			}



		switch(operateurId) {
		/*
		 * les opréareteurs suivent les relations de allen
		 * Allen, J. F. (1983). Maintaining knowledge about temporal intervals. 
		 * Communications of The ACM, 26(11), 832-843. 
		 * https://doi.org/10.1145/182.358434
		 * 
		 */


		/*les 4 premiers opérateur n'on besoin que d'une valeur, on utilise donc 
		 * value[0] même si l'opérateur ne référence par forcément le début de 
		 * l'intervalle
		 */

		//<
		case "fini avant":
			retour+="("+periode[1]+"<="+value[0]+")";
			break;
			//>
		case "commence aprés":
			retour+="("+periode[0]+">="+value[0]+")";
			break;
			//m
		case "commence à":
			retour+="("+periode[0]+"="+value[0]+")";
			break;
			//mi
		case "fini à":
			retour+="("+periode[1]+"="+value[0]+")";
			break;
			/*les deux ci dessous ne sont pas des opérateurs des relations de allen, 
			 * mais ils complètent le set qui permet d'en construire l'intégralité 
			 * avec des conjonction
			 */
		case "commence avant":
			retour+="("+periode[0]+"<="+value[0]+")";
			break;
		case "fini aprés":
			retour+="("+periode[1]+">="+value[0]+")";
			break;


			//commence avant le début de l'intervalle et fini avant la fin
			//o
		case "chevauche":
			retour+="(("+periode[0]+"<="+value[0]+")AND("+periode[1]+">="+value[0]+
			")AND("+periode[1]+"<="+value[1]+"))";
			break;
			//oi
		case "est chevauché":
			retour+="(("+periode[0]+">="+value[0]+")AND("+periode[0]+"<="+value[1]+
			")AND("+periode[1]+">="+value[1]+"))";
			break;
			//=
		case "est":
			retour+="(("+periode[0]+"="+value[0]+")AND("+periode[1]+"="+value[1]+"))";
			break;
			//d
		case "contient":
			retour+="(("+periode[0]+"<="+value[0]+")AND("+periode[1]+">="+value[1]+"))";
			break;
			//di
		case "est contenu par":
			retour+="(("+periode[0]+">="+value[0]+")AND("+periode[1]+"<="+value[1]+"))";
			break;
			//s
		case "préfixe":
			retour+="(("+periode[0]+"="+value[0]+")AND("+periode[1]+"<="+value[1]+"))";
			break;
			//si
		case "est préfixé par":
			retour+="(("+periode[0]+"="+value[0]+")AND("+periode[1]+">="+value[1]+"))";
			break;
			//f
		case "suffixe":
			retour+="(("+periode[0]+">="+value[0]+")AND("+periode[1]+"="+value[1]+"))";
			break;
			//fi
		case "est suffixé par":
			retour+="(("+periode[0]+"<="+value[0]+")AND("+periode[1]+"="+value[1]+"))";
			break;

		default:
			throw new IllegalArgumentException("la règle contient des éléments non valide: à la ligne "+numLigne);
		}


		return retour;
	}



	private String composeClauseEvent(String rest,String table,String typeEvent) {


		String retour=table+".type_event=\'"+typeEvent+"\' AND ";

		if (!from.contains(", "+table)) {
			from+=", "+table;
			retour+=table+".fk_personne_id=pk_personne_id AND ";
		}


		//représente l'indice de fin du token courant
		int endIdx=nextTokenIdx(0, rest);
		//le prochain token étant une pèriode on la traduit p
		String periodeId=rest.substring(0,endIdx-4);

		//on passe au token suivant
		rest=rest.substring(endIdx);
		endIdx=nextTokenIdx(0, rest);
		//le prochain token étant un opérateur on le traduit 
		String operateurId=rest.substring(0,endIdx-4);

		//on passe au token suivant qui est la valeur avec laquelle on compare
		rest=rest.substring(endIdx);
		endIdx=0;
		int value;
		while(endIdx<rest.length()&&rest.charAt(endIdx)!=' '&&rest.charAt(endIdx)!='\r') {
			endIdx++;
		}

		value=Integer.parseInt((rest.substring(0,endIdx)));





		String moment;

		String annee ="cast(CASE WHEN "+table+".annee != \'NA\' THEN "+table+".annee END as int)";
		String anneeNaissance= "cast(CASE WHEN personne.annee_naissance != \'NA\' THEN personne.annee_naissance END as int)";


		switch (periodeId){

		case "âge":
			moment= "("+annee+"-"+anneeNaissance+")";
			break;
		case "année":
			moment= annee;
			break;
		default:
			throw new IllegalArgumentException("la règle contient des éléments non valide: à la ligne "+numLigne);
			}

		switch(operateurId) {

		case "<":
			retour+="("+moment+"<"+value+")";
			break;
		case ">":
			retour+="("+moment+">"+value+")";
			break;
		case "=/=":
			retour+="("+moment+"<>"+value+")";
			break;
		case "<=":
			retour+="("+moment+"<="+value+")";
			break;
		case ">=":
			retour+="("+moment+">="+value+")";
			break;
		case "=":
			retour+="("+moment+"="+value+")";
			break;

		default:
			throw new IllegalArgumentException("la règle contient des éléments non valide: à la ligne "+numLigne);
		}
		
		// TODO retour+=addRank


		return retour;
	}



	public void executeQuery(String s) {
		try {
			res = stmt.executeQuery(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	public void close(){
		try {
			conn.close();	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public String toString() {
		String r="";
		try {

			if (res==null||res.getFetchSize()==0) {
				return "il n'y a pas de personnes dans la base de données correspondant à votre requète";
			}
			while(res.next()) {
				try {
					for(int i=1; ; i++) {
						r+=res.getString(i)+"  ";
					}
				}catch (java.sql.SQLException e) {}
				r+=" \r\n";


			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}


}
