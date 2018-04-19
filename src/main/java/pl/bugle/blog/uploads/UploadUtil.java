package pl.bugle.blog.uploads;

import pl.bugle.blog.entity.Images;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.FileImageOutputStream;
import org.primefaces.model.UploadedFile;

public abstract class UploadUtil {

    private static final ExternalContext EXTERNAL_CONTEXT = FacesContext.getCurrentInstance().getExternalContext();
    private static final String UPLOADS_PATH = EXTERNAL_CONTEXT.getInitParameter("uploads.PATH");
    private static final String QUALITY_JPEG = EXTERNAL_CONTEXT.getInitParameter("quality.JPEG");

    public static Images createImage(UploadedFile file, String name, String alt, int c[], Date date) throws IOException {
        
        ImageWriter writer = null;
        FileImageOutputStream output = null;
        
        try(InputStream is = file.getInputstream()) {                 

            LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Path path = Paths.get(UPLOADS_PATH + File.separator + ld.getYear() + File.separator + ld.getMonthValue());
            Files.createDirectories(path);

            BufferedImage croppedImage = ImageIO.read(is).getSubimage(c[0], c[1], c[2], c[3]);
            Image scaledImage = croppedImage.getScaledInstance(750, 300, Image.SCALE_AREA_AVERAGING);
            BufferedImage rgbImage = new BufferedImage(750, 300, BufferedImage.TYPE_INT_RGB);
            rgbImage.createGraphics().drawImage(scaledImage, 0, 0, Color.WHITE, null);

            JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(Locale.getDefault());
            jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            jpegParams.setCompressionQuality(Float.parseFloat(QUALITY_JPEG));

            writer = ImageIO.getImageWritersByFormatName("jpg").next();
            File image = new File(path + File.separator + name + ".jpg");
            
            if(image.exists()) throw new FileAlreadyExistsException(name);
                
            output = new FileImageOutputStream(image);
            writer.setOutput(output);
            writer.write(null, new IIOImage(rgbImage, null, null), jpegParams);

            return new Images(name, alt);
            
        } catch (FileAlreadyExistsException fae) {
            throw fae;
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if(output != null) {
                output.flush();
                output.close();
            }
            if(writer != null) {
                writer.dispose();
            }
        }
    }
    
    public static void deleteImage(String name, Date date) throws IOException {
        LocalDate ld = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Path path = Paths.get(UPLOADS_PATH + File.separator + ld.getYear() + File.separator + ld.getMonthValue() + File.separator + name + ".jpg");
        try {
            Files.deleteIfExists(path);
        } catch (IOException ex) {
           throw ex;
        }
    }
}
