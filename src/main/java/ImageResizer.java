import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageResizer extends Thread {

    File[] files;
    private int newWidth;
    private String dstFolder;
    private long start;

    public ImageResizer(File[] files, int newWidth, String dstFolder, long start) {
        this.files = files;
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
        this.start = start;
    }

    @Override
    public void run() {
        try
        {
            for(File file : files)
            {
                BufferedImage image = ImageIO.read(file);

                if(image == null) {continue; }

                double scale1 = (double) (newWidth*2) / image.getWidth();

                int w = (int) (image.getWidth()*scale1);
                int h = (int) (image.getHeight()*scale1);

                BufferedImage image2 = new BufferedImage(w, h, image.getType() );

                int type1 = AffineTransformOp.TYPE_NEAREST_NEIGHBOR;

                image2 = scale(image, image2, scale1, type1);

                double scale2 = 0.5;

                int w2 = (int) (image2.getWidth()*scale2);
                int h2 = (int) (image2.getHeight()*scale2);

                BufferedImage imageFinal = new BufferedImage(w2, h2, image2.getType() );

                int type2 = AffineTransformOp.TYPE_BICUBIC;

                imageFinal = scale(image2, imageFinal, scale2, type2);



                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(imageFinal, "jpg", newFile);

            }
        }
        catch (Exception ex) { ex.printStackTrace(); }

        System.out.println("Duration: " + (System.currentTimeMillis() - start));

    }

    private BufferedImage scale(BufferedImage imageStart, BufferedImage imageEnd, final double scale, final int type) {

        AffineTransform affineTransform = AffineTransform.getScaleInstance(scale, scale);
        AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, type);

        return  affineTransformOp.filter(imageStart, imageEnd);
    }
}





