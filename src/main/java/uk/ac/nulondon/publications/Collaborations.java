package uk.ac.nulondon.publications;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class Collaborations {

    Map<Scientist, Set<Scientist>> coAuthors;

    public Collaborations() {
        coAuthors = new HashMap<>();
    }

    public void addScientist(Scientist s) {
        coAuthors.put(s, new HashSet<>());
    }

    public void addCoauthor(Scientist s, Scientist coAuthor) {
        coAuthors.get(s).add(coAuthor);
        coAuthors.get(coAuthor).add(s);
    }

    public Set<Scientist> getCoauthors(Scientist p) {
        return coAuthors.getOrDefault(p, Collections.emptySet());
    }

    public boolean areCoauthors(Scientist p1, Scientist p2) {
        return getCoauthors(p1).contains(p2);
    }


    public HashMap<Scientist, Integer> coauthorsNumbers() {
        HashMap<Scientist, Integer> result = new HashMap<>();
        for (Scientist scientist : coAuthors.keySet()) {
            result.put(scientist, coAuthors.get(scientist).size());
        }
        return result;
    }

    public List<ScientistPair> shouldCollaborate() {
        List<ScientistPair> result = new ArrayList<>();
        for (Scientist s1 : coAuthors.keySet()) {
            for (Scientist s2 : coAuthors.keySet()) {
                if (!(s1.equals(s2))
                        && !getCoauthors(s1).contains(s2)
                        && haveCommonCoauthors(s1, s2)) {
                    result.add(new ScientistPair(s1, s2));
                }
            }
        }
        return result;
    }

    private boolean haveCommonCoauthors(Scientist s1, Scientist s2) {
        Set<Scientist> coauthors1 = getCoauthors(s1);
        Set<Scientist> coauthors2 = getCoauthors(s2);
        for (Scientist coauthor : coauthors1) {
            if (coauthors2.contains(coauthor)) {
                return true;
            }
        }
        return false;
    }

    public HashMap<Scientist, Integer> erdosNumbers(Scientist root) {
        HashMap<Scientist, Integer> result = new HashMap<>();
        Deque<Scientist> queue = new ArrayDeque<>();
        queue.addLast(root);
        result.put(root, 0);
        while (!queue.isEmpty()) {
            Scientist s = queue.pollFirst();
            int distance = result.get(s) + 1;
            for (Scientist coauthor : getCoauthors(s))
                if (!result.containsKey(coauthor)) {
                    result.put(coauthor, distance);
                    queue.addLast(coauthor);
                }
        }
        return result;
    }

    public String visualize() {
        return visualize(Collections.emptyMap());
    }

    public String visualize(Map<Scientist, Integer> numbers) {
        Set<String> visited = new HashSet<>();
        try (StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw)) {
            pw.println("graph G {");
            Map<Scientist, String> nodeNames = new HashMap<>();
            int i = 1;
            for (Scientist vertex : coAuthors.keySet()) {
                String nodeId = "N" + i++;
                nodeNames.put(vertex, nodeId);
                if (numbers.containsKey(vertex)) {
                    pw.printf("  %s[label=\"%s\\n%d\"]%n", nodeId, vertex.name(), numbers.get(vertex));
                } else {
                    pw.printf("  %s[label=\"%s\"]%n", nodeId, vertex.name());
                }
            }
            for (Scientist vertex : coAuthors.keySet()) {
                String startId = nodeNames.get(vertex);
                for (Scientist neighbour : coAuthors.get(vertex)) {
                    String endId = nodeNames.get(neighbour);
                    String edge = String.format("  %s--%s;", startId, endId);
                    String reversed = String.format("  %s--%s;", endId, startId);
                    if (!visited.contains(reversed)) {
                        visited.add(edge);
                        pw.println(edge);
                    }
                }
            }
            pw.println("}");
            pw.flush();
            return sw.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
