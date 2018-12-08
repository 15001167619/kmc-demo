package com.zkhc.foot.data;

import com.alibaba.fastjson.JSONObject;
import com.zkhc.foot.data.modules.service.IReportService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * @author 武海升
 * @date 2018/12/6 13:48
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FootDataApplicationTest {

    @Resource
    private IReportService reportService;

    @Test
    public void initReportData() {
        JSONObject jsonObject = new JSONObject();
        reportService.generateReportData(jsonObject.toJSONString());
    }

    @Test
    public void localDateTimeTest() {
        try {
            LocalDateTime of = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            System.out.println("当前日期----> "+of);
            of = LocalDateTime.of(LocalDate.of(2018,11,7), LocalTime.MIN);
            System.out.println("指定日期----> "+of);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
