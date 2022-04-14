package com.cy.store.service.ex;

/** 作为业务层异常的基类 throws new ServiceException() */
public class ServiceException extends RuntimeException{
    // 什么信息都不提供
    public ServiceException() {
        super();
    }
    // 抛出信息
    public ServiceException(String message) {
        super(message);
    }
    // 抛出信息并指定异常对象
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    protected ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
