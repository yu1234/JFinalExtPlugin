package com.yu.jinal.plugin.ext.annotation;

/**
 * Created by igreentree on 2017/7/21 0021.
 */
public interface IModelScanReport {
    void report(String tableName, String primaryKey, Class<?> modelClass);
    void report(String tableName, Class<?> modelClass);
}
