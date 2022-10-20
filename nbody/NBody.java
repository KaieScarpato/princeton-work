public class NBody {
    public static void main(String[] args) {
        // read in inputs from args and input file
        double t = Double.parseDouble(args[0]);   //total time
        double deltaT = Double.parseDouble(args[1]);   //time increment
        int num = StdIn.readInt();   //number of planets
        double radius = StdIn.readDouble();   //radius of universe

        //initalizes variables
        double[] px = new double[num];  //position-x array
        double[] py = new double[num];  //position-y array
        double[] vx = new double[num];  //velocity-x array
        double[] vy = new double[num];  //veloicty-y array
        double[] mass = new double[num];  //mass array
        double[] fx = new double[num];  //force-x array
        double[] fy = new double[num];  //force-y array
        double G = 6.67e-11;            //gravitational constant G
        String[] image = new String[num];  //image of plants array

        //reads inputs from file
        for (int i = 0; i < num; i++) {
            px[i] = StdIn.readDouble();
            py[i] = StdIn.readDouble();
            vx[i] = StdIn.readDouble();
            vy[i] = StdIn.readDouble();
            mass[i] = StdIn.readDouble();
            image[i] = StdIn.readString();
        }
        //set standard drawing
        StdDraw.setXscale(-radius, radius);
        StdDraw.setYscale(-radius, radius);
        StdDraw.enableDoubleBuffering();
        StdAudio.play("2001.wav");

        //display movement at each time increment
        for (double a = 0.0; a < t; a += deltaT) {
            //sets force arrays to 0
            for (int i = 0; i < num; i++) {
                fx[i] = 0;
                fy[i] = 0;
            }
            //finds force for each object in solar system
            for (int b1 = 0; b1 < num; b1++) {
                for (int b2 = 0; b2 < num; b2++) {
                    if (b1 != b2) {
                        double x = px[b2] - px[b1];
                        double y = py[b2] - py[b1];
                        double d = x * x + y * y;
                        double r = Math.sqrt(d);
                        double f = (G * mass[b1] * mass[b2]) / d;
                        fx[b1] += f * x / r;
                        fy[b1] += f * y / r;
                    }
                }
            }
            //finds position of each object at a specified time
            for (int j = 0; j < num; j++) {
                double ax = fx[j] / mass[j];
                double ay = fy[j] / mass[j];
                vx[j] = vx[j] + ax * deltaT;
                vy[j] = vy[j] + ay * deltaT;
                px[j] = px[j] + vx[j] * deltaT;
                py[j] = py[j] + vy[j] * deltaT;
            }

            //draw each image at a specified time
            StdDraw.setXscale(-radius, radius);
            StdDraw.setYscale(-radius, radius);
            StdDraw.picture(0, 0, "starfield.jpg");
            for (int j = 0; j < num; j++) {
                StdDraw.picture(px[j], py[j], image[j]);
            }
            StdDraw.show();
            StdDraw.pause(20);
        }
        StdOut.printf("%d\n", num);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < num; i++) {
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                          px[i], py[i], vx[i], vy[i], mass[i], image[i]);
        }
    }
}
