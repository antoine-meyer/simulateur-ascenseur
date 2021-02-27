public class EvenementOuverturePorteCabine extends Evenement {
    /*
     * OPC: Ouverture Porte Cabine L'instant précis ou la porte termine de s'ouvrir.
     */

    public EvenementOuverturePorteCabine(long d) {
        super(d);
    }

    public void afficheDetails(StringBuilder buffer, Immeuble immeuble) {
        buffer.append("OPC");
    }

    public void traiter(Immeuble immeuble, Echeancier echeancier) {
        Cabine cabine = immeuble.cabine;
        Etage étage = cabine.étage;
        assert !cabine.porteOuverte;

        //nombre d'entrants
        int entrants = 0;
        //nombre de sortants
        int sortants = 0;

        //on ouvre les portes de la cabine
        cabine.porteOuverte = true;

        //SI il y a des passagers dans la cabine
        if(!cabine.cabineVide()){           
            //SI des passagers veulent descendre
            if(cabine.passagersVeulentDescendre()){
                //on fait descendre les passagers qui sont arrivés à leur étage
                cabine.faireDescendrePassagers(immeuble, date);
            //SINON SI aucuns passagers ne veut descendre
            }else{
                notYetImplemented();
            }
        //SINON SI il n'y a pas de passagers
        }

        //SI il y a des gens qui attendent la cabine
        if(étage.aDesPassagers()){
            //SI on est en mode parfait
            if(isModeParfait()){
                //on parcourt les gens qui attendent
                int index = 0;
		        while (index < étage.getListPassager().size()) {
			        Passager p = étage.getListPassager().get(index);
                    //SI le gens va dans le sens opposé à la cabine
                    if( p.sens() != cabine.intention() ) {
                        //ET SI que la cabine descend OU ne bouge pas
                        if(cabine.intention() == 'v'){
                            //ET SI il n'y a personne en dessous
                            if(!immeuble.passagerEnDessous(étage)){
                                //alors on embarque les gens et on va vers le haut
                                cabine.faireMonterPassager(p);
                                entrants = entrants + 1;
                                cabine.changerIntention('^');
                            }else{
                                notYetImplemented();
                            }
                        }else{
                            notYetImplemented();
                        }
                    }else{
                        notYetImplemented();
                    }
                    index++;
		        }
            //SINON SI on est en mode infernal
            }else{
                //on récupère le 1er passager de la file 
                Passager pp = étage.getPremierPassager(cabine);
                //on fait monter les gens de cette étage dans la cabine
                cabine.faireMonterPassager(pp);
                //on augmente le nombre d'entrant
                entrants = entrants + 1;
                //on enlève le mec qui vient de monter dans la cabine de l'étage car il est dans la cabine maintenant
                étage.retirerPassager(pp);
                //on enlève l'évènement PAP dans l'échéancier
                echeancier.retirerPAP();
                //on regarde s'il y a des gens qui attendent dans la direction de la cabine
                //SI la cabine descend
                if(cabine.intention() == 'v'){
                    //si il y a des gens qui attendent en dessous
                    if(immeuble.passagerEnDessous(étage)){
                        //on continue de descendre
                        notYetImplemented();
                    //sinon on change de sens pour aller vers le haut
                    }else{
                        cabine.changerIntention('^');
                    }
                //SINON SI la cabine monte
                }else{
                    notYetImplemented();
                }      
            }
        //SINON SI il n'y a aucunes personnes qui attend
        }else{
            //on ne fait rien : notYetImplemented();
        }

        //après que tout le monde soit descendu et/ou monté : les portes doivent se refermer SAUF SI la cabine est vide
        if(cabine.cabineVide()){
            //on change l'intention de la cabine en '-'
            cabine.changerIntention('-');
        }else{
            echeancier.ajouter(new EvenementFermeturePorteCabine(date+((entrants+sortants)*tempsPourEntrerOuSortirDeLaCabine)+tempsPourOuvrirOuFermerLesPortes));
        }

        assert cabine.porteOuverte;
    }

}
