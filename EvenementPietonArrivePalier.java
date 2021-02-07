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
        notYetImplemented();
    }

    public EvenementPietonArrivePalier(long d, Etage edd, Passager pa) {
        super(d);
        étage = edd;
        passager = pa;
    }

}
