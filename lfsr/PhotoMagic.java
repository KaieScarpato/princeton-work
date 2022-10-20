import java.awt.Color;

public class PhotoMagic {

    // returns a new picture that has a transformed copy of the given picture, using the given lfsr.
    public static Picture transform(Picture picture, LFSR lfsr) {
        int w = picture.width(); //width of picture
        int h = picture.height(); //height of picture
        Picture p = new Picture(w, h); //new picture of height h and width w
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                Color c = picture.get(i, j); //color of pixel at (w,h)
                int r1 = c.getRed();//red intensity
                int g1 = c.getGreen();//green intensity
                int b1 = c.getBlue();//blue intensity
                int r2 = lfsr.generate(8) ^ r1;//changes red intensity using the given lsfr
                int g2 = lfsr.generate(8) ^ g1;//changes green intensity using the given lsfr
                int b2 = lfsr.generate(8) ^ b1;//changes blue intensity using the given lsfr
                Color newColor = new Color(r2, g2, b2);//new color of pixel at (w,h)
                p.set(i, j, newColor);
            }
        }
        return p;
    }

    // takes the name of an image file and a description of an lfsr as command-line arguments;
    // displays a copy of the image that is "encrypted" using the LFSR.
    public static void main(String[] args) {
        Picture picture = new Picture(args[0]);//reads input picture
        LFSR lsfr = new LFSR(args[1], Integer.parseInt(args[2]));//creates an lfsr
        Picture newPic = transform(picture, lsfr);//transforms picture using lfsr
        newPic.show();
    }
}

