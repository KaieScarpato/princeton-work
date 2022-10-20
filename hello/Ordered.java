public class Ordered {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int y = Integer.parseInt(args[1]);
        int z = Integer.parseInt(args[2]);
        boolean b;
        b = ((x < y) && (y < z)) || ((z < y) && (y < x));
        System.out.println(b);
    }
}
