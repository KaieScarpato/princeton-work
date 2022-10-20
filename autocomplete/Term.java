import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Term implements Comparable<Term> {
    /*
    Term data structure contains String query and long weight.
    Supports toString, and implements comparable on terms by weight and prefix.
     */

    private static class reverseWeight implements Comparator<Term> {

        public int compare(Term o1, Term o2) {
            if (o1.weight == o2.weight) return 0;
            if (o1.weight > o2.weight) return -1;
            else return 1;
        }
    }

    private static class byPrefix implements Comparator<Term> {
        private int len;        // length of prefix

        public byPrefix(int r) {
            len = r;
        }

        public int compare(Term o1, Term o2) {
            if (o1.query.length() < o2.query.length() && o1.query.length() < len) {
                return -1;
            }
            else if (o2.query.length() < o1.query.length()
                    && o2.query.length() < len) {
                return 1;
            }

            for (int i = 0; i < len; i++) {
                if (o1.query.charAt(i) > o2.query.charAt(i)) {
                    return 1;
                }
                else if (o1.query.charAt(i) < o2.query.charAt(i)) {
                    return -1;
                }
            }
            return 0;
        }
    }

    private long weight;           // weight of prefix
    private String query;          // string query

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null || weight < 0) throw new IllegalArgumentException("");
        this.query = query;
        this.weight = weight;

    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new reverseWeight();
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0) throw new IllegalArgumentException("");
        return new byPrefix(r);
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return this.query.compareTo(that.query);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return weight + "\t" + query;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term o = new Term("ABC", 1);
        Term t = new Term("ABD", 2);
        StdOut.println(o.toString());
        StdOut.println(t.toString());
        StdOut.println(byReverseWeightOrder().compare(o, t));
        StdOut.println(byPrefixOrder(3).compare(o, t));
        StdOut.println(o.compareTo(t));
    }

}
