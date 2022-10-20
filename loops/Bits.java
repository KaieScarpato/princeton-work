public class Bits {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int x = 0;
        if (n < 0) {
            System.out.println("Illegal input");
        }
        if (n > 0) {
            while (n > 0) {
                n = n / 2;
                x += 1;
            }
            System.out.println(x);
        }
    }
}
