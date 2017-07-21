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

    /**
     * 路由配置
     * @param routes
     */
    public void configRoute(Routes routes) {
        System.out.println("configRoute");
        //获取控制器注解注入对象
        ControllerAnnotation controllerAnnotation = new ControllerAnnotation(scanControllerPackage);
        //第一种注入方式（直接传入路由对象，路径统一配置（适合单路由））
        controllerAnnotation.injectController(routes);
        //第二种注入方式（直接传入回调接口，路径配置（适合拆分路由））
        controllerAnnotation.injectController(new IControllerScanReport() {
            /**
             *
             * @param controllerKey 访问某个 Controller 所需要的一个字符,相当于controllerKey，可定义多个
             * @param controllerClass 控制器Class
             * @param module 所属模块，适用于路由拆分,默认“”
             */
            public void report(String controllerKey, Class<?> controllerClass, String module) {

                //do something
            }

            /**
             *
             * @param controllerKey 访问某个 Controller 所需要的一个字符,相当于controllerKey，可定义多个
             * @param controllerClass 控制器Class
             * @param viewPath 该 Controller 返回的视图的相对路径,默认“controllerKey”
             * @param module 所属模块，适用于路由拆分,默认“”
             */
            public void report(String controllerKey, Class<?> controllerClass, String viewPath, String module) {
                //do something
            }
        });
    }

    public void configEngine(Engine engine) {
        System.out.println("configEngine");
    }

    /**
     * 插件添加配置
     * @param plugins
     */
    public void configPlugin(Plugins plugins) {
        System.out.println("configPlugin");
        //连接数据库
        DruidPlugin dp = new DruidPlugin("jdbcUrl", "username", "password");
        plugins.add(dp);
        //建立表和对象映射关系对象
        final ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
        //添加表和对象映射关系，扫描@JFinalModel注解(注入方式一：直接传入ActiveRecordPlugin对象实例)
        ModelAnnotationPlugin map1 = new ModelAnnotationPlugin(scanModelPackage);
        map1.injectModel(arp);
        //添加表和对象映射关系，扫描@JFinalModel注解(注入方式一：直接传入ActiveRecordPlugin对象实例)
        ModelAnnotationPlugin map2 = new ModelAnnotationPlugin(scanModelPackage);
        map2.injectModel(new IModelScanReport() {
            public void report(String tableName, String primaryKey, Class<?> modelClass) {
                //do something
            }

            public void report(String tableName, Class<?> modelClass) {
               //do something
            }
        });
        //添加插件(注：注解注入插件必须在ActiveRecordPlugin插件之前添加)
        plugins.add(map1);
        plugins.add(arp);

    }

    public void configInterceptor(Interceptors interceptors) {
        System.out.println("configInterceptor");
    }

    public void configHandler(Handlers handlers) {
        System.out.println("configHandler");
    }
}
