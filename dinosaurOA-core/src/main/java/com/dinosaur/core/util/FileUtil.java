package com.dinosaur.core.util;

import com.dinosaur.core.context.ApplicationContextHolder;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件操作工具类，文件操作处理文件相关的操作，包括文件上传、下载，<br/>
 * word导出及导入，excel的导入及导出
 * @Author Alcott Hawk
 * @Date 1/30/2017
 */
public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    private static Workbook workbook;

    /**
     * 上传图片
     * @param multipartFile
     * @param subFolder 目标文件路径，请求路径为服务器相对路径入<b>/themes/default/static/image/</b>
     * @return
     * @throws IOException
     */
    public static String upload(MultipartFile multipartFile, String subFolder) throws IOException {
        subFolder = subFolder + multipartFile.getOriginalFilename();
        String filePath = ApplicationContextHolder.getServletContext().getRealPath("/")+subFolder;
        File file = new File(filePath);
        if (!file.exists()){
            file.mkdirs();
        }
        try {
            multipartFile.transferTo(file);
            return subFolder;
        } catch (IOException e) {
            logger.error("文件写入异常："+e.getMessage());
            throw new IOException(e.getMessage());
        }
    }

    /**
     * 读取excel的数据，excel格式如下：<br/>
     * <table>
     *     <tr>
     *         <td>id<td/>
     *         <td>名称<td/>
     *         <td>的话号码<td/>
     *         <td>地址<td/>
     *         <td>备注<td/>
     *     <tr/>
     *     <tr>
     *         <td>1<td/>
     *         <td>赵高<td/>
     *         <td>19999999999<td/>
     *         <td>中国<td/>
     *         <td>秦朝<td/>
     *     <tr/>
     *     <tr>
     *         <td>1<td/>
     *         <td>李斯<td/>
     *         <td>10000000000<td/>
     *         <td>中国<td/>
     *         <td>秦朝<td/>
     *     <tr/>
     * <table/>
     * @param file 需要读取的excel文件
     * @return 包行工作簿数据的map对象的集合
     * @throws IOException
     */
    public static List readerExcel(InputStream file) throws IOException {
        workbook = new HSSFWorkbook(new POIFSFileSystem(file));
        int sheetNumber = workbook.getNumberOfSheets();
        List content = null;
        if (sheetNumber > 0){
            for (int i = 0; i < sheetNumber; i++){
                content = new ArrayList();
                Sheet sheet = workbook.getSheetAt(i);
                int rowNum = sheet.getLastRowNum();
                Row rowTitle = sheet.getRow(0);
                int cellNum = rowTitle.getPhysicalNumberOfCells();
                Map<String, Object> data = null;
                for (int j = 1; j< rowNum; j++){
                    data = new HashMap();
                    int k = 0;
                    while (k < cellNum){
                        data.put(rowTitle.getCell(k).getStringCellValue(),sheet.getRow(j).getCell(k).getStringCellValue());
                        k++;
                    }
                    content.add(data);
                }
            }
            return content;
        } else {
            return new ArrayList();
        }
    }

    /**
     * 导出excel
     * @param data 包含数据的集合
     * @throws IOException
     */
    public static ByteArrayOutputStream exportExcel(List<Map<String, Object>> data) throws IOException {
        if (data.size()>0){
            workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet();
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            Row rowTitle = sheet.createRow(0);
            Map title = data.get(0);
            // TODO 多个工作簿的处理
            title.forEach((k,v)->{
                Cell cell = rowTitle.createCell(v.hashCode());
                cell.setCellValue(title.get(k).toString());
            });
            for (int i = 1; i < data.size(); i++){
                Row row = sheet.createRow(i);
                Map dataMap = data.get(i);
                dataMap.forEach((k,v)->{
                    Cell cell = row.createCell(v.hashCode());
                    cell.setCellValue(dataMap.get(k).toString());
                });
            }
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            workbook.close();
            return outputStream;
        } else {
            throw new NullPointerException("数据不能为空");
        }
    }

}
