package com.ityb.elasticsearch.demo.service;

import com.ityb.elasticsearch.demo.document.ReportDocument;

import java.util.List;

/**
 * Created by viruser on 2020/4/15.
 */
public interface ReportService {

    long insert(ReportDocument reportDocument);

    List<ReportDocument> queryReport(ReportDocument reportDocument);

    List<ReportDocument> searchReport(ReportDocument reportDocument);
}
