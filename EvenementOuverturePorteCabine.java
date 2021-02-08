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

        //on ouvre les portes de la cabine
        cabine.porteOuverte = true;

        //SI il y a des passagers
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
        }else{
            notYetImplemented();
        }

        //SI il y a des gens qui attendent la cabine
        if(étage.aDesPassagers()){
            notYetImplemented();

            //SI les gens qui attendent vont dans le même sens que la cabine 
                //SI on est en mode parfait 
                    //ALORS ils montent
                //SINON SI on est en mode infernal
                    //ALORS ils montent
                //FSI
            //SINON SI les gens attendent mais veulent aller a l'opposé
                //SI on est en mode parfait
                    //ALORS on fait rien
                //SINON SI on est en mode infernal
                    //ALORS ils montent
                //FSI
            //FSI

        //SINON SI il n'y a aucunes personnes qui attend
        }else{
            //on ne fait rien : notYetImplemented();
        }

        //après que tout le monde soit descendu et/ou monté : les portes doivent se refermer
        echeancier.ajouter(new EvenementFermeturePorteCabine(date+tempsPourOuvrirOuFermerLesPortes));

        assert cabine.porteOuverte;
    }

}
