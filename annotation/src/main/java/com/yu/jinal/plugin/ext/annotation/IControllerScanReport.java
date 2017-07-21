package com.yu.jinal.plugin.ext.annotation;

/**
 * Created by igreentree on 2017/7/21 0021.
 */
public interface IControllerScanReport {
    void report(String controllerKey, Class<?> controllerClass, String module);
    void report(String controllerKey, Class<?> controllerClass, String viewPath, String module);
}
