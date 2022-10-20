public class RandomWalker {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        int x = 0;
        int y = 0;
        System.out.println("(" + x + "," + y + ")");

        for (int i = 1; i <= n; i++) {
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
            System.out.println("(" + x + "," + y + ")");
        }
        double dist = Math.abs(x * x) + Math.abs(y * y);
        System.out.println("Squared distance " + "= " + dist);
    }
}
