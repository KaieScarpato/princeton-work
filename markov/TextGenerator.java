public class TextGenerator {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]); //order of kgram
        int T = Integer.parseInt(args[1]); //length of string
        String text = StdIn.readString();  //text for kgram
        MarkovModel model1 = new MarkovModel(text, k);//creates a Markov Model of the text
        String s = text.substring(0, k);
        String kgram = s;

        //adds random characters to the string s based on the frequency of characters
        //following the given kgram
        for (int i = 0; i < T - k; i++) {
            s += model1.random(kgram);
            kgram += String.valueOf(s.charAt(s.length() - 1));
            kgram = kgram.substring(1);
        }
        StdOut.println(s);
    }
}
