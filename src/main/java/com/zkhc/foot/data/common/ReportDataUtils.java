package com.zkhc.foot.data.common;

import com.zkhc.foot.data.config.constants.SystemConstants;
import com.zkhc.foot.data.modules.entity.Report;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * @author 武海升
 * @date 2018/12/8 16:49
 */
public class ReportDataUtils {

    public static void downloadAndWriteFile(String reportDataFileName, List<Report> reportList) {
        for (Report report : reportList) {
            downloadDataReport(reportDataFileName,report.getUniqueCode(),report.getLeftDataUrl(),report.getRightDataUrl());
            writeDataReport(reportDataFileName,report.getUniqueCode(),report.getDataContent());
        }
    }

    private static void writeDataReport(String reportDataFileName,String uniqueCode, String dataContent) {
        try {
            FileUtils.writeStringToFile(new File(reportDataFileName + uniqueCode +".json"), dataContent, "UTF-8",true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void downloadDataReport(String reportDataFileName,String uniqueCode, String leftDataUrl, String rightDataUrl) {
        String httpUrlPath;
        String filePath;
        //下载 leftDataUrl
        httpUrlPath = SystemConstants.downloadBaseUrl + leftDataUrl;
        filePath = reportDataFileName + uniqueCode + "_leftData"+".dat";
        ossDownloadData(httpUrlPath,filePath);
        //下载 rightDataUrl
        httpUrlPath = SystemConstants.downloadBaseUrl + rightDataUrl;
        filePath = reportDataFileName + uniqueCode + "_rightData"+".dat";
        ossDownloadData(httpUrlPath,filePath);
    }

    private static void ossDownloadData(String httpUrlPath, String filePath) {
        try {
            URL httpUrl = new URL(httpUrlPath);
            FileUtils.copyURLToFile(httpUrl, new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean createDirs(String path){
        try {
            File file = new File(path);
            if(!file.exists()&&!file.isDirectory()) {
                return file.mkdirs();
            }else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
