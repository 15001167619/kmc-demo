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
public class ApplicationTest {

    @Autowired
    private ZnkfHospitalMapper znkfHospitalMapper;

    private static String baseUrl = "https://db.yaozh.com/hmap?p=page&pageSize=20";
    private static String hospitalUrl = "https://db.yaozh.com";
    private static Integer page = 10;


    @Test
    public void initHospitalData() {
        for (int i = 1; i <= page; i++) {
            String pageUrl = baseUrl.replace("page", String.valueOf(i));
            Document doc = getDocumentInfo(pageUrl);
            List<ZnkfHospital> hospitalList = getHospitalList(doc);
            System.out.println(hospitalList);
            znkfHospitalMapper.insertHospitalBatch(hospitalList);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    private static Document getDocumentInfo(String pageUrl){
        Document doc = null;
        try {
            doc = Jsoup.connect(pageUrl)
                    .timeout(30000)
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

    private static List<ZnkfHospital> getHospitalList(Document doc){
        List<ZnkfHospital> hospitalList = new ArrayList<>();
        String hospitalName = null;
        String hospitalType = null;
        String hospitalLevel = null;
        String province = null;
        String city = null;
        String area = null;
        String address = null;
        String telephone = null;
        Elements table = doc.select("table");
        Elements trs = table.select("tr");
        for(int i = 0;i<trs.size();i++){
            if(i!=0){
                String hospitalInfoUrl = trs.get(i).select("th").select("a").first().attr("href");
                Document hospitalInfoDoc = getDocumentInfo(hospitalUrl+hospitalInfoUrl);
                Elements hospitalInfoTable = hospitalInfoDoc.select("table");
                Elements hospitalInfoTrs = hospitalInfoTable.select("tr");
                for (Element hospitalInfoTr : hospitalInfoTrs) {
                    if(hospitalInfoTr.select("th").text().equals("电话")){
                        telephone = hospitalInfoTr.select("td").text();
                        break;
                    }else {
                        telephone = null;
                    }
                }
                hospitalName = trs.get(i).select("th").text();
            }
            Elements tds = trs.get(i).select("td");
            if(tds.size()==7){
                hospitalType = tds.get(1).text();
                hospitalLevel = tds.get(0).text();
                province = tds.get(2).text();
                city = tds.get(3).text();
                area = tds.get(4).text();
                address = tds.get(6).text();
                ZnkfHospital hospital = ZnkfHospital.builder()
                        .hospitalName(hospitalName)
//                        .hospitalType(hospitalType)
//                        .hospitalLevel(hospitalLevel)
                        .province(province)
                        .city(city)
                        .area(area)
                        .address(address)
                        .telephone(telephone)
                        .build();
                hospitalList.add(hospital);
            }

        }
        return hospitalList;
    }


}
