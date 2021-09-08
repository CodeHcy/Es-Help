package com.hcy.esArsenal.dto;

import com.hcy.esArsenal.enums.RangeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author huchenying
 * @Description 范围值数据包装类
 * @Date 2021/9/3
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Range {
    private RangeType rangeType;
    private String value;
}
