package com.zkhc.foot.data.modules.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * @author 武海升
 * @date 2018/12/15 11:05
 */
@AllArgsConstructor
@Builder
@Data
public class ZnkfHospital {

    private Integer id;
    private String hospitalName;
    private String hospitalType;
    private String hospitalLevel;
    private Integer weight;
    private String province;
    private String city;
    private String area;
    private String address;
    private String telephone;
    private String cover;
    private String remark;
    private Date createTime;
    private Date updateTime;

    public ZnkfHospital(){

    }

}
