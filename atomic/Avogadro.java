public class Avogadro {

    // estimates Avogadros number and Boltzmann's constant
    public static void main(String[] args) {
        double delta = 0.5;
        double temp = 297;
        double viscosity = 9.135e-4;
        double radius = 0.5e-6;
        double R = 8.31446;
        double meters_per_pixel = 0.175e-6;

        double[] displacements = StdIn.readAllDoubles();
        double total_dist = 0.0;
        for (int i = 0; i < displacements.length; i++) {
            total_dist += Math.pow(displacements[i] * meters_per_pixel, 2);
        }
        double var = total_dist / (2 * displacements.length);
        double diffusion = var / (2 * delta);
        double boltzmann = (diffusion * 6 * Math.PI * viscosity * radius) / temp;
        double avogadro = R / boltzmann;
        StdOut.println(boltzmann);
        StdOut.println(avogadro);
    }
}
