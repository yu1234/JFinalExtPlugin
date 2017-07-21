package com.yu.jinal.plugin.ext.annotation;


import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
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
    private Routes routes;

    //路径统一配置（适合单路由）
    public ControllerAnnotation(String[] scanPackage, Routes routes) {
        this.scanPackage = scanPackage;
        this.routes = routes;
        startScan();
    }

    //路径配置（适合拆分路由）
    public ControllerAnnotation(String[] scanPackage, IControllerScanReport controllerScanReport) {
        this.scanPackage = scanPackage;
        this.controllerScanReport = controllerScanReport;
        startScan();
    }

    /**
     * 开始扫描注解
     */
    private void startScan() {
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
                                            if (controllerScanReport != null) {
                                                if ("controllerKey".equals(viewPath)) {
                                                    controllerScanReport.report(controllerKey, modelClass, module);
                                                } else {
                                                    controllerScanReport.report(controllerKey, modelClass, viewPath, module);
                                                }
                                            }
                                            if (routes != null) {
                                                if ("controllerKey".equals(viewPath)) {
                                                    routes.add(controllerKey, (Class<? extends Controller>) modelClass);
                                                } else {
                                                    routes.add(controllerKey, (Class<? extends Controller>) modelClass, viewPath);
                                                }
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
