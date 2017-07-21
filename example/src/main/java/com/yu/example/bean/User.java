package com.yu.example.bean;

import com.jfinal.plugin.activerecord.Model;
import com.yu.jinal.plugin.ext.annotation.annotation.JFinalModel;


/**
 * Created by yu on 2017/7/19 0019.
 */
@JFinalModel(tableName = "user")
public class User extends Model<User> {
    public static final User dao = new User().dao();
    public static final String TABLE_NAME = "user";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String AGE = "age";

    private String id;
    private String name;
    private int age;

    public String getId() {
        return this.getStr(ID);
    }

    public void setId(String id) {
        this.id = id;
        this.set(ID, id);
    }

    public String getName() {
        return this.getStr(NAME);
    }

    public void setName(String name) {
        this.name = name;
        this.set(NAME, name);
    }

    public int getAge() {
        return this.getInt(AGE);
    }

    public void setAge(int age) {
        this.age = age;
        this.set(AGE, age);
    }
}