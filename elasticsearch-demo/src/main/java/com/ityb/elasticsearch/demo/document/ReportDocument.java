package com.ityb.elasticsearch.demo.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * Created by viruser on 2020/4/15.
 */
@Document(indexName="report_index",type = "report")
@Data
public class ReportDocument implements Serializable{

    @Id
    private Long id;

    @Field(searchAnalyzer="ik_smart",analyzer="ik_max_word")
    private String reportTitle;

    @Field(searchAnalyzer="ik_smart",analyzer="ik_max_word")
    private String reportContent;

}
