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
				//SINON SI intention cabine est 'v'
				}else if(c.intention() == 'v'){
					
					//si le mec veut aller vers le bas et qu'il reste de la place alors il monte
					if(p.sens() == 'v' && !c.cabinePleine()){
						//il monte
						notYetImplemented();
					//sinon s'il veut aller vers le haut alors on stock le type
					}else if(p.sens() == '^' ){
						//on stock le type
						étage.ajouter(p);
						//on ajoute un PAP
						echeancier.ajouter(new EvenementPietonArrivePalier(date+délaiDePatienceAvantSportif, étage, p));
					}

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
			//SI peu importe l'intention
			if(c.intention() == '-' || c.intention() == '^' || c.intention() == 'v'){
				//SI les portes sont fermées
				if(c.porteOuverte == false){
					//on ajoute le nouvel arrivant à l'étage
					étage.ajouter(p);
				//SINON SI les portes sont ouvertes
				}else{
					//on ajoute le nouvel arrivant à l'étage
					étage.ajouter(p);

					// on ferme les portes que si (cas ultra particulier au temps 89)
					if(c.intention() == '-'){
						echeancier.ajouter(new EvenementFermeturePorteCabine(date+tempsPourOuvrirOuFermerLesPortes));
					}

					//on change l'intention de la cabine en regardant
					//SI il y a des gens au dessus
					if(immeuble.passagerAuDessus(c.étage)){
						//RIEN
					}
					//SINON SI il y a des gens au dessous
					//et cabine vide (racollage moche mais qui marche)
					if(immeuble.passagerEnDessous(c.étage)&&c.cabineVide()){
						//on change l'intention de la cabine
						//A CHANGER CAR CHANGEMENT INTENTION QUE DANS OPC !!!!!
						//on est obligé à cause du pas 5
						c.changerIntention('v');
					}
							
				}
			}
		}

		//ajout d'un nouveau APP dans l'échéancier
		date += étage.arrivéeSuivante();
		echeancier.ajouter(this);
		assert c.intention() != '-' : "intention impossible";	
	}

}

