package com.yu.jinal.plugin.ext.annotation.annotation;

import java.lang.annotation.*;

/**
 * Created by yu on 2017/7/21 0021.
 * jfinal控制器注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JFinalController {
    String[] value();//访问某个 Controller 所需要的一个字符

    String module() default "";//所属模块，适用于路由拆分

    String viewPath() default "controllerKey";//该 Controller 返回的视图的相对路径
}
