package com.hcy.esArsenal.api;


import com.hcy.esArsenal.wrapper.EsQueryWrapper;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * @Author huchenying
 * @Description 门户类
 * @Date 2021/8/16
 **/
@Service
public class EsDoor {

    private static final Logger logger = LoggerFactory.getLogger(EsDoor.class);

    @Autowired
    private EsCoreOpt esCoreOpt;

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @Author huchenying
     * @Description 查询门户
     * @Date 2021/8/23 11:58
     * @Param [index=索引枚举, esQueryWrapper]
     **/
    public List<Map<String, Object>> query(String index, EsQueryWrapper esQueryWrapper) {
        List<Map<String, Object>> maps = Collections.emptyList();
        try {
            maps = esCoreOpt.searchListData(index, getSearchSource(esQueryWrapper), getHighlightField(esQueryWrapper));
            return maps;
        } catch (IOException e) {
            logger.error("es query error!", e);
            return maps;
        }
    }

    /**
     * @return java.util.List<org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder.Field>
     * @Author huchenying
     * @Description 获取高亮字段
     * @Date 2021/8/23 11:55
     * @Param [esQueryWrapper]
     **/
    public List<HighlightBuilder.Field> getHighlightField(EsQueryWrapper esQueryWrapper) {
        return esQueryWrapper.getSearchSource().highlighter().fields();
    }

    /**
     * @return org.elasticsearch.search.builder.SearchSourceBuilder
     * @Author huchenying
     * @Description 获取SearchSource
     * @Date 2021/8/23 11:55
     * @Param [esQueryWrapper]
     **/
    public SearchSourceBuilder getSearchSource(EsQueryWrapper esQueryWrapper) {
        return esQueryWrapper.getSearchSource();
    }

    /**
     * @return String 返回id
     * @Author huchenying
     * @Description 插入, 不指定主键
     * @Date 2021/8/23 16:53
     * @Param [object, index]
     **/
    public String insert(Object object, String index) {
        try {
            return esCoreOpt.addData(object, index);
        } catch (IOException e) {
            logger.error("es insert error!", e);
            return null;
        }
    }

    /**
     * @return String 返回id
     * @Author huchenying
     * @Description 插入, 指定主键
     * @Date 2021/8/23 16:54
     * @Param [object, index, id]
     **/
    public String insert(Object object, String index, String id) {
        try {
            return esCoreOpt.addData(object, index, id);
        } catch (IOException e) {
            logger.error("es insert error!", e);
            return null;
        }
    }

    /**
     * @return String 返回id
     * @Author huchenying
     * @Description 批量插入
     * @Date 2021/8/23 16:54
     * @Param [index, list]
     **/
    public boolean bulkInsert(String index, List<Object> list) {
        return esCoreOpt.bulkPost(index, list);
    }

    /**
     * @return java.lang.String 返回id
     * @Author huchenying
     * @Description 根据id删除记录
     * @Date 2021/8/23 17:00
     * @Param [index, id]
     **/
    public String delete(String index, String id) {
        try {
            return esCoreOpt.deleteDataById(index, id);
        } catch (IOException e) {
            logger.error("es delete error!" + e);
            return null;
        }
    }

    /**
     * @Author huchenying
     * @Description 更新，在es中只能覆盖
     * @Date 2021/8/23 17:02
     * @Param [index, id, object]
     * @return java.lang.String 返回id
     **/
    public String update(String index, String id, Object object) {
        try {
            return esCoreOpt.addData(object, index, id);
        } catch (IOException e) {
            logger.error("es update error!" + e);
            return null;
        }
    }

}
