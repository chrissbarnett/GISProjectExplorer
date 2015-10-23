package edu.tufts.gis.projectexplorer.component.image;

import edu.tufts.gis.projectexplorer.domain.ResourceType;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by cbarne02 on 4/30/15.
 */

public class ThumbnailCreator {
    private static final Logger log = LoggerFactory.getLogger(ThumbnailCreator.class);

    private int long_side = 300; //default

    private BufferedImage bufferedImage;
    private BufferedImage thumbBufferedImage;


    public Path createFromPdf(Path pdfPath, int size) throws IOException {
        pdfToImage(pdfPath);

        resizeToThumbnail(size); //retains aspect ratio

        String imageType = "png";

        Path outputPath = Files.createTempFile("thumbnail", "." + imageType);
        write(imageType, outputPath);

        return outputPath;
    }

    public Path createFromPdf(Path pdfPath) throws IOException {
        return createFromPdf(pdfPath, long_side);
    }


    private void pdfToImage(Path pdfPath) throws IOException {
        PDDocument document = null;
        try {
            try {
                File pdfFile = pdfPath.toFile();
                document = PDDocument.load(pdfFile);
            } catch (IOException ex) {

                log.error("{}", ex);
            }

            List<PDPage> pages = document.getDocumentCatalog().getAllPages();

            boolean validSize = pages.size() == 1;
            assert validSize;

            PDPage page = pages.get(0);
            bufferedImage = page.convertToImage();
        } finally {
            document.close();
        }
    }

    private void resizeToThumbnail(int long_side){
        int height = bufferedImage.getHeight();
        int width = bufferedImage.getWidth();

        boolean isLandscape = isLandscape(width, height);

        float ar = getAspectRatio(width, height);

        int thumbwidth = 0;
        int thumbheight = 0;
        if (isLandscape) {
            thumbwidth = long_side;
            thumbheight = (int) (thumbwidth / ar);
        } else {
            //portrait!
            thumbheight = long_side;
            thumbwidth = (int) (thumbheight * ar);
        }

        thumbBufferedImage = resize(bufferedImage, thumbwidth, thumbheight);

    }

    public static float getAspectRatio(int width, int height){

        return (float) width/height;
    }

    public static boolean isLandscape(int width, int height){
        return width >= height;
    }


    private static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }

    private void write(String imageType, Path outputPath){
        try {
            // retrieve image
            BufferedImage bi = thumbBufferedImage;
            File outputFile = outputPath.toFile();
            ImageIO.write(bi, imageType, outputFile);
            log.info("wrote image to {}", outputPath.toString());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
