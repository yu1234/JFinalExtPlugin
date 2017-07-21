package com.yu.example.test;

import com.jfinal.core.Controller;
import com.yu.example.bean.User;
import com.yu.jinal.plugin.ext.annotation.annotation.JFinalController;

/**
 * Created by igreentree on 2017/7/19 0019.
 */
@JFinalController({"/", "/hello"})
public class HelloController extends Controller {
    public void index() {
        User user = User.dao.findById("1500453530921");
        renderHtml("id:" + user.getId() + "<br/>" + "name:" + user.getName() + "<br/>" + "age:" + user.getAge());
    }
}
