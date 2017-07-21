package com.yu.example.config;

import com.jfinal.config.*;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import com.yu.example.bean.User;
import com.yu.jinal.plugin.ext.annotation.ControllerAnnotation;
import com.yu.jinal.plugin.ext.annotation.IControllerScanReport;
import com.yu.jinal.plugin.ext.annotation.IModelScanReport;
import com.yu.jinal.plugin.ext.annotation.ModelAnnotationPlugin;


/**
 * Created by yu on 2017/7/20 0020.
 */
public class JFinalBaseConfig extends JFinalConfig {
    /**
     * model类扫描包
     */
    private final static String[] scanModelPackage = new String[]{
            "com.yu.example"
    };
    /**
     * controller类扫描包
     */
    private final static String[] scanControllerPackage = new String[]{
            "com.yu.example"
    };

    public void configConstant(Constants constants) {
        //开启debug模式
        constants.setDevMode(true);
        System.out.println("configConstant");
    }

    public void configRoute(final Routes routes) {
        System.out.println("configRoute");
        ControllerAnnotation controllerAnnotation = new ControllerAnnotation(scanControllerPackage, new IControllerScanReport() {
            public void report(String controllerKey, Class<?> controllerClass, String module) {
                routes.add(controllerKey, (Class<? extends Controller>) controllerClass);
            }

            public void report(String controllerKey, Class<?> controllerClass, String viewPath, String module) {
                routes.add(controllerKey, (Class<? extends Controller>) controllerClass, viewPath);
            }
        });
    }

    public void configEngine(Engine engine) {
        System.out.println("configEngine");
    }

    public void configPlugin(Plugins plugins) {
        System.out.println("configPlugin");
        //连接数据库
        DruidPlugin dp = new DruidPlugin("jdbc:mysql://172.104.98.7/YuResource", "yu", "5227146");
        plugins.add(dp);
        //建立表和对象映射关系对象
        final ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
        //添加表和对象映射关系，扫描@JFinalModel注解
        ModelAnnotationPlugin map = new ModelAnnotationPlugin(scanModelPackage, new IModelScanReport() {
            public void report(String tableName, String primaryKey, Class<?> modelClass) {
                arp.addMapping(tableName, primaryKey, (Class<? extends Model<?>>) modelClass);
            }

            public void report(String tableName, Class<?> modelClass) {
                arp.addMapping(tableName, User.class);
            }
        });
        plugins.add(map);
        plugins.add(arp);

    }

    public void configInterceptor(Interceptors interceptors) {
        System.out.println("configInterceptor");
    }

    public void configHandler(Handlers handlers) {
        System.out.println("configHandler");
    }
}
