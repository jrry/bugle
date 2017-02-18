package pl.bugle.blog.util;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class AjaxExceptionHandlerFactory extends ExceptionHandlerFactory {
    
    private ExceptionHandlerFactory wrapped;

    public AjaxExceptionHandlerFactory(ExceptionHandlerFactory wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return new AjaxExceptionHandler(getWrapped().getExceptionHandler());
    }

    @Override
    public ExceptionHandlerFactory getWrapped() {
        return wrapped;
    }
}
