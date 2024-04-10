package uk.ac.nulondon;

import uk.ac.nulondon.publications.Collaborations;
import uk.ac.nulondon.publications.Scientist;
import uk.ac.nulondon.publications.ScientistPair;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;

public final class App {
    private App() {
    }

    public static void main(String[] args) throws IOException, URISyntaxException {
        Collaborations c = GraphBuilder.getCollaborations();

        HashMap<Scientist, Integer> numbers = c.erdosNumbers(GraphBuilder.ERDOS);


        String dot = c.visualize(numbers);


        show(dot);

        //for (ScientistPair scientistPair : c.shouldCollaborate()) {
        //    System.out.println(scientistPair);
        // }
    }

    private static void show(String dot) throws IOException, URISyntaxException {
        String encoded = URLEncoder.encode(dot, "UTF8")
                .replaceAll("\\+", "%20");
        Desktop.getDesktop().browse(
                new URI("https://dreampuf.github.io/GraphvizOnline/#"
                        + encoded));
    }
}
