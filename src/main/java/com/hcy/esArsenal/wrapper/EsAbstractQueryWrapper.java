package com.hcy.esArsenal.wrapper;

import com.hcy.esArsenal.dto.Range;
import com.hcy.esArsenal.enums.AnalyzerType;
import com.hcy.esArsenal.enums.LinkType;
import com.hcy.esArsenal.enums.QueryType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author huchenying
 * @Description query父类
 * @Date 2021/8/17
 **/
public abstract class EsAbstractQueryWrapper {

    private static final Logger logger = LoggerFactory.getLogger(EsAbstractQueryWrapper.class);

    private final SearchSourceBuilder searchSourceBuilder;
    private final BoolQueryBuilder boolQueryBuilder;
    private AnalyzerType analyzer;

    public EsAbstractQueryWrapper() {
        searchSourceBuilder = SearchSourceBuilder.searchSource();
        //初始化分词器，后续可以把这个抽象出来,默认为ik_min_word分词
        logger.info("Init analyzer is ik_smart.");
        analyzer = AnalyzerType.IK_SMART;
        boolQueryBuilder = QueryBuilders.boolQuery();
    }

    /**
     * @return void
     * @Author huchenying
     * @Description 添加QueryBuilder到BoolQueryBuilder中
     * @Date 2021/8/23 10:48
     * @Param [name, text, type, linkType]
     **/
    public void doIt(String name, String text, QueryType type, LinkType linkType) {
        QueryBuilder queryBuilder = getQueryBuilder(type, name, text);
        merge(queryBuilder, linkType);
    }

    /**
     * @return void
     * @Author huchenying
     * @Description 添加QueryBuilder到BoolQueryBuilder中
     * @Date 2021/8/23 10:48
     * @Param [name, text, type, linkType]
     **/
    public void doIt(String name, Range range, QueryType type, LinkType linkType) {
        if (type == QueryType.RANGE) {
            QueryBuilder queryBuilder = rangeQuery(name, range);
            merge(queryBuilder, linkType);
        }
    }

    /**
     * @return void
     * @Author huchenying
     * @Description 合并查询
     * @Date 2021/9/8 14:09
     * @Param [queryBuilder, linkType]
     **/
    private void merge(QueryBuilder queryBuilder, LinkType linkType) {
        if (queryBuilder != null) {
            switch (linkType) {
                case OR:
                    boolQueryBuilder.should(queryBuilder);
                    break;
                case AND:
                    boolQueryBuilder.must(queryBuilder);
                    break;
                default:
                    logger.error("error LinkType!");
            }
        }
    }

    /**
     * @return org.elasticsearch.index.query.QueryBuilder
     * @Author huchenying
     * @Description 通过枚举类型获取QueryBuilder，普通的条件
     * @Date 2021/8/23 10:26
     * @Param [type, name, text]
     **/
    private QueryBuilder getQueryBuilder(QueryType type, String name, String text) {

        switch (type) {
            case MATCH:
                return matchQuery(name, text);
            case MATCHALL:
                return matchAllQuery(text);
            case TERM:
                return termQuery(name, text);
            default:
                return null;
        }
    }

    public QueryBuilder matchQuery(String name, String text) {
        return QueryBuilders.matchQuery(name, text).analyzer(analyzer.getName());
    }

    public QueryBuilder matchAllQuery(String text) {
        return QueryBuilders.matchAllQuery().queryName(text);
    }

    public QueryBuilder termQuery(String name, String text) {
        return QueryBuilders.termQuery(name, text);
    }

    /**
     * @return org.elasticsearch.index.query.QueryBuilder
     * @Author huchenying
     * @Description 范围查找
     * @Date 2021/9/8 14:10
     * @Param [type, name, range]
     **/
    public QueryBuilder rangeQuery(String name, Range range) {

        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(name);

        switch (range.getRangeType()) {
            case GT:
                rangeQueryBuilder.gt(range.getValue());
            case GTE:
                rangeQueryBuilder.gte(range.getValue());
            case LT:
                rangeQueryBuilder.lt(range.getValue());
            case LTE:
                rangeQueryBuilder.lte(range.getValue());
        }
        return QueryBuilders.rangeQuery(name);
    }


    /**
     * @return org.elasticsearch.search.builder.SearchSourceBuilder
     * @Author huchenying
     * @Description 获取SearchSourceBuilder
     * @Date 2021/8/23 10:26
     * @Param []
     **/
    public SearchSourceBuilder getSearchSource() {
        searchSourceBuilder.query(boolQueryBuilder);
        return searchSourceBuilder;
    }

    public void setAnalyzer(AnalyzerType analyzer) {
        this.analyzer = analyzer;
    }

    public void setHighlight(HighlightBuilder highlight) {
        if (searchSourceBuilder.highlighter() != null){
            highlight.fields().addAll(searchSourceBuilder.highlighter().fields());
        }
        searchSourceBuilder.highlighter(highlight);
    }

    public void setFrom(int from) {
        searchSourceBuilder.from(from);
    }

    public void setSize(int size) {
        searchSourceBuilder.size(size);
    }


}
