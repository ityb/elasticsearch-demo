package com.ityb.elasticsearch.demo.repository;

import com.ityb.elasticsearch.demo.document.ReportDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by viruser on 2020/4/15.
 */
public interface ReportRepository extends ElasticsearchRepository<ReportDocument,Long>{

}
