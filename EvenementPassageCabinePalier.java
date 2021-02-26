public class EvenementPassageCabinePalier extends Evenement {
    /*
     * PCP: Passage Cabine Palier L'instant précis où la cabine passe juste en face
     * d'un étage précis. Vous pouvez modifier cette classe comme vous voulez
     * (ajouter/modifier des méthodes etc.).
     */

    private Etage étage;

    public EvenementPassageCabinePalier(long d, Etage e) {
        super(d);
        étage = e;
    }

    public void afficheDetails(StringBuilder buffer, Immeuble immeuble) {
        buffer.append("PCP ");
        buffer.append(étage.numéro());
    }

    public void traiter(Immeuble immeuble, Echeancier echeancier) {
        Cabine cabine = immeuble.cabine;
        assert !cabine.porteOuverte;
        assert étage.numéro() != cabine.étage.numéro();
        //on change l'étage courant de la cabine pour l'étage passé en paramètre
        immeuble.cabine.étage = étage;
        //SI un ou des passagers veulent descendre
        if( cabine.passagersVeulentDescendre() ){
            //on arrête la cabine et on ouvre les portes
            echeancier.ajouter( new EvenementOuverturePorteCabine(date+tempsPourOuvrirOuFermerLesPortes) );
        //SINON SI personne ne veut descendre
        }else{
            //SI un ou des passagers attendent à l'étage
            if(étage.aDesPassagers()){
                //SI on est en mode parfait
                if(isModeParfait()){
                            /*System.out.println(étage.numéro());
                            //SI la cabine descend et que des gens veulent descendre
                            //OU QUE la cabine monte et que des gens veulent monter 
                            if( ( cabine.intention() == 'v' && étage.aDesPassagersQuiDescendent() ) || ( cabine.intention() == '^' && étage.aDesPassagersQuiMontent() ) ){
                                notYetImplemented();
                            }
                            //SINON SI la cabine descend et des gens veulent monter
                            else if(cabine.intention() == 'v' && étage.aDesPassagersQuiMontent() && cabine.passagersVeulentDescendre() ){
                                notYetImplemented();
                            }
                            */
                    notYetImplemented();
                }
                //SINON SI on est en mode infernal
                else{
                            //on attrape le passager qu'on veut déplacer
                            //Passager pass = étage.getPremierPassager(cabine);
                            //on fait monter le premier gens dans la cabine
                            //cabine.faireMonterPassager(pass);
                            //on l'enlève de l'étage
                            //étage.retirerPassager(pass);
                    //on ferme les portes de la cabine
                    echeancier.ajouter(new EvenementOuverturePorteCabine(date+tempsPourOuvrirOuFermerLesPortes));
                }
            //SINON SI personne ne veut monter
            }else{
                //SI on va vers le haut
                if(cabine.intention() == '^'){
                    //on monte la cabine d'un étage
                    étage = immeuble.étage( cabine.étage.numéro()+1 );
                //SINON SI on va vers le bas
                }else if(cabine.intention() == 'v'){
                    //on baisse la cabine d'un étage
                    étage = immeuble.étage( cabine.étage.numéro()-1 );
                //SINON SI la cabine ne bouge pas
                }else{
                    notYetImplemented();
                }
                //on change le temps
                date += tempsPourBougerLaCabineDUnEtage;
                //on fait évoluer la cabine dans l'échéancier
                echeancier.ajouter(this);
            }
        }
    }
}
