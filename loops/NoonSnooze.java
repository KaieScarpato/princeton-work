public class NoonSnooze {
    public static void main(String[] args) {
        int minsElapsed = Integer.parseInt(args[0]);
        int hours = minsElapsed / 60;
        int mins = minsElapsed % 60;
        if ((hours / 12) % 2 == 0) {
            if (mins > 10) {
                System.out.println(1 + (hours + 23) % 12 + ":" + mins + " pm");
            }
            else {
                System.out.println(1 + (hours + 23) % 12 + ":0" + mins + " pm");
            }
        }
        if ((hours / 12) % 2 == 1) {
            if (mins > 10) {
                System.out.println(1 + (hours + 23) % 12 + ":" + mins + " am");
            }
            else {
                System.out.println(1 + (hours + 23) % 12 + ":0" + mins + " am");
            }
        }
    }
}
