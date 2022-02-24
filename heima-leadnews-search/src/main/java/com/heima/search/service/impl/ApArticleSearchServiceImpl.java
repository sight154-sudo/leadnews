package com.heima.search.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.search.service.ApArticleSearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: tang
 * @date: Create in 17:25 2021/9/14
 * @description:
 */
@Service
@Slf4j
public class ApArticleSearchServiceImpl implements ApArticleSearchService {

    @Autowired
    private RestHighLevelClient client;
    /**
     * ES文章分页搜索
     * @param dto
     * @return
     */
    @Override
    public ResponseResult search(UserSearchDto dto) {
        //校验参数
        if(!ObjectUtil.isAllNotEmpty(dto)){
            return ResponseResult.setAppHttpCodeEnum(AppHttpCodeEnum.PARAM_INVALID);
        }

        //只有在首页查询时保存  开辟新线程保存用户搜索记录

        if(dto.getFromIndex() == 0){

        }
        //创建查询对象
        SearchRequest searchRequest = new SearchRequest("app_info_article");
        //构建查询条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //创建bool查询条件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //添加条件
        //根据关键词分词查询
        QueryStringQueryBuilder queryStringQueryBuilder = QueryBuilders.queryStringQuery(dto.getSearchWords()).field("title").field("content").defaultOperator(Operator.OR);
        boolQueryBuilder.must(queryStringQueryBuilder);
        RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery("publishTime").lt(dto.getMinBehotTime());
        boolQueryBuilder.filter(rangeQueryBuilder);
        //添加到构建器中
        sourceBuilder.query(boolQueryBuilder);

        //指定排序规则
        sourceBuilder.sort("publishTime", SortOrder.DESC);
        //设置分页
        sourceBuilder.from(0);
        sourceBuilder.size(dto.getPageSize());
        //添加到查询对象中
        searchRequest.source(sourceBuilder);
        //设置搜索条件
        SearchResponse response = null;
        try {
            //执行搜索
            response = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("查询出错!! 关键字为{}",dto.getSearchWords(),e);
            e.printStackTrace();
        }
        SearchHit[] hits = response.getHits().getHits();
        //封装结果并返回
        List<Map> collect = Arrays.stream(hits).map(hit -> {
            Map map = JSON.parseObject(hit.getSourceAsString(), Map.class);
            map.put("id",hit.getId());
            return map;
        }).collect(Collectors.toList());
        return ResponseResult.okResult(collect);
    }
}
