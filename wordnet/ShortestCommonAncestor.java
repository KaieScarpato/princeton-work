import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;

public class ShortestCommonAncestor {
    /*
    Finds shortest common ancestor and length between vertices in a dag.
     */

    private Digraph graph;    // dag

    // constructor takes a rooted DAG as argument
    public ShortestCommonAncestor(Digraph G) {
        if (!isDAG(G)) throw new IllegalArgumentException("");

        this.graph = G;
    }

    // length of shortest ancestral path between v and w
    public int length(int v, int w) {
        if (!isValid(v) || !isValid(w)) throw new IllegalArgumentException("");

        HashMap<Integer, Integer> vHash = bfs(v);
        HashMap<Integer, Integer> wHash = bfs(w);

        int x = ancestor(v, w);

        return vHash.get(x) + wHash.get(x);
    }

    // a shortest common ancestor of vertices v and w
    public int ancestor(int v, int w) {
        if (!isValid(v) || !isValid(w)) throw new IllegalArgumentException("");

        HashMap<Integer, Integer> vHash = bfs(v);
        HashMap<Integer, Integer> wHash = bfs(w);

        int ancestor = -1;
        int length = Integer.MAX_VALUE;

        for (int i : vHash.keySet()) {
            if (wHash.containsKey(i) && vHash.get(i) + wHash.get(i) < length) {
                length = vHash.get(i) + wHash.get(i);
                ancestor = i;
            }
        }

        return ancestor;
    }

    // length of shortest ancestral path of vertex subsets A and B
    public int lengthSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        int counterA = 0;
        for (Integer i : subsetA) {
            counterA++;
            if (i == null || !isValid(i)) throw new IllegalArgumentException("");
        }

        int counterB = 0;
        for (Integer i : subsetB) {
            counterB++;
            if (i == null || !isValid(i)) throw new IllegalArgumentException("");
        }

        if (counterA == 0 || counterB == 0) throw new IllegalArgumentException("");

        HashMap<Integer, Integer> reachableA = reachable(subsetA);
        HashMap<Integer, Integer> reachableB = reachable(subsetB);

        int ancestor = ancestorSubset(subsetA, subsetB);

        return reachableA.get(ancestor) + reachableB.get(ancestor);
    }

    // a shortest common ancestor of vertex subsets A and B
    public int ancestorSubset(Iterable<Integer> subsetA, Iterable<Integer> subsetB) {
        int counterA = 0;
        for (Integer i : subsetA) {
            counterA++;
            if (i == null || !isValid(i)) throw new IllegalArgumentException("");
        }

        int counterB = 0;
        for (Integer i : subsetB) {
            counterB++;
            if (i == null || !isValid(i)) throw new IllegalArgumentException("");
        }

        if (counterA == 0 || counterB == 0) throw new IllegalArgumentException("");

        int ancestor = -1;
        int length = Integer.MAX_VALUE;

        HashMap<Integer, Integer> reachableA = reachable(subsetA);
        HashMap<Integer, Integer> reachableB = reachable(subsetB);

        for (int i : reachableA.keySet()) {
            if (reachableB.containsKey(i) && reachableB.get(i) + reachableA.get(i) < length) {
                length = reachableB.get(i) + reachableA.get(i);
                ancestor = i;
            }
        }

        return ancestor;
    }

    // bfs, returns a hashmap with key = vertex and value = length from v to vertex
    private HashMap<Integer, Integer> bfs(int v) {
        HashMap<Integer, Integer> bfsHash = new HashMap<Integer, Integer>();
        Queue<Integer> bfsQueue = new Queue<Integer>();

        int length = 0;

        bfsQueue.enqueue(v);

        while (!bfsQueue.isEmpty()) {
            SET<Integer> vertices = new SET<Integer>();

            while (!bfsQueue.isEmpty()) {
                int i = bfsQueue.dequeue();

                if (!bfsHash.containsKey(i)) {
                    bfsHash.put(i, length);
                }
                Iterable<Integer> adj = graph.adj(i);

                for (int n : adj) {
                    if (!vertices.contains(n)) {
                        vertices.add(n);
                    }
                }
            }
            for (int vertex : vertices) {
                bfsQueue.enqueue(vertex);
            }
            length++;
        }

        return bfsHash;
    }

    // returns all reachable vertices from a subset of vertices in the digraph
    private HashMap<Integer, Integer> reachable(Iterable<Integer> subset) {
        HashMap<Integer, Integer> bfsHash = new HashMap<Integer, Integer>();
        Queue<Integer> bfsQueue = new Queue<Integer>();

        int length = 0;

        for (int s : subset) {
            bfsQueue.enqueue(s);
        }

        while (!bfsQueue.isEmpty()) {
            SET<Integer> vertices = new SET<Integer>();

            while (!bfsQueue.isEmpty()) {
                int i = bfsQueue.dequeue();

                if (!bfsHash.containsKey(i)) {
                    bfsHash.put(i, length);
                }
                Iterable<Integer> adj = graph.adj(i);

                for (int n : adj) {
                    if (!vertices.contains(n)) {
                        vertices.add(n);
                    }
                }
            }
            for (int vertex : vertices) {
                bfsQueue.enqueue(vertex);
            }
            length++;
        }

        return bfsHash;
    }

    private int time = 0; // time vertex v is finished in dfs

    // dfs to help find cycle in graph
    private void DFS(Digraph G, int v, boolean[] visited, int[] timeFinished) {
        visited[v] = true;

        time++;
        for (int u : G.adj(v)) {
            if (!visited[u]) {
                DFS(G, u, visited, timeFinished);
            }
        }

        time++;
        timeFinished[v] = time;
    }

    // returns true if given directed graph is dag
    public boolean isDAG(Digraph G) {
        int n = G.V();
        boolean[] visited = new boolean[n];
        int[] timeFinished = new int[n];

        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                DFS(G, i, visited, timeFinished);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j : G.adj(i)) {
                if (timeFinished[i] < timeFinished[j]) {
                    return false;
                }
            }
        }

        return true;
    }

    // checks if vertex v is within bounds and is not null
    private boolean isValid(int v) {
        if (v > graph.V() || v < 0) {
            return false;
        }
        return true;
    }

    // unit testing (required)
    public static void main(String[] args) {
        String g = "digraph1.txt";
        In in = new In(g);
        Digraph G = new Digraph(in);
        SET<Integer> subA = new SET<Integer>();
        SET<Integer> subB = new SET<Integer>();
        subA.add(6);
        subA.add(7);
        subB.add(4);
        subB.add(2);
        ShortestCommonAncestor sca = new ShortestCommonAncestor(G);
        int anc = sca.ancestorSubset(subA, subB);
        int len = sca.lengthSubset(subA, subB);
        StdOut.println("ancestor of subA and subB = " + anc);
        StdOut.println("length of shortest path from subA to subB = " + len);
        while (!StdIn.isEmpty()) {
            int v = StdIn.readInt();
            int w = StdIn.readInt();
            int length = sca.length(v, w);
            int ancestor = sca.ancestor(v, w);
            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
        }
    }
}
