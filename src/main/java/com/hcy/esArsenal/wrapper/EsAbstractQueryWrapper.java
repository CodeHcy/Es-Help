package com.hcy.esArsenal.wrapper;

import com.hcy.esArsenal.enums.AnalyzerType;
import com.hcy.esArsenal.enums.LinkType;
import com.hcy.esArsenal.enums.QueryType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author huchenying
 * @Description query父类
 * @Date 2021/8/17
 **/
public abstract class EsAbstractQueryWrapper {

    private static final Logger logger = LoggerFactory.getLogger(EsAbstractQueryWrapper.class);

    public SearchSourceBuilder searchSourceBuilder;
    private BoolQueryBuilder boolQueryBuilder;
    public AnalyzerType analyzer;

    public EsAbstractQueryWrapper() {
        searchSourceBuilder = SearchSourceBuilder.searchSource();
        //初始化分词器，后续可以把这个抽象出来,默认为ik_min_word分词
        logger.info("Init analyzer is ik_smart.");
        analyzer = AnalyzerType.IK_SMART;
        boolQueryBuilder = QueryBuilders.boolQuery();
    }

    /**
     * @Author huchenying
     * @Description 添加QueryBuilder到BoolQueryBuilder中
     * @Date 2021/8/23 10:48
     * @Param [name, text, type, linkType]
     * @return void
     **/
    public void doIt(String name, String text, QueryType type, LinkType linkType) {
        QueryBuilder queryBuilder = getQueryBuilder(type, name, text);
        if (queryBuilder != null) {
            logger.error("错误的查询类型.");
            switch (linkType){
                case OR:
                    boolQueryBuilder.should(queryBuilder);break;
                case AND:
                    boolQueryBuilder.must(queryBuilder);break;
                default:
                    logger.error("error LinkType!");
            }
        }
    }

    /**
     * @Author huchenying
     * @Description 通过枚举类型获取QueryBuilder
     * @Date 2021/8/23 10:26
     * @Param [type, name, text]
     * @return org.elasticsearch.index.query.QueryBuilder
     **/
    private QueryBuilder getQueryBuilder(QueryType type, String name, String text) {

        switch (type) {
            case MATCH:
                return QueryBuilders.matchQuery(name, text).analyzer(analyzer.getName());
            case MATCHALL:
                return QueryBuilders.matchAllQuery().queryName(text);
            case TERM:
                return QueryBuilders.termQuery(name, text);
            default:
                return null;
        }
    }

    /**
     * @Author huchenying
     * @Description 获取SearchSourceBuilder
     * @Date 2021/8/23 10:26
     * @Param []
     * @return org.elasticsearch.search.builder.SearchSourceBuilder
     **/
    public SearchSourceBuilder getSearchSource() {
        searchSourceBuilder.query(boolQueryBuilder);
        return searchSourceBuilder;
    }

    public void setAnalyzer(AnalyzerType analyzer){
        this.analyzer = analyzer;
    }

    public void setHighlight(HighlightBuilder highlight){
        searchSourceBuilder.highlighter(highlight);
    }

    public void setFrom(int from){
        searchSourceBuilder.from(from);
    }

    public void setSize(int size){
        searchSourceBuilder.size(size);
    }


}
