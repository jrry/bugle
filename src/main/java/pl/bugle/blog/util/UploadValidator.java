package pl.bugle.blog.util;

import java.util.regex.Pattern;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.omnifaces.util.Messages;
import org.primefaces.model.UploadedFile;

@FacesValidator("uploadValidator")
public class UploadValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        
        UploadedFile file = (UploadedFile) value;
        Pattern pattern = Pattern.compile("image/(jpe?g|gif|png|bmp)");
        
        if (file.getSize() == 0 || file.getSize() > 5242880) {
            throw new ValidatorException(Messages.createError("Załaduj obrazek nie większy 5 MB"));
        }
        
        if (!pattern.matcher(file.getContentType()).matches()) {
            throw new ValidatorException(Messages.createError("Załadowany plik nie jest obrazkiem"));
        }

    }
    
}
