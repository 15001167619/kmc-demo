package com.zkhc.foot.data;

import com.zkhc.foot.data.modules.dao.ZnkfHospitalMapper;
import com.zkhc.foot.data.modules.entity.ZnkfHospital;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 武海升
 * @date 2018/12/18 13:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class MyApplicationTest {

    @Autowired
    private ZnkfHospitalMapper znkfHospitalMapper;

    private static String hospitalUrl = "https://db.yaozh.com/hmap/hospitalId.html";
    private static Integer pageSize = 81174;


    @Test
    public void initHospitalData() {
        for (int i = 515; i <= pageSize; i++) {
            String pageUrl = hospitalUrl.replace("hospitalId", String.valueOf(i));
            Document doc = getDocumentInfo(pageUrl);
            List<ZnkfHospital> hospitalList = getHospitalList(doc);
            if(hospitalList!=null && hospitalList.size()!=0){
               znkfHospitalMapper.insertHospitalBatch(hospitalList);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {

                }
            }

        }
    }


    private static Document getDocumentInfo(String pageUrl){
        Document doc = null;
        try {
            doc = Jsoup.connect(pageUrl)
                    .timeout(300000)
                    .validateTLSCertificates(false)
                    .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                    .header("Accept-Encoding", "gzip, deflate, sdch")
                    .header("Accept-Language", "zh-CN,zh;q=0.8")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    private static List<ZnkfHospital> getHospitalList(Document hospitalInfoDoc){
        List<ZnkfHospital> hospitalList = new ArrayList<>();
        String hospitalName = null;
        String hospitalType = null;
        String hospitalLevel = null;
        String province = null;
        String city = null;
        String area = null;
        String address = null;
        String telephone = null;

        Elements hospitalInfoTable = hospitalInfoDoc.select("table");
        Elements hospitalInfoTrs = hospitalInfoTable.select("tr");
        for (Element hospitalInfoTr : hospitalInfoTrs) {
            String thName = hospitalInfoTr.select("th").text();
            if("医院名称".equals(thName.trim())){
                hospitalName = hospitalInfoTr.select("td").text();
            }else if("医院等级".equals(thName.trim())){
                hospitalLevel = hospitalInfoTr.select("td").text();
            }else if("医院类型".equals(thName.trim())){
                hospitalType = hospitalInfoTr.select("td").text();
            }else if("省".equals(thName.trim())){
                province = hospitalInfoTr.select("td").text();
            }else if("市".equals(thName.trim())){
                city = hospitalInfoTr.select("td").text();
            }else if("县".equals(thName.trim())){
                area = hospitalInfoTr.select("td").text();
            }else if("医院地址".equals(thName.trim())){
                address = hospitalInfoTr.select("td").text();
            }else if("电话".equals(thName.trim())){
                telephone = hospitalInfoTr.select("td").text();
            }
        }
        if(hospitalName!=null){
            ZnkfHospital hospital = ZnkfHospital.builder()
                    .hospitalName(hospitalName)
                    .hospitalType(getType(hospitalType))
                    .hospitalLevel(getLevel(hospitalLevel))
                    .province(province)
                    .city(city)
                    .area(area)
                    .address(address)
                    .telephone(telephone)
                    .build();
            hospitalList.add(hospital);
        }

        return hospitalList;
    }

    private static Integer getType(String hospitalType){
        Integer result = 0;
        if(hospitalType==null || "".equals(hospitalType.trim())){
            return result;
        }
        if("专科医院".equals(hospitalType.trim())){
            result = 1;
        }else if("综合医院".equals(hospitalType.trim())){
            result = 2;
        }else if("中医医院".equals(hospitalType.trim())){
            result = 3;
        }else if("妇幼保健院".equals(hospitalType.trim())){
            result = 4;
        }else if("中西医结合医院".equals(hospitalType.trim())){
            result = 5;
        }else if("民族医院".equals(hospitalType.trim())){
            result = 6;
        }else if(hospitalType.trim().contains("专科疾病防治院")){
            result = 7;
        }else if("护理院".equals(hospitalType.trim())){
            result = 8;
        }else if("疗养院".equals(hospitalType.trim())){
            result = 9;
        }else if("社区卫生服务中心".equals(hospitalType.trim())){
            result = 10;
        }else if("乡镇卫生院".equals(hospitalType.trim())){
            result = 11;
        }else if("疾病预防控制中心".equals(hospitalType.trim())){
            result = 12;
        }else if("计划生育服务中心".equals(hospitalType.trim())){
            result = 13;
        }else if("问诊部".equals(hospitalType.trim())){
            result = 14;
        }else if("诊所".equals(hospitalType.trim())){
            result = 15;
        }
        return result;
    }

    private static Integer getLevel(String hospitalLevel){
        Integer result = 0;
        if(hospitalLevel==null || "".equals(hospitalLevel.trim())){
            return result;
        }
        if("三级甲等".equals(hospitalLevel.trim())){
            result = 1;
        }else if("三级乙等".equals(hospitalLevel.trim())){
            result = 2;
        }else if("三级未定".equals(hospitalLevel.trim())){
            result = 3;
        }else if("二级甲等".equals(hospitalLevel.trim())){
            result = 4;
        }else if("二级乙等".equals(hospitalLevel.trim())){
            result = 5;
        }else if("二级未定".equals(hospitalLevel.trim())){
            result = 6;
        }else if("一级甲等".equals(hospitalLevel.trim())){
            result = 7;
        }else if("一级乙等".equals(hospitalLevel.trim())){
            result = 8;
        }else if("一级未定".equals(hospitalLevel.trim())){
            result = 9;
        }
        return result;
    }


}
