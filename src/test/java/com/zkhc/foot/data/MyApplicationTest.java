package com.zkhc.foot.data;

import com.zkhc.foot.data.modules.dao.ZnkfHospitalMapper;
import com.zkhc.foot.data.modules.entity.ZnkfHospital;
import com.zkhc.foot.data.modules.service.ISpiderHospitalService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author 武海升
 * @date 2018/12/18 13:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MyApplicationTest {

    @Autowired
    private ISpiderHospitalService hospitalService;

    @Autowired
    private ZnkfHospitalMapper hospitalMapper;

    @Test
    public void initHospitalData() {
        hospitalService.spiderHospital();
    }




    @Test
    public void logTest() {
        hospitalMapper.insertHospital(ZnkfHospital.builder()
                .hospitalName("122")
                .hospitalType(0)
                .hospitalLevel(0)
                .build());
    }


}
