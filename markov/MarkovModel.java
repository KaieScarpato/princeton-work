public class MarkovModel {

    private ST<String, Integer> kgrams = new ST<String, Integer>();
    //ST with kgram and number of times it appears
    private ST<String, Integer> kgramsChar = new ST<String, Integer>();
    //ST with the character following a kgram and number of times it appears
    private int order;
    //length of kgram

    // creates a Markov model of order k for the specified text
    public MarkovModel(String text, int k) {
        this.order = k;
        text = text.replaceAll("\\s", "");
        text = text + text.substring(0, order);
        for (int i = 0; i < text.length() - order; i++) {
            String s = text.substring(i, i + order);
            if (!kgrams.contains(s)) {
                int count = 0;
                for (int j = 0; j < text.length() - order; j++) {
                    if (text.substring(j, j + order).equals(s)) {
                        count += 1;
                    }
                }
                this.kgrams.put(s, count);
            }
        }
        for (int i = 0; i < text.length() - order; i++) {
            String s = text.substring(i, i + order + 1);
            if (!kgramsChar.contains(s)) {
                int count = 0;
                for (int j = 0; j < text.length() - order; j++) {
                    if (text.substring(j, j + order + 1).equals(s)) {
                        count += 1;
                    }
                }
                this.kgramsChar.put(s, count);
            }
        }
    }

    // returns the order k of this Markov model
    public int order() {
        return order;
    }

    // returns a string representation of the Markov model (as described below)
    public String toString() {
        String returnString = "";
        for (String word : kgrams.keys()) {
            returnString += word + ": ";
            for (String c : kgramsChar.keys()) {
                if (c.substring(0, order).equals(word)) {
                    returnString += String.valueOf(c.charAt(order)) + " ";
                    returnString += kgramsChar.get(c) + " ";
                }
            }
            returnString += "\n";
        }
        return returnString;
    }

    // returns the number of times the specified kgram appears in the text
    public int freq(String kgram) {
        if (kgram.length() != order) {
            throw new IllegalArgumentException("Illegal kgram length");
        }
        if (!kgrams.contains(kgram)) {
            return 0;
        }
        else {
            return kgrams.get(kgram);
        }
    }

    // returns the number of times the character c follows the specified
    // kgram in the text
    public int freq(String kgram, char c) {
        if (kgram.length() != order) {
            throw new IllegalArgumentException("Illegal kgram length");
        }
        kgram += String.valueOf(c);
        if (!kgramsChar.contains(kgram)) {
            return 0;
        }
        else {
            return kgramsChar.get(kgram);
        }
    }

    // returns a random character that follows the specified kgram in the text,
    // chosen with weight proportional to the number of times that character
    // follows the specified kgram in the text
    public char random(String kgram) {
        if (kgram.length() != order) {
            throw new IllegalArgumentException("Illegal kgram length");
        }
        if (!kgrams.contains(kgram)) {
            throw new IllegalArgumentException("Illegal kgram input");
        }
        String returnChar = "";
        int idx = 0;
        int[] freqArr = new int[kgrams.get(kgram)];
        for (String word : kgramsChar) {
            if (word.substring(0, order).equals(kgram)) {
                returnChar += String.valueOf(word.charAt(word.length() - 1));
                freqArr[idx] = kgramsChar.get(word);
                idx += 1;
            }
        }
        idx = StdRandom.discrete(freqArr);
        return returnChar.charAt(idx);
    }

    // tests this class by directly calling all instance methods
    public static void main(String[] args) {
        String text1 = "gagggagaggcgagaaa";
        MarkovModel model1 = new MarkovModel(text1, 2);
        StdOut.println();
        StdOut.println(model1.toString());
        StdOut.println(model1.random("ga"));
    }
}
