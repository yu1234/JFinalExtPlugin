package com.yu.jinal.plugin.ext.annotation;

import java.lang.annotation.Annotation;

/**
 * Created by igreentree on 2017/7/20 0020.
 */
public interface IAnnotationScanReport {
    void report(Class<? extends Annotation> aClass, String s);
}
