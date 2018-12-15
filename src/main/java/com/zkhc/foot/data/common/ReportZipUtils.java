package com.zkhc.foot.data.common;

import java.io.*;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

/**
 * @author 武海升
 * @date 2018/12/15 9:18
 */
public class ReportZipUtils {

    private static int byteLength = 1024;

    /**
     * 文件路径和文件名
     * @param dir 文件路径
     * @param fileName 文件名
     */
    public static File zipFile(File dir, String fileName){
        File zip=new File(dir+".zip");
        try {
            ZipOutputStream zos;
            //最终用该输出流将内容输出到该zip文件中
            zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zip)));
            zos.setEncoding("UTF-8");
            compress(zos, dir, fileName);
            zos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return zip;
    }

    private static void compress(ZipOutputStream zos, File dir, String fileName) throws IOException {
        if (dir.isDirectory()) {
            // 压缩文件夹
            File[] fl = dir.listFiles();
            zos.putNextEntry(new ZipEntry(fileName + "/"));
            fileName = fileName.length() == 0 ? "" : fileName + "/";
            for (int i = 0; i < fl.length; i++) {
                compress(zos, fl[i], fileName + fl[i].getName());
            }
        }else {
            // 压缩文件
            zos.putNextEntry(new ZipEntry(fileName));
            FileInputStream fis = new FileInputStream(dir);
            inStream2outStream(fis, zos);
            fis.close();
            zos.closeEntry();
        }
    }

    private static void inStream2outStream(InputStream is, OutputStream os)throws IOException {
        BufferedInputStream bis = new BufferedInputStream(is);
        BufferedOutputStream bos = new BufferedOutputStream(os);
        int bytesRead = 0;
        for (byte[] buffer = new byte[byteLength]; ((bytesRead = bis.read(buffer, 0, byteLength)) != -1);) {
            bos.write(buffer, 0, bytesRead);
        }
        bos.flush();
    }

}