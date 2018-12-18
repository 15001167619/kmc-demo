package com.zkhc.foot.data;

import com.alibaba.fastjson.JSONObject;
import com.zkhc.foot.data.modules.dao.ZnkfHospitalMapper;
import com.zkhc.foot.data.modules.entity.ZnkfHospital;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 武海升
 * @date 2018/12/18 9:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class InsertApplicationTest {

    @Autowired
    private ZnkfHospitalMapper znkfHospitalMapper;


    @Test
    public void initReportData() {
        znkfHospitalMapper.insertHospitalBatch(getHospitalList());
    }

    private List<ZnkfHospital> getHospitalList(){
        List<ZnkfHospital> hospitalList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
        }
        return hospitalList;
    }


}
