import java.util.List;

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
                sortants = cabine.faireDescendrePassagers(immeuble, date);
                

                //s'il y a des gens en dessous
                //et personne au dessus
                //et aucuns passagers de la cabine qui veut monter
                //alors on descend
                if(immeuble.passagerEnDessous(étage) && !immeuble.passagerAuDessus(étage) && !cabine.infernal_suivreLeSensActuel() ){
                    //on peut descendre
                    cabine.changerIntention('v');
                }
                //si on est a l'étage minimum alors on ne peut que monter
                if( cabine.étage.numéro() == immeuble.étageLePlusBas().numéro() ){
                    //on change l'intention
                    cabine.changerIntention('^');
                }
            //SINON SI aucuns passagers ne veut descendre
            }else{
                //rien de spécial
                //notYetImplemented();
            }
        //SINON SI il n'y a pas de passagers
        }else{
            //rien de spécial
            //notYetImplemented();
        }

        //SI il y a des gens qui attendent la cabine
        if(étage.aDesPassagers()){
            //SI on est en mode parfait
            if(isModeParfait()){
                //si on est à l'étage max alors on change intention pour 'v'
                if(étage==immeuble.étageLePlusHaut()){
                    cabine.changerIntention('v');
                }
                //on parcourt les gens qui attendent
                int index = 0;                              //le && sera autour de la méthode qui sera dans Etage !!
                while (index < étage.getListPassager().size() && !cabine.cabinePleine() ) {
                    //System.out.println("taille:"+étage.getListPassager().size());
                    Passager p = étage.getListPassager().get(index);
                    //SI le gens va dans le sens opposé à la cabine
                    if( p.sens() != cabine.intention() ) {
                        //ET SI que la cabine descend OU ne bouge pas
                        if(cabine.intention() == 'v'){
                            //ET SI il n'y a personne en dessous et que quelqu'un veut montrer et personne veut descendre
                            if(!immeuble.passagerEnDessous(étage) && étage.aDesPassagersQuiMontent() && !étage.aDesPassagersQuiDescendent()){
                                //alors on embarque les gens et on va vers le haut
                                //System.out.println("APS TOI MEC");
                                //si la cabine est vide
                                if(cabine.cabineVide()){
                                    cabine.changerIntention('^');
                                
                                    //System.out.println("APS TOI MEC 1-"+p.numéroDeCréation+"-"+p.sens());
                                    char res = cabine.faireMonterPassager(p);
                                    entrants = entrants + 1;
                                    //on enlève le passager de l'étage
                                    étage.retirerPassager(p);
                                    //on enlève le PAP de l'échéancier
                                    echeancier.retirerPAP(p);
                                }
                            //SINON
                            }else{
                                //si des gens descendent et quelquun veut descendre alors on le prend et pas les autres
                                if(étage.aDesPassagersQuiDescendent() && p.sens()=='v' ){
                                    char res = cabine.faireMonterPassager(p);
                                    entrants = entrants + 1;
                                    étage.retirerPassager(p);
                                    echeancier.retirerPAP(p);
                                }        
                            }
                        //SINON la cabine monte
                        }else if(cabine.intention() == '^'){
                            //on continue de monter
                            //on fait rien de spécial
                            //notYetImplemented();
                        }
                    //SINON SI les gens vont dans le même sens que la cabine
                    }else{
                        //System.out.println("APS TOI MEC 2-"+p.numéroDeCréation+"-"+p.sens());
                        entrants = entrants + étage.faireMonterPassagers(echeancier);
                    }
                    index++;
                }            
            //SINON SI on est en mode infernal
            }else{
                //tant qu'il y a de la place et des passagers on fait monter les gens
                while( (!cabine.cabinePleine()) && étage.aDesPassagers() ){
                    //on récupère le 1er passager de la file 
                    Passager pp = étage.infernal_getPremierPassager(cabine);
                    //on fait monter les gens de cette étage dans la cabine
                    cabine.faireMonterPassager(pp);
                    //on augmente le nombre d'entrant
                    entrants = entrants + 1;
                    //on enlève le mec qui vient de monter dans la cabine de l'étage car il est dans la cabine maintenant
                    étage.retirerPassager(pp);
                    //on enlève l'évènement PAP dans l'échéancier
                    echeancier.retirerPAP(pp);
                }
               
                //on regarde s'il y a des gens qui attendent dans la direction de la cabine
                //SI la cabine descend
                if(cabine.intention() == 'v'){
                    //si il y a des gens qui attendent en dessous
                    if(immeuble.passagerEnDessous(étage)){
                        //on continue de descendre
                        //notYetImplemented();

                    //sinon personne attend en dessous mais peut etre qu'un passager veut aller en bas
                    }else{
                        //on regarde si un passager veut suivre le sens
                        //System.out.println(cabine.infernal_suivreLeSensActuel());
                        if(cabine.infernal_suivreLeSensActuel()){
                            //on change rien
                            //notYetImplemented();
                        //si il veut changer de sens (donc aller vers ^) on change
                        }else{
                            //on change de sens pour aller vers le haut
                            cabine.changerIntention('^');
                        }
                    }
                //SINON SI la cabine monte
                }else if(cabine.intention() == '^'){
                    //s'il y a des gens qui attendent au dessus
                    if(immeuble.passagerAuDessus(étage)){
                        //on continue de monter
                        //notYetImplemented(); //camouflé
                    //sinon si personne n'attend au dessus mais peut etre qu'un passager veut aller en haut
                    }else{
                        if(cabine.infernal_suivreLeSensActuel()){
                            
                            //notYetImplemented();
                        //monte, personne au dessus et personne veut aller alors on change
                        }else{
                            cabine.changerIntention('v');
                            
                        }
                    }
                //SINON si la cabine est '-'
                }else{
                    notYetImplemented();
                }

            }
        //SINON SI il n'y a aucunes personnes qui attend
        }else{
            //on ne fait rien : notYetImplemented();
        }

        //après que tout le monde soit descendu et/ou monté : les portes doivent se refermer SAUF SI la cabine est vide ET qu'il y a personne en dessous ni au dessus
        if(cabine.cabineVide() && !immeuble.passagerAuDessus(étage) && !immeuble.passagerEnDessous(étage)){
            //on change l'intention de la cabine en '-'
            cabine.changerIntention('-');
        }else{
            //System.out.println("Entrants:"+entrants+";Sortants:"+sortants);
            echeancier.ajouter(new EvenementFermeturePorteCabine(date+((entrants+sortants)*tempsPourEntrerOuSortirDeLaCabine)+tempsPourOuvrirOuFermerLesPortes));
        }

        assert cabine.porteOuverte;
    }

}
