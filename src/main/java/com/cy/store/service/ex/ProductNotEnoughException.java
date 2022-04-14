package com.cy.store.service.ex;

public class ProductNotEnoughException extends ServiceException{
    public ProductNotEnoughException() {
        super();
    }

    public ProductNotEnoughException(String message) {
        super(message);
    }

    public ProductNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotEnoughException(Throwable cause) {
        super(cause);
    }

    protected ProductNotEnoughException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
