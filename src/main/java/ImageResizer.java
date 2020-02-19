import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Queue;

public class ImageResizer extends Thread {

    private Queue<File> queue;
    private int newWidth;
    private String dstFolder;
    private long start;
    String name;

    public ImageResizer(Queue queue, int newWidth, String dstFolder, long start, String name) {
        this.queue = queue;
        this.newWidth = newWidth;
        this.dstFolder = dstFolder;
        this.start = start;
        this.name = name;
    }

    @Override
    public void run() {

        for (;;) {

        try
        {
            File currentFile = queue.poll();

            if (currentFile == null) { return;}

             BufferedImage image = ImageIO.read(currentFile);

             if(image == null) { System.out.println("cant read this image"); continue; }

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

                File newFile = new File(dstFolder + File.separator + currentFile.getName());
                ImageIO.write(imageFinal, "jpg", newFile);

                System.out.println(name + " " + " has copied file name " + currentFile.getName() + " size " +  currentFile.length()/1000 + " kb for " + (System.currentTimeMillis() - start) + "ms" );


        }
        catch (Exception ex) { ex.printStackTrace(); } } }


    private BufferedImage scale(BufferedImage imageStart, BufferedImage imageEnd, final double scale, final int type) {

        AffineTransform affineTransform = AffineTransform.getScaleInstance(scale, scale);
        AffineTransformOp affineTransformOp = new AffineTransformOp(affineTransform, type);

        return  affineTransformOp.filter(imageStart, imageEnd);
    }
}





