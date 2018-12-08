package com.zkhc.foot.data.modules.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author 武海升
 * @date 2018/12/6 10:51
 */
@Data
public class Report {

    private Integer id;
    private String patientCode;
    private String patientName;
    private String uniqueCode;
    private String qrCode;
    private String leftDataUrl;
    private String rightDataUrl;
    private String dataContent;
    private String hospitalName;
    private Date createTime;

}
