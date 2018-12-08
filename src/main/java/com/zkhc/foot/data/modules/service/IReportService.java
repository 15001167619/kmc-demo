package com.zkhc.foot.data.modules.service;

import com.baomidou.mybatisplus.service.IService;
import com.zkhc.foot.data.modules.entity.Report;

/**
 * @author 武海升
 * @date 2018/12/6 13:49
 */
public interface IReportService extends IService<Report> {

    /**
     * 生成报告数据
     * @author 武海升
     * @param args  筛选条件
     * @date 2018/12/8 16:49
     */
    void generateReportData(String args);


}
