public class EvenementFermeturePorteCabine extends Evenement {
    /*
     * FPC: Fermeture Porte Cabine L'instant précis ou la porte termine de se
     * fermer. Tant que la porte n'est pas complètement fermée, il est possible pour
     * un passager de provoquer la réouverture de la porte. Dans ce cas le FPC est
     * décalé dans le temps selon la méthode decalerFPC qui est dans l'échéancier.
     */

    public EvenementFermeturePorteCabine(long d) {
        super(d);
    }

    public void afficheDetails(StringBuilder buffer, Immeuble immeuble) {
        buffer.append("FPC");
    }

    public void traiter(Immeuble immeuble, Echeancier echeancier) {
        Cabine cabine = immeuble.cabine;
        assert cabine.porteOuverte;
        //on ferme les portes de la cabine
        cabine.porteOuverte = false;

        //SI la cabine est vide 
        if(cabine.cabineVide()){
            //SI intention est -
            if(cabine.intention() == '-'){
                notYetImplemented();
            }
            //SINON intention est ^
            else if(cabine.intention() == '^'){
                //on monte la cabine d'un étage
                Etage nouveauEtage = immeuble.étage( cabine.étage.numéro()+1 );
                //on fait avancer la cabine
                echeancier.ajouter( new EvenementPassageCabinePalier(date+tempsPourBougerLaCabineDUnEtage, nouveauEtage) );
            }
            //SINON intention est v
            else if(cabine.intention() == 'v'){
                //on descend la cabine d'un étage
                Etage nouveauEtage = immeuble.étage( cabine.étage.numéro()-1 );
                //on fait avancer la cabine
                echeancier.ajouter( new EvenementPassageCabinePalier(date+tempsPourBougerLaCabineDUnEtage, nouveauEtage) );
            }
        //SINON SI la cabine a des gens
        }else{
            //SI la cabine va vers le haut ^
            if(cabine.intention() == '^'){
                //on monte la cabine d'un étage
                Etage nouveauEtage = immeuble.étage( cabine.étage.numéro()+1 );
                //on fait avancer la cabine
                echeancier.ajouter( new EvenementPassageCabinePalier(date+tempsPourBougerLaCabineDUnEtage, nouveauEtage) );
            //SINON SI la cabine va vers le bas v
            }else if(cabine.intention() == 'v'){
                //on descend la cabine d'un étage
                Etage nouveauEtage = immeuble.étage( cabine.étage.numéro()-1 );
                //on fait avancer la cabine
                echeancier.ajouter( new EvenementPassageCabinePalier(date+tempsPourBougerLaCabineDUnEtage, nouveauEtage) );
            //SINON SI elle ne bouge pas
            }else{
                notYetImplemented();
            }
        }
        assert !cabine.porteOuverte;
    }

    public void setDate(long d) {
        this.date = d;
    }

}
