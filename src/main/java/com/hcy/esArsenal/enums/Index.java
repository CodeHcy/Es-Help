package com.hcy.esArsenal.enums;

/**
 * @Author huchenying
 * @Description
 * @Date 2021/8/16
 **/
public enum Index {

    User("user"),DEMO_INDEX("demo_index");

    String name;
    private Index(String name){
       this.name = name;
    }

    public String getName() {
        return name;
    }
}
