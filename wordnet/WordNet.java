import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class WordNet {
    /*
    WordNet class, takes in two files and creates a wordnet from the given files.
     */

    // hashmap of ids to synsets
    private HashMap<Integer, String> intToSyn = new HashMap<Integer, String>();
    // hashmap of synsets to their ids
    private HashMap<String, SET<Integer>> synToInt = new HashMap<String, SET<Integer>>();
    // dag
    private Digraph graph;
    // used to find ancestors of vertices and lengths from vertex to vertex
    private ShortestCommonAncestor shortest;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException("");

        int length = 0;

        In syn = new In(synsets);
        while (syn.hasNextLine()) {
            length++;
            String[] line = syn.readLine().split(",");
            int id = Integer.parseInt(line[0]);
            String[] words = line[1].split(" ");
            for (String word : words) {
                this.intToSyn.put(id, word);
                if (this.synToInt.containsKey(word)) {
                    this.synToInt.get(word).add(id);
                }
                else {
                    SET<Integer> ids = new SET<Integer>();
                    ids.add(id);
                    this.synToInt.put(word, ids);
                }
            }
        }
        syn.close();

        this.graph = new Digraph(length);

        In hyp = new In(hypernyms);
        while (hyp.hasNextLine()) {
            String[] line = hyp.readLine().split(",");
            int synset = Integer.parseInt(line[0]);
            for (int i = 1; i < line.length; i++) {
                int hypernym = Integer.parseInt(line[i]);
                this.graph.addEdge(synset, hypernym);
            }
        }
        hyp.close();

        this.shortest = new ShortestCommonAncestor(graph);
    }

    // the set of all WordNet nouns
    public Iterable<String> nouns() {
        return synToInt.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) throw new IllegalArgumentException("");

        return synToInt.containsKey(word);
    }

    // a synset (second field of synsets.txt) that is a shortest common ancestor
    // of noun1 and noun2 (defined below)
    public String sca(String noun1, String noun2) {
        if (noun1 == null || noun2 == null) throw new IllegalArgumentException("");
        if (!isNoun(noun1) || !isNoun(noun2)) throw new IllegalArgumentException("");

        SET<Integer> n1 = synToInt.get(noun1);
        SET<Integer> n2 = synToInt.get(noun2);

        int ancestorID = shortest.ancestorSubset(n1, n2);
        String ancestor = intToSyn.get(ancestorID);

        return ancestor;
    }

    // distance between noun1 and noun2 (defined below)
    public int distance(String noun1, String noun2) {
        if (noun1 == null || noun2 == null) throw new IllegalArgumentException("");
        if (!isNoun(noun1) || !isNoun(noun2)) throw new IllegalArgumentException("");

        SET<Integer> n1 = synToInt.get(noun1);
        SET<Integer> n2 = synToInt.get(noun2);

        return shortest.lengthSubset(n1, n2);
    }

    // unit testing (required)
    public static void main(String[] args) {
        WordNet wordnet = new WordNet("synsets11.txt", "hypernyms11AmbiguousAncestor.txt");
        String n1 = "a";
        String n2 = "d";
        StdOut.println(wordnet.nouns());
        StdOut.println(wordnet.isNoun(n1));
        StdOut.println(wordnet.sca(n1, n2));
        StdOut.println(wordnet.distance(n1, n2));
    }
}
