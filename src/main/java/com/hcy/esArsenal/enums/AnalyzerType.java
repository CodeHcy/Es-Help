package com.hcy.esArsenal.enums;

/**
 * @Author huchenying
 * @Description
 * @Date 2021/8/17
 **/
public enum AnalyzerType {
    IK_MAX_WORD("ik_max_word"),IK_SMART("ik_smart"),STANDARD("standard");

    private final String name;
    AnalyzerType(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

}
