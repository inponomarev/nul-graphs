package uk.ac.nulondon;

import uk.ac.nulondon.publications.Collaborations;
import uk.ac.nulondon.publications.Scientist;

public class GraphBuilder {

    public final static Scientist ERDOS = new Scientist("Paul Erdos");

    public static Collaborations getCollaborations() {
        Collaborations collaborations = new Collaborations();
        // Create scientists and mathematicians
        Scientist paulErdos = ERDOS;
        Scientist andrewWiles = new Scientist("Andrew Wiles");
        Scientist terenceTao = new Scientist("Terence Tao");
        Scientist grigoriPerelman = new Scientist("Grigori Perelman");
        Scientist maryCartwright = new Scientist("Mary Cartwright");
        Scientist richardFeynman = new Scientist("Richard Feynman");
        Scientist albertEinstein = new Scientist("Albert Einstein");
        Scientist johnNash = new Scientist("John Nash");
        Scientist julianSchwinger = new Scientist("Julian Schwinger");
        Scientist adaLovelace = new Scientist("Ada Lovelace"); // Separate node

        // Add scientists to collaborations
        collaborations.addScientist(paulErdos);
        collaborations.addScientist(andrewWiles);
        collaborations.addScientist(terenceTao);
        collaborations.addScientist(grigoriPerelman);
        collaborations.addScientist(maryCartwright);
        collaborations.addScientist(richardFeynman);
        collaborations.addScientist(albertEinstein);
        collaborations.addScientist(johnNash);
        collaborations.addScientist(julianSchwinger);
        collaborations.addCoauthor(richardFeynman, julianSchwinger);
        collaborations.addScientist(adaLovelace); // No collaborations will be added for Ada Lovelace

        // Define hypothetical collaborations (NB: these relations are hypothetical and not historically correct!!!)
        collaborations.addCoauthor(paulErdos, maryCartwright);
        collaborations.addCoauthor(paulErdos, johnNash);
        collaborations.addCoauthor(paulErdos, andrewWiles);
        collaborations.addCoauthor(johnNash, albertEinstein);
        collaborations.addCoauthor(richardFeynman, johnNash);
        collaborations.addCoauthor(andrewWiles, terenceTao);
        collaborations.addCoauthor(terenceTao, grigoriPerelman);
        collaborations.addCoauthor(maryCartwright, grigoriPerelman);
        collaborations.addCoauthor(albertEinstein, richardFeynman);
        collaborations.addCoauthor(johnNash, albertEinstein);
        collaborations.addCoauthor(johnNash, richardFeynman);
        return collaborations;
    }
}
