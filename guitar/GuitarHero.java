public class GuitarHero {
    public static void main(String[] args) {
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
        GuitarString[] stringArr = new GuitarString[37];
        for (int i = 0; i < 37; i++) {
            double f = 440 * Math.pow(2, ((i - 24) / 12.0));
            stringArr[i] = new GuitarString(f);
        }
        Keyboard keyboardVirtual = new Keyboard();
        while (true) {

            // check if the user has played a key; if so, process it
            if (keyboardVirtual.hasNextKeyPlayed()) {

                // the key the user played
                char key = keyboardVirtual.nextKeyPlayed();

                // pluck the corresponding string
                stringArr[keyboard.indexOf(key)].pluck();
            }

            // compute the superposition of the samples
            double s = 0;
            for (int i = 0; i < 37; i++) {
                s += stringArr[i].sample();
            }

            // play the sample on standard audio
            StdAudio.play(s);

            // advance the simulation of each guitar string by one step
            for (int i = 0; i < 37; i++) {
                stringArr[i].tic();
            }

        }
    }
}
