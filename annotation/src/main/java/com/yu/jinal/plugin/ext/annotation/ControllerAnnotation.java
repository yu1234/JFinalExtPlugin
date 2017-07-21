package com.yu.jinal.plugin.ext.annotation;



import com.yu.jinal.plugin.ext.annotation.annotation.JFinalController;
import com.yu.jinal.plugin.ext.annotation.utils.AnnotationScanUtils;
import com.yu.jinal.plugin.ext.annotation.utils.StringTools;

import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * Created by igreentree on 2017/7/21 0021.
 * 控制器注解扫描类
 */
public class ControllerAnnotation {
    private IControllerScanReport controllerScanReport;
    //需要扫描的包空间
    private String[] scanPackage;

    public ControllerAnnotation() {
    }

    public ControllerAnnotation(String[] scanPackage, IControllerScanReport controllerScanReport) {
        this.scanPackage = scanPackage;
        this.controllerScanReport = controllerScanReport;
        startScan(this.scanPackage, this.controllerScanReport);
    }

    /**
     * 开始扫描注解
     *
     * @param scanPackage
     * @param controllerScanReport
     */
    public void startScan(String[] scanPackage, final IControllerScanReport controllerScanReport) {
        if (controllerScanReport == null) {
            throw new RuntimeException("IControllerScanReport not null");
        }
        //开始扫描
        try {
            AnnotationScanUtils.scanAnnotationByClass(new Class[]{JFinalController.class}, new IAnnotationScanReport() {
                public void report(Class<? extends Annotation> aClass, String s) {
                    try {
                        Class modelClass = Class.forName(s);
                        if (modelClass != null) {
                            JFinalController jFinalController = (JFinalController) modelClass.getAnnotation(JFinalController.class);
                            if (jFinalController != null) {
                                String[] values = jFinalController.value();
                                String module = jFinalController.module();
                                String viewPath = jFinalController.viewPath();
                                if (StringTools.isNotBlank(values)) {
                                    for (String controllerKey : values) {
                                        if (StringTools.isNotBlank(controllerKey)) {
                                            if ("controllerKey".equals(viewPath)) {
                                                controllerScanReport.report(controllerKey, modelClass, module);
                                            } else {
                                                controllerScanReport.report(controllerKey, modelClass, viewPath, module);
                                            }
                                        }


                                    }

                                }

                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, scanPackage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
