package com.hcy.esArsenal.wrapper;

import com.hcy.esArsenal.enums.LinkType;
import com.hcy.esArsenal.enums.QueryType;
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
     * @Description 对单字段模糊查询，可以多个，是 or 关系
     * @Date 2021/8/23 10:30
     * @Param [field, value]
     * @return com.hcy.hengtncommoninfodemo.esArsenal.wrapper.EsQueryWrapper<T>
     **/
    public EsQueryWrapper<T> query(Func<T, ?> field, String value){
        //不仅要拿到列，还要拿到表的类型，从而知道是哪个索引
        doIt(getColumn(field),value, QueryType.MATCH, LinkType.OR);
        return this;
    }

    /**
     * @Author huchenying
     * @Description 全文查询，可以多个，是 or 关系
     * @Date 2021/8/23 10:31
     * @Param [column, text]
     * @return com.hcy.hengtncommoninfodemo.esArsenal.wrapper.EsQueryWrapper<T>
     **/
    public EsQueryWrapper<T> queryAll(Func<T, ?> field, String text){
        //不仅要拿到列，还要拿到表的类型，从而知道是哪个索引
        doIt(getColumn(field),text, QueryType.MATCHALL,LinkType.OR);
        return this;
    }

    /**
     * @Author huchenying
     * @Description 字段高亮，只能高亮一个字段，多个不常用就算了
     * @Date 2021/8/23 10:32
     * @Param [column]
     * @return com.hcy.hengtncommoninfodemo.esArsenal.wrapper.EsQueryWrapper<T>
     **/
    public EsQueryWrapper<T> highlight(Func<T, ?> field) {
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(EsLambdaUtils.getColumn(EsLambdaUtils.resolve(field), true));
        setHighlight(highlightBuilder);
        return this;
    }

    /**
     * @Author huchenying
     * @Description 精确查询，多个是and关系
     * @Date 2021/8/23 10:41
     * @Param [field, text]
     * @return com.hcy.hengtncommoninfodemo.esArsenal.wrapper.EsQueryWrapper<T>
     **/
    public EsQueryWrapper<T> eq(Func<T, ?> field, String text){
        doIt(getColumn(field),text,QueryType.TERM, LinkType.AND);
        return this;
    }

    /**
     * @Author huchenying
     * @Description 通过lambda获取列名
     * @Date 2021/8/23 10:39
     * @Param [field]
     * @return java.lang.String
     **/
    private String getColumn(Func<T, ?> field){
        return  EsLambdaUtils.getColumn(EsLambdaUtils.resolve(field), true);
    }

    /**
     * @Author huchenying
     * @Description 设置分页
     * @Date 2021/8/23 10:58
     * @Param [pageNo, pageSize]
     * @return com.hcy.hengtncommoninfodemo.esArsenal.wrapper.EsQueryWrapper<T>
     **/
    public EsQueryWrapper<T> page(int pageNo,int pageSize){
        setFrom(pageNo);
        setSize(pageSize);
        return this;
    }

}
