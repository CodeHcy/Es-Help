package com.hcy.esArsenal.wrapper;

import com.hcy.esArsenal.dto.Range;
import com.hcy.esArsenal.enums.LinkType;
import com.hcy.esArsenal.enums.QueryType;
import com.hcy.esArsenal.enums.RangeType;
import com.hcy.esArsenal.utils.EsLambdaUtils;
import com.hcy.esArsenal.utils.Func;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author huchenying
 * @Description Es查询包装父类
 * @Date 2021/8/16
 **/
@SuppressWarnings("all")
public class EsQueryWrapper<T> extends EsAbstractQueryWrapper {

    static final Logger logger = LoggerFactory.getLogger(EsQueryWrapper.class);

    /**
     * @Author huchenying
     * @Description 对单字段模糊查询
     * @Date 2021/9/8 14:26
     * @Param [field, value, linkType] linkType 默认是 and 关系
     * @return com.hcy.esArsenal.wrapper.EsQueryWrapper<T>
     **/
    public EsQueryWrapper<T> like(Func<T, ?> field, String value,LinkType linkType) {
        //不仅要拿到列，还要拿到表的类型，从而知道是哪个索引
        doIt(getColumn(field), value, QueryType.MATCH, linkType);
        return this;
    }

    public EsQueryWrapper<T> like(Func<T, ?> field, String value) {
        //不仅要拿到列，还要拿到表的类型，从而知道是哪个索引
        doIt(getColumn(field), value, QueryType.MATCH, LinkType.AND);
        return this;
    }

    /**
     * @Author huchenying
     * @Description 全文查询，可以多个，默认是 or 关系
     * @Date 2021/9/8 14:28
     * @Param [field, text, linkType, condition]
     * @return com.hcy.esArsenal.wrapper.EsQueryWrapper<T>
     **/
    public EsQueryWrapper<T> likeAll(Func<T, ?> field, String text,LinkType linkType, boolean condition) {
        if (condition) {
            doIt(getColumn(field), text, QueryType.MATCHALL, linkType);
        }
        return this;
    }

    public EsQueryWrapper<T> likeAll(Func<T, ?> field, String text) {
        //不仅要拿到列，还要拿到表的类型，从而知道是哪个索引
        doIt(getColumn(field), text, QueryType.MATCHALL, LinkType.OR);
        return this;
    }

    public EsQueryWrapper<T> likeAll(Func<T, ?> field, String text, boolean condition) {
        if (condition) {
            doIt(getColumn(field), text, QueryType.MATCHALL, LinkType.OR);
        }
        return this;
    }

    /**
     * @return com.hcy.hengtncommoninfodemo.esArsenal.wrapper.EsQueryWrapper<T>
     * @Author huchenying
     * @Description 字段高亮
     * @Date 2021/8/23 10:32
     * @Param [column]
     **/
    public EsQueryWrapper<T> highlight(Func<T, ?> field) {
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(EsLambdaUtils.getColumn(EsLambdaUtils.resolve(field), true));
        setHighlight(highlightBuilder);
        return this;
    }

    /**
     * @Author huchenying
     * @Description 精确匹配
     * @Date 2021/9/8 14:22
     * @Param [field, text, linkType, condition] linkType不传是AND
     * @return com.hcy.esArsenal.wrapper.EsQueryWrapper<T>
     **/
    public EsQueryWrapper<T> eq(Func<T, ?> field, String text,LinkType linkType, boolean condition) {
        if (condition) {
            doIt(getColumn(field), text, QueryType.TERM, linkType);
        }
        return this;
    }

    public EsQueryWrapper<T> eq(Func<T, ?> field, String text, boolean condition) {
        if (condition) {
            doIt(getColumn(field), text, QueryType.TERM, LinkType.AND);
        }
        return this;
    }

    public EsQueryWrapper<T> eq(Func<T, ?> field, String text, LinkType linkType) {
        doIt(getColumn(field), text, QueryType.TERM, linkType);
        return this;
    }

    public EsQueryWrapper<T> eq(Func<T, ?> field, String text) {
        doIt(getColumn(field), text, QueryType.TERM, LinkType.AND);
        return this;
    }

    public EsQueryWrapper<T> gt(Func<T, ?> field, String text) {
        Range range = new Range(RangeType.GT, text);
        doIt(getColumn(field), range, QueryType.RANGE, LinkType.AND);
        return this;
    }

    public EsQueryWrapper<T> gt(Func<T, ?> field, String text, boolean condition) {
        if (condition) {
            Range range = new Range(RangeType.GT, text);
            doIt(getColumn(field), range, QueryType.RANGE, LinkType.AND);
        }
        return this;
    }

    public EsQueryWrapper<T> gt(Func<T, ?> field, String text, LinkType linkType) {
        Range range = new Range(RangeType.GT, text);
        doIt(getColumn(field), range, QueryType.RANGE, linkType);
        return this;
    }

    public EsQueryWrapper<T> gt(Func<T, ?> field, String text, LinkType linkType,boolean condition) {
        if (condition) {
            Range range = new Range(RangeType.GT, text);
            doIt(getColumn(field), range, QueryType.RANGE, linkType);
        }
        return this;
    }

    public EsQueryWrapper<T> lt(Func<T, ?> field, String text) {
        Range range = new Range(RangeType.LT, text);
        doIt(getColumn(field), range, QueryType.RANGE, LinkType.AND);
        return this;
    }

    public EsQueryWrapper<T> lt(Func<T, ?> field, String text, boolean condition) {
        if (condition) {
            Range range = new Range(RangeType.LT, text);
            doIt(getColumn(field), range, QueryType.RANGE, LinkType.AND);
        }
        return this;
    }

    public EsQueryWrapper<T> lt(Func<T, ?> field, String text, LinkType linkType) {
        Range range = new Range(RangeType.LT, text);
        doIt(getColumn(field), range, QueryType.RANGE, linkType);
        return this;
    }

    public EsQueryWrapper<T> lt(Func<T, ?> field, String text, LinkType linkType,boolean condition) {
        if (condition) {
            Range range = new Range(RangeType.LT, text);
            doIt(getColumn(field), range, QueryType.RANGE, linkType);
        }
        return this;
    }

    public EsQueryWrapper<T> gte(Func<T, ?> field, String text) {
        Range range = new Range(RangeType.GTE, text);
        doIt(getColumn(field), range, QueryType.RANGE, LinkType.AND);
        return this;
    }

    public EsQueryWrapper<T> gte(Func<T, ?> field, String text, boolean condition) {
        if (condition) {
            Range range = new Range(RangeType.GTE, text);
            doIt(getColumn(field), range, QueryType.RANGE, LinkType.AND);
        }
        return this;
    }

    public EsQueryWrapper<T> gte(Func<T, ?> field, String text, LinkType linkType) {
        Range range = new Range(RangeType.GTE, text);
        doIt(getColumn(field), range, QueryType.RANGE, linkType);
        return this;
    }

    public EsQueryWrapper<T> gte(Func<T, ?> field, String text, LinkType linkType,boolean condition) {
        if (condition) {
            Range range = new Range(RangeType.GTE, text);
            doIt(getColumn(field), range, QueryType.RANGE, linkType);
        }
        return this;
    }

    public EsQueryWrapper<T> lte(Func<T, ?> field, String text) {
        Range range = new Range(RangeType.LTE, text);
        doIt(getColumn(field), range, QueryType.RANGE, LinkType.AND);
        return this;
    }

    public EsQueryWrapper<T> lte(Func<T, ?> field, String text, boolean condition) {
        if (condition) {
            Range range = new Range(RangeType.LTE, text);
            doIt(getColumn(field), range, QueryType.RANGE, LinkType.AND);
        }
        return this;
    }

    public EsQueryWrapper<T> lte(Func<T, ?> field, String text, LinkType linkType) {
        Range range = new Range(RangeType.LTE, text);
        doIt(getColumn(field), range, QueryType.RANGE, linkType);
        return this;
    }

    public EsQueryWrapper<T> lte(Func<T, ?> field, String text, LinkType linkType,boolean condition) {
        if (condition) {
            Range range = new Range(RangeType.LTE, text);
            doIt(getColumn(field), range, QueryType.RANGE, linkType);
        }
        return this;
    }

    /**
     * @return java.lang.String
     * @Author huchenying
     * @Description 通过lambda获取列名
     * @Date 2021/8/23 10:39
     * @Param [field]
     **/
    private String getColumn(Func<T, ?> field) {
        return EsLambdaUtils.getColumn(EsLambdaUtils.resolve(field), true);
    }

    /**
     * @return com.hcy.hengtncommoninfodemo.esArsenal.wrapper.EsQueryWrapper<T>
     * @Author huchenying
     * @Description 设置分页
     * @Date 2021/8/23 10:58
     * @Param [pageNo, pageSize]
     **/
    public EsQueryWrapper<T> page(int pageNo, int pageSize) {
        setFrom(pageNo);
        setSize(pageSize);
        return this;
    }

}
