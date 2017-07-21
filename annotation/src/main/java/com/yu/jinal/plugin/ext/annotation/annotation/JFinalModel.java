package com.yu.jinal.plugin.ext.annotation.annotation;

import java.lang.annotation.*;

/**
 * Created by igreentree on 2017/7/20 0020.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JFinalModel {
     String tableName(); //数据库表名
     String primaryKey() default "id"; //主键名称，默认为“id”
}
