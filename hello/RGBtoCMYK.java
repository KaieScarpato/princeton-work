public class RGBtoCMYK {
    public static void main(String[] args) {
        int r = Integer.parseInt(args[0]);
        int g = Integer.parseInt(args[1]);
        int b = Integer.parseInt(args[2]);
        double w = Math.max((double) r / 255, Math.max((double) g / 255, (double) b / 255));
        double c = (w - (double) r / 255) / w;
        double m = (w - (double) g / 255) / w;
        double y = (w - (double) b / 255) / w;
        double k = 1 - w;
        System.out.println("red = " + r);
        System.out.println("green = " + g);
        System.out.println("blue = " + b);
        System.out.println("cyan = " + c);
        System.out.println("magenta = " + m);
        System.out.println("yellow = " + y);
        System.out.println("black = " + k);
    }
}
