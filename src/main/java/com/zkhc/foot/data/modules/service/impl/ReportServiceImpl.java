package com.zkhc.foot.data.modules.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.zkhc.foot.data.common.ReportDataUtils;
import com.zkhc.foot.data.config.constants.SystemConstants;
import com.zkhc.foot.data.modules.dao.ReportMapper;
import com.zkhc.foot.data.modules.entity.Report;
import com.zkhc.foot.data.modules.service.IReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 武海升
 * @date 2018/12/7 10:04
 */
@Slf4j
@Service
@Transactional(rollbackFor=Exception.class)
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements IReportService {

    private final ReportMapper reportMapper;

    @Autowired
    public ReportServiceImpl(ReportMapper reportMapper) {
        this.reportMapper = reportMapper;
    }



    @Override
    public void generateReportData(String args) {
        boolean flag;
        try {
            log.info("---------------start----------------");
            JSONObject jsonObject = JSONObject.parseObject(args);
            EntityWrapper<Report> ew = new EntityWrapper(new Report());
            ew.between("create_time", LocalDateTime.of(LocalDate.now(), LocalTime.MIN), LocalDateTime.of(LocalDate.now(), LocalTime.MAX));
            List<Report> originalReportList = reportMapper.selectList(ew);
            //初始化根目录
            String reportDataBaseFileName = "数据报告采集_"+LocalDateTime.of(LocalDate.now(), LocalTime.MIN).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            reportDataBaseFileName = SystemConstants.basePath + reportDataBaseFileName;
            final String baseDirPath = reportDataBaseFileName + "//";
            flag = ReportDataUtils.createDirs(baseDirPath);
            if(flag){
                //按照患者编码分类
                Map<String, List<Report>> mapReport = originalReportList.stream()
                        .collect(
                                Collectors.groupingBy(Report::getPatientCode)
                        );
                //遍历每一组患者报告,并依照患者名称初始化报告目录
                mapReport.forEach((k, v) -> {
                    String reportDataFileName;
                    try {
                        reportDataFileName = baseDirPath + v.get(0).getPatientName()+"//";
                        if(ReportDataUtils.createDirs(reportDataFileName)){
                            //下载报告数据文件以及将报告数据流写入JSON文件中
                            ReportDataUtils.downloadAndWriteFile(reportDataFileName,v);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                log.info("---------------end----------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
