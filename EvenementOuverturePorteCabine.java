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




        notYetImplemented();


        //les portes viennent de finir de s'ouvrir
            //les gens qui doivent descendre descende
            //si des gens doivent monter, ils montent
            //les portes doivent se refermer après que tout le monde soit monter puis descendu



        assert cabine.porteOuverte;
    }

}
