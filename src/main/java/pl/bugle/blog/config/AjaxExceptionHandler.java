package pl.bugle.blog.config;

import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.FacesContext;
import org.omnifaces.exceptionhandler.FullAjaxExceptionHandler;

public class AjaxExceptionHandler extends FullAjaxExceptionHandler {
    
    public AjaxExceptionHandler(ExceptionHandler wrapped) {
        super(wrapped);
    }
    
    @Override
    protected void logException(FacesContext context, Throwable exception, String location, String message, Object... parameters) {
        if (exception instanceof ViewExpiredException) {
            super.logException(context, null, location, message, parameters);
        } else {
            super.logException(context, exception, location, message, parameters);
        }
    }
}
