public class RandomWalkers {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        int x = 0;
        int y = 0;
        double distTotal = 0;
        for (int i = 0; i < trials; i++) {
            for (int j = 1; j <= n; j++) {
                double a = Math.random();
                if (a < 0.25) {
                    x = x - 1;
                }
                if ((a >= 0.25) && (a < 0.5)) {
                    x = x + 1;
                }
                if ((a >= 0.5) && (a < 0.75)) {
                    y = y - 1;
                }
                if (a >= 0.75) {
                    y = y + 1;
                }
            }
            distTotal += Math.abs(x * x) + Math.abs(y * y);
        }
        System.out.println("mean squared distance = " + distTotal / trials);
    }
}
