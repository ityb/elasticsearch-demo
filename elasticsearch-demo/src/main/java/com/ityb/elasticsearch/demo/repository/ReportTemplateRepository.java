package com.ityb.elasticsearch.demo.repository;

import com.ityb.elasticsearch.demo.document.ReportDocument;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by viruser on 2020/4/15.
 */
@Repository
public class ReportTemplateRepository {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    public List<ReportDocument> searchReport(ReportDocument reportDocument) {

        //设置排序
        Sort sort = new Sort(Sort.Direction.DESC, "id");//以id值为准 降序排列，ASC为升序

        //设置分页
        Pageable pageable = PageRequest.of(0, 10, sort);

        //设置筛选条件
        MatchPhraseQueryBuilder reportTitlePhraseQuery = QueryBuilders.matchPhraseQuery("reportTitle", reportDocument.getReportTitle());

        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("id", reportDocument.getId());

        //设置高亮字段
        HighlightBuilder.Field HighlightBuilderField = new HighlightBuilder.Field("reportTitle")
                .preTags("<font style='color:red'>")
                .postTags("</font>");

        //组装搜索器
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(reportTitlePhraseQuery)
                .withFilter(termQueryBuilder)
                .withHighlightFields(HighlightBuilderField)
                .withPageable(pageable)
                .build();

        //分页查询
        AggregatedPage<ReportDocument> aggregatedPage = this.elasticsearchTemplate.queryForPage(searchQuery, ReportDocument.class, new SearchResultMapper() {
            //分页结果集查询
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                List<ReportDocument> resultList = new ArrayList<>();
                SearchHits hits = searchResponse.getHits();
                for (SearchHit searchHit : hits) {
                    if (hits.getHits().length <= 0) {
                        return null;
                    }
                    ReportDocument report = new ReportDocument();
                    String highLightMessage = searchHit.getHighlightFields().get("reportTitle").fragments()[0].toString();
                    report.setId(Long.parseLong(searchHit.getId()));
                    Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
                    sourceAsMap.forEach((key,value)->{
                        if(key.equals("reportTitle")){
                            report.setReportTitle(highLightMessage);
                        }else if(key.equals("reportContent")){
                            report.setReportContent(String.valueOf(value));
                        }
                    });
                    resultList.add(report);
                }
                return new AggregatedPageImpl<>((List<T>) resultList,pageable,hits.getTotalHits());
            }
        });
        List<ReportDocument> content = aggregatedPage.getContent();
        return content;

    }
}
