/*
 * APP: Arrivée Passager Palier L'instant précis où un nouveau passager arrive
 * sur un palier donné, dans le but de monter dans la cabine.
 */
public class EvenementArriveePassagerPalier extends Evenement {

	private Etage étage;

	public EvenementArriveePassagerPalier(long d, Etage edd) {
		super(d);
		étage = edd;
	}

	public void afficheDetails(StringBuilder buffer, Immeuble immeuble) {
		buffer.append("APP ");
		buffer.append(étage.numéro());
	}

	public void traiter(Immeuble immeuble, Echeancier echeancier) {
		assert étage != null;
		assert immeuble.étage(étage.numéro()) == étage;
		Passager p = new Passager(date, étage, immeuble);
		Cabine c = immeuble.cabine;
		
		//SI la cabine est là
		if(c.étage == étage){
			//SI les portes sont ouvertes
			if(c.porteOuverte){
				//SI intention -
				if(c.intention() == '-'){
					//on regarde s'il reste de la place
					char place = c.faireMonterPassager(p);
					//SI y a de la place 0
					if(place == '0'){
						notYetImplemented();
					//SINON SI intention différente I	
					}else if(place == 'I'){
						//on change l'intention de la cabine
						c.changerIntention(p.sens());
						//on ajoute evenement FPC
						echeancier.ajouter(new EvenementFermeturePorteCabine(date+tempsPourOuvrirOuFermerLesPortes));
						//SI mode parfait
						if(isModeParfait()){
							//on ajoute le passager à la cabine
							c.faireMonterPassager(p);
						//SINON SI mode infernal 
						}else{
							//rien de spécial
						}
					//SINON SI plein P
					}else{
						notYetImplemented();
					}
				//SINON SI intention v
				}else if(c.intention() == 'v'){
					notYetImplemented();
				//SINON SI intention ^
				}else{
					notYetImplemented();
				}
			//SINON SI les portes sont fermées
			}else{
				//on ajoute le nouvel arrivant à l'étage
				étage.ajouter(p);
				//on ajoute un PAP
				echeancier.ajouter(new EvenementPietonArrivePalier(date+délaiDePatienceAvantSportif, étage, p));
			}
		//SINON SI la cabine n'est pas là
		}else{
			//on ajoute un PAP
			echeancier.ajouter(new EvenementPietonArrivePalier(date+délaiDePatienceAvantSportif, étage, p));
			//SI intention -
			if(c.intention() == '-'){
				//SI les portes sont fermées
				if(c.porteOuverte == false){
					notYetImplemented();
				//SINON SI les portes sont ouvertes
				}else{
					//on ajoute le nouvel arrivant à l'étage
					étage.ajouter(p);
					//on change l'intention de la cabine en regardant
					//SI il y a des gens au dessus
					if(immeuble.passagerAuDessus(c.étage)){
						notYetImplemented();
					}
					//SINON SI il y a des gens au dessous
					else if(immeuble.passagerEnDessous(c.étage)){
						//on change l'intention de la cabine
						c.changerIntention('v');
					}
					//SINON SI il y a personnes nulles part
					else{
						notYetImplemented();
					}
					// on ferme les portes
					echeancier.ajouter(new EvenementFermeturePorteCabine(date+tempsPourOuvrirOuFermerLesPortes));
				}
			}
			//SINON intention ^
			else if(c.intention() == '^'){
				notYetImplemented();
			}
			//SINON intention v
			else if(c.intention() == 'v'){
				notYetImplemented();
			}
		}

		//ajout d'un nouveau APP dans l'échéancier
		date += étage.arrivéeSuivante();
		echeancier.ajouter(this);
		assert c.intention() != '-' : "intention impossible";	
	}

}





















//si la cabine a les portes ouvertes
		//et si la cabine est à l'étage où il y a notre bonhomme
		/*if (c.porteOuverte && c.étage == étage) {
			//si l'intention de cabine c'est : pas bougé alors
				//CAS QUE POUR LE TOUT PREMIER TOUR CAR APPRES ON AURA TOUJOURS : v ou ^ !!!!
			if (c.intention() == '-') {
				
				//PROB 1 - RAJOUTER UN IF POUR VERIFIER QUIL Y A ENCORE DE LA PLACE POUR UN NOUVEAU
				//PROB 2 - SI UN MEC SE GLISSE ON AURA 2 EVENEMENTS FPC
					//PROB 3 - SI UN MEC SE GLISSE CEST PAS SON AVIS QUI COMPTE POUR LA DIRECTION DE LA CABINE
				
				//on va dans le sens que veut le passager
				c.changerIntention(p.sens());
				
				//ON PEUT AVOIR UN PROBLEME ICI CAR SI UN MEC SE GLISSE ALORS ON AURAIT UN 2EME
				//EVENEMENT FERME PORTE
				//pour eviter cela on parcours lecheancier, on trouve le FPC, on lenleve et on met
				//un nouveau a la bonne date
				
				//on ajoute a l'écheancier l'evenement de fermer les portes de la cabine 
				echeancier.ajouter(new EvenementFermeturePorteCabine(date + tempsPourOuvrirOuFermerLesPortes));
				//on ajoute le passager a la cabine
				char fmp = c.faireMonterPassager(p);
				
				//si fmp vaut 0 alors OK donc le mec est dans la cabine
					//si fmp vaut I alors le passager et la cabine ne vont pas dans le meme sens
						//(intention) ET on ne met pas le passager dans la cabine (en mode parfait !!!)
					//si fmp vaut P alors la cabine est pleine

	// Faudrait aussi ajouter le premier PCP... : pas sur...cest FPC qui va faire ca
				if (fmp == 'O') {
					assert true;
					//si on est ici alors OK donc on ajoute l'evenement que la cabine va avancer
					//mais que quand les portes seront fermees
					notYetImplemented();	
				} else {
					assert false : "else impossible";
					notYetImplemented();
				}


			} else {
				notYetImplemented();
			}
		} else {
			notYetImplemented();
		}*/


		//AJOUTER DANS LECHEANCIER UN NOUVEAU APP
			
		//1 :
			//echeancier.ajouter(new EvenementArriveePassagerPalier(date + étage.arrivéeSuivante(), étage));
		//2 : idee : recycler l'object pour eviter le new et ainsi gagne du temps 
		/*
		date += étage.arrivéeSuivante();
		echeancier.ajouter(this);

		assert c.intention() != '-' : "intention impossible";
		*/