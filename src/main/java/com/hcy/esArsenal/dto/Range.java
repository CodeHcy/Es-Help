package com.hcy.esArsenal.dto;

import com.hcy.esArsenal.enums.RangeType;
import lombok.Data;

/**
 * @Author huchenying
 * @Description 范围值数据包装类
 * @Date 2021/9/3
 **/
@Data
public class Range {
    private RangeType rangeType;
    private String value;
}
