package com.yu.jinal.plugin.ext.annotation;

import com.jfinal.plugin.IPlugin;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Model;
import com.yu.jinal.plugin.ext.annotation.annotation.JFinalModel;
import com.yu.jinal.plugin.ext.annotation.utils.AnnotationScanUtils;
import com.yu.jinal.plugin.ext.annotation.utils.StringTools;

import java.io.IOException;
import java.lang.annotation.Annotation;

/**
 * Created by yu on 2017/7/21 0021.
 * 实现jfinal model类注解注入插件
 */
public class ModelAnnotationPlugin implements IPlugin {
    //需要扫描的包空间
    private String[] scanPackage;
    //扫描结果回调接口
    private IModelScanReport modelScanReport;
    // ActiveRecord plugin.
    private ActiveRecordPlugin arp;

    public ModelAnnotationPlugin(String[] scanPackage) {

        this.scanPackage = scanPackage;
    }


    //注入方式一
    public void injectModel(IModelScanReport modelScanReport) {
        this.modelScanReport = modelScanReport;
    }

    //注入方式二
    public void injectModel(ActiveRecordPlugin arp) {
        this.arp = arp;
    }

    //初始化参数
    public boolean start() {
        //开始扫描
        try {
            AnnotationScanUtils.scanAnnotationByClass(new Class[]{JFinalModel.class}, new IAnnotationScanReport() {
                public void report(Class<? extends Annotation> aClass, String s) {
                    try {
                        Class modelClass = Class.forName(s);
                        if (modelClass != null) {
                            JFinalModel jFinalModel = (JFinalModel) modelClass.getAnnotation(JFinalModel.class);
                            if (jFinalModel != null) {
                                String tableName = jFinalModel.tableName();
                                String primaryKey = jFinalModel.primaryKey();
                                if (StringTools.isNotBlank(tableName)) {
                                    if (modelScanReport != null) {
                                        modelScanReport.report(tableName, modelClass);
                                        if (StringTools.isNotBlank(primaryKey) && !primaryKey.equals("id")) {
                                            modelScanReport.report(tableName, primaryKey, modelClass);
                                        }
                                    }
                                    if (arp != null) {
                                        if (StringTools.isNotBlank(primaryKey) && !primaryKey.equals("id")) {
                                            arp.addMapping(tableName, primaryKey, (Class<? extends Model<?>>) modelClass);
                                        } else {
                                            arp.addMapping(tableName, (Class<? extends Model<?>>) modelClass);
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
        return true;
    }

    public boolean stop() {
        return true;
    }
}
