package com.yu.example.main;

import com.jfinal.core.JFinal;

/**
 * Created by igreentree on 2017/7/19 0019.
 */
public class AppStart {

    public static void main(String[] args) {
        JFinal.start("src/main/webapp", 80, "/");
    }
}
