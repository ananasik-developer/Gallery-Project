package ru.student.gallery.util;

import jakarta.servlet.http.Part;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.Iterator;

public class ImageUtil {

    public static String save(Part part, String directory, String prefix, int maxSize) {
        String contentType = part.getContentType();
        boolean png = "image/png".equals(contentType);

        if (!png && !"image/jpeg".equals(contentType)) {
            throw new IllegalArgumentException("Допустимы только JPG и PNG");
        }

        BufferedImage source;
        try (InputStream in = part.getInputStream()) {
            source = ImageIO.read(in);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (source == null) {
            throw new IllegalArgumentException("Не удалось прочитать изображение");
        }

        BufferedImage target = scale(source, maxSize);
        String fileName = prefix + "_" + System.currentTimeMillis() + (png ? ".png" : ".jpg");
        File file = new File(directory, fileName);

        try {
            if (png) {
                ImageIO.write(target, "png", file);
            } else {
                writeJpeg(target, file);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return fileName;
    }

    private static BufferedImage scale(BufferedImage source, int maxSize) {
        int width = source.getWidth();
        int height = source.getHeight();

        if (width <= maxSize && height <= maxSize) {
            return source;
        }

        double ratio = (double) maxSize / Math.max(width, height);
        int newWidth = Math.max(1, (int) Math.round(width * ratio));
        int newHeight = Math.max(1, (int) Math.round(height * ratio));

        int type = source.getColorModel().hasAlpha()
                ? BufferedImage.TYPE_INT_ARGB
                : BufferedImage.TYPE_INT_RGB;

        BufferedImage target = new BufferedImage(newWidth, newHeight, type);
        Graphics2D graphics = target.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.drawImage(source, 0, 0, newWidth, newHeight, null);
        graphics.dispose();

        return target;
    }

    private static void writeJpeg(BufferedImage image, File file) throws Exception {
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter writer = writers.next();

        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.85f);

        try (ImageOutputStream out = ImageIO.createImageOutputStream(file)) {
            writer.setOutput(out);
            writer.write(null, new IIOImage(image, null, null), param);
        } finally {
            writer.dispose();
        }
    }
}
