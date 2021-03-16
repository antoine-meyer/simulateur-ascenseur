/*
 * PAP: Pieton Arrive Palier : L'instant précis où un passager qui a décidé de
 * continuer à pieds arrive sur un palier donné.
 */
public class EvenementPietonArrivePalier extends Evenement {

    private Etage étage;
    private Passager passager;

    public void afficheDetails(StringBuilder buffer, Immeuble immeuble) {
        buffer.append("PAP ");
        buffer.append(étage.numéro());
        buffer.append(" #");
        buffer.append(passager.numéroDeCréation);
    }

    public void traiter(Immeuble immeuble, Echeancier echeancier) {
        //System.out.println("EtageNum:"+étage.numéro()+";PassagerNumDepar:"+passager.numéroDepart()+";PassNumArriv:"+passager.numéroDestination());
        //si l'étage est égal à l'étage de départ du passager alors première fois
        if(étage.numéro()==passager.numéroDepart()){
            //on transforme le futur passager en pieton
            //on ajoute le passager à la liste des pietons de son étage
            étage.ajouterPieton(passager);
            //on supprime le nouveau pieton de la liste des futurs passagers
            étage.retirerPassager(passager);
            //on régénère l'évènement PAP forcément une fois
            //si le type veut monter
            if(passager.sens()=='^'){
                Etage nouveauEtage = immeuble.étage( passager.numéroDepart()+1 );
                echeancier.ajouter(new EvenementPietonArrivePalier(date+tempsPourMonterOuDescendreUnEtageAPieds, nouveauEtage, passager));
            }
            //si le type veut descendre
            if(passager.sens()=='v'){
                Etage nouveauEtage = immeuble.étage( passager.numéroDepart()-1 );
                echeancier.ajouter(new EvenementPietonArrivePalier(date+tempsPourMonterOuDescendreUnEtageAPieds, nouveauEtage, passager));
            }
        //sinon traitement de l'avancement
        }else{
            //on supprimer le piéton de l'étage précédent de piéton
            //si le type veut descendre
            if(passager.sens()=='v'){
                immeuble.étage(étage.numéro()+1).retirerPieton(passager);
            }
            //si le type veut monter
            if(passager.sens()=='^'){
                immeuble.étage(étage.numéro()-1).retirerPieton(passager);
            }

            //si on est à l'étage final
            if(étage.numéro()==passager.numéroDestination()){
                //on fait rien de spécial
                //notYetImplemented();
            //sinon on avance
            }else{
                //on régénère l'évènement PAP tant que nous ne sommes pas arrivés à l'étage de destination
            
                //réafficher pieton dans nouveau étage
                étage.ajouterPieton(passager);

                //gestion échéancier
                if(passager.sens()=='v'){
                    
                    Etage newEtage = immeuble.étage(étage.numéro()-1);
                    echeancier.ajouter(new EvenementPietonArrivePalier(date+tempsPourMonterOuDescendreUnEtageAPieds, newEtage, passager));
                    
                }
                if(passager.sens()=='^'){
                    Etage newEtage = immeuble.étage(étage.numéro()+1);
                    echeancier.ajouter(new EvenementPietonArrivePalier(date+tempsPourMonterOuDescendreUnEtageAPieds, newEtage, passager));
                }
                
            }
        }
    }

    public EvenementPietonArrivePalier(long d, Etage edd, Passager pa) {
        super(d);
        étage = edd;
        passager = pa;
    }

    public Passager getPassager(){
        return this.passager;
    }

}


//V1
    //ajoutes passagers à la liste des pietons (qui est dans etage)
    //du coup supprimer ce passager de la liste des passagers (dans etage aussi)
    //il faut regenerer l'evenement tant qu'il est pas arrive a etageDestination()
    //supprimer le pieton de la liste des pietons de l'etage precedent