package com.yu.jinal.plugin.ext.annotation.utils;



import com.yu.jinal.plugin.ext.annotation.IAnnotationScanReport;
import eu.infomas.annotation.AnnotationDetector;

import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * Created by igreentree on 2017/7/20 0020.
 */
public class AnnotationScanUtils {


    /**
     * 扫描类注解
     *
     * @param annotationClasses 需要扫描的注解
     * @param report            扫描结果回调
     * @param scanPackage       需要扫描的包
     */
    public static void scanAnnotationByClass(final Class<? extends Annotation>[] annotationClasses, final IAnnotationScanReport report, String... scanPackage) throws IOException {
        AnnotationDetector.Reporter reporter = new AnnotationDetector.TypeReporter() {
            public void reportTypeAnnotation(Class<? extends Annotation> aClass, String s) {
                report.report(aClass, s);
            }

            public Class<? extends Annotation>[] annotations() {
                return annotationClasses;
            }
        };
        startScan(reporter, scanPackage);

    }

    /**
     * 扫描属性注解
     *
     * @param annotationClasses 需要扫描的注解
     * @param report            扫描结果回调
     * @param scanPackage       需要扫描的包
     */
    public static void scanAnnotationByField(final Class<? extends Annotation>[] annotationClasses, final IAnnotationScanReport report, String... scanPackage) throws IOException {
        AnnotationDetector.Reporter reporter = new AnnotationDetector.FieldReporter() {

            public void reportFieldAnnotation(Class<? extends Annotation> aClass, String s, String s1) {
                report.report(aClass, s);
            }

            public Class<? extends Annotation>[] annotations() {
                return annotationClasses;
            }
        };
        startScan(reporter, scanPackage);
    }

    /**
     * 扫描方法注解
     *
     * @param annotationClasses 需要扫描的注解
     * @param report            扫描结果回调
     * @param scanPackage       需要扫描的包
     */
    public static void scanAnnotationMethod(final Class<? extends Annotation>[] annotationClasses, final IAnnotationScanReport report, String... scanPackage) throws IOException {
        AnnotationDetector.Reporter reporter = new AnnotationDetector.MethodReporter() {
            public void reportMethodAnnotation(Class<? extends Annotation> aClass, String s, String s1) {
                report.report(aClass, s);
            }

            public Class<? extends Annotation>[] annotations() {
                return annotationClasses;
            }
        };
        startScan(reporter, scanPackage);
    }

    /**
     * 开始扫描
     *
     * @param reporter
     * @param scanPackage
     */
    private static void startScan(AnnotationDetector.Reporter reporter, String... scanPackage) throws IOException {
        AnnotationDetector cf = new AnnotationDetector(reporter);

            if (StringTools.isNotBlank(scanPackage)) {
                cf.detect(scanPackage);
            } else {
                cf.detect();
            }

    }


}
