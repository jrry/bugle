package pl.bugle.blog.util;

import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.omnifaces.util.Messages;

@FacesValidator("passwordValidator")
public class PasswordValidator implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        UIInput confirm = (UIInput) component.getAttributes().get("confirm");
        if (confirm.getValue() != null) {
            String confirmPassword = confirm.getValue().toString();
            if (!confirmPassword.equals(value.toString())) {
                throw new ValidatorException(Messages.createError("Podane hasła różnią się"));
            }
        }
    }
    
}
