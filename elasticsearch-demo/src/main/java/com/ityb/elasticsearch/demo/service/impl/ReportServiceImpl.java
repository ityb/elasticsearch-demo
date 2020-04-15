package com.ityb.elasticsearch.demo.service.impl;

import com.ityb.elasticsearch.demo.document.ReportDocument;
import com.ityb.elasticsearch.demo.repository.ReportRepository;
import com.ityb.elasticsearch.demo.repository.ReportTemplateRepository;
import com.ityb.elasticsearch.demo.service.ReportService;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viruser on 2020/4/15.
 */
@Service
public class ReportServiceImpl implements ReportService{

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportTemplateRepository reportTemplateRepository;

    @Override
    public long insert(ReportDocument reportDocument) {
        ReportDocument document = reportRepository.save(reportDocument);
        return document.getId();
    }

    @Override
    public List<ReportDocument> queryReport(ReportDocument reportDocument) {
        QueryBuilder queryBuilder=new MatchAllQueryBuilder();
        if(!StringUtils.isEmpty(reportDocument.getReportTitle())){
            queryBuilder= new MatchPhraseQueryBuilder("reportTitle",reportDocument.getReportTitle());
        }
        Iterable<ReportDocument> search = reportRepository.search(queryBuilder);
        List<ReportDocument> resultList=new ArrayList<>();
        search.forEach(e->resultList.add(e));
        return resultList;
    }

    @Override
    public List<ReportDocument> searchReport(ReportDocument reportDocument) {
        return this.reportTemplateRepository.searchReport(reportDocument);
    }
}
