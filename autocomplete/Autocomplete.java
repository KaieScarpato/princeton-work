import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class Autocomplete {
    /*
    Creates array of terms containing a specific prefix and returns them in
    descending order.
     */
    Term[] terms;    // Array of terms

    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) throw new IllegalArgumentException("");

        int n = terms.length;
        this.terms = new Term[n];

        for (int i = 0; i < n; i++) {
            if (terms[i] == null) throw new IllegalArgumentException("");
            else {
                this.terms[i] = terms[i];
            }
        }

        Arrays.sort(this.terms);
    }

    // Returns all terms that start with the given prefix, in descending order of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) throw new IllegalArgumentException("");

        Term pre = new Term(prefix, 0);
        int n = numberOfMatches(prefix);
        Term[] preArr = new Term[n];

        int len = terms.length;
        int j = 0;
        for (int i = 0; i < len; i++) {
            if (Term.byPrefixOrder(prefix.length()).compare(terms[i], pre) == 0) {
                preArr[j] = terms[i];
                j++;
            }
        }
        Arrays.sort(preArr, Term.byReverseWeightOrder());
        return preArr;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) throw new IllegalArgumentException("");

        Term t = new Term(prefix, 0);

        int i = BinarySearchDeluxe.firstIndexOf(terms, t, Term.byPrefixOrder(prefix.length()));
        int j = BinarySearchDeluxe.lastIndexOf(terms, t, Term.byPrefixOrder(prefix.length()));

        if (i < 0 || j < 0) {
            return 0;
        }
        return j - i + 1;

    }

    // unit testing (required)
    public static void main(String[] args) {

        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }
}

