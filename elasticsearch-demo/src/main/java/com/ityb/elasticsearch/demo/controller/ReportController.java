package com.ityb.elasticsearch.demo.controller;

import com.ityb.elasticsearch.demo.document.ReportDocument;
import com.ityb.elasticsearch.demo.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by viruser on 2020/4/15.
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @RequestMapping("/insert")
    public long insert(ReportDocument reportDocument){
        return this.reportService.insert(reportDocument);
    }

    @RequestMapping("/queryReport")
    public List<ReportDocument> queryReport(ReportDocument reportDocument){
        return this.reportService.queryReport(reportDocument);
    }

    @RequestMapping("/searchReport")
    public List<ReportDocument> searchReport(ReportDocument reportDocument){
        return this.reportService.searchReport(reportDocument);
    }
}
