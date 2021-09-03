package com.hcy.esArsenal.utils;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @Author huchenying
 * @Description 可序列化函数式接口，用于lambda wrapper 接参
 * @Date 2021/8/16
 **/
@FunctionalInterface
public interface Func<T, R> extends Function<T, R>, Serializable {
}
