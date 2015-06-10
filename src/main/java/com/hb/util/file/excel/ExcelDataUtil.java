/**  
 * @Title: ExcelDataUtil.java 
 * @Package com.hotent.core.excel.util 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author huangbing 
 * @date 2015年6月10日 上午11:30:02 
 * @version V1.0  
 */ 
package com.hb.util.file.excel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

/** 
 * @ClassName: ExcelDataUtil 
 * @Description: 针对 springmvc jsp 上传文件功能， 获取MultipartFile对象的  文件转为excel。
 * @author huangbing 
 * @date 2015年6月10日 上午11:30:02  
 */
public class ExcelDataUtil {
 
    public static String getFileName(MultipartFile file) {
        return (String) file.getOriginalFilename();
    }
 
    public static String[][] GetExcelData(MultipartFile file, int columnNumber)
            throws Exception {
        String[][] arr = null;
        Workbook wb = null;
 
        try {
            wb = new HSSFWorkbook(file.getInputStream());//2003
        } catch (Exception e) {
            wb = new XSSFWorkbook(file.getInputStream());//2007
        }
 
        int sheetNum = wb.getNumberOfSheets();
        for (int i = 0; i < sheetNum; i++) {
            Sheet childSheet = wb.getSheetAt(i);
            int rowNum = childSheet.getPhysicalNumberOfRows();
            for (int j = 0; j < rowNum; j++) {
                Row row = childSheet.getRow(j);
                int cellNum = columnNumber + 1;
                if (j == 0)
                    arr = new String[rowNum][cellNum];
                for (int k = 0; k < cellNum; k++) {
                    Cell cell = row.getCell(k);
                    arr[j][k] = parseExcel(cell);
                }
            }
        }
        return arr;
    }
     
     
    public static String getSheetName(MultipartFile file)
            throws Exception {
        Workbook wb = null;
        try {
            wb = new HSSFWorkbook(file.getInputStream());//2003
        } catch (Exception e) {
            wb = new XSSFWorkbook(file.getInputStream());//2007
        }
        return wb.getSheetName(0);
    }
     
     
     
     
    public static HashMap<String, HashMap<String, String[][]>> GetExcelMapData(
            MultipartFile file, int columnNumber) throws Exception {
        HashMap<String, HashMap<String, String[][]>> maps = new HashMap<String, HashMap<String, String[][]>>();
        HashMap<String, String[][]> mapda = new HashMap<String, String[][]>();
        String filename = file.getOriginalFilename();
        String ModelYear="";
        if(filename.lastIndexOf("_")!=-1){
            ModelYear = filename.substring((filename.lastIndexOf("_")-4),
                    (filename.lastIndexOf("_")));
        }
     
        String[][] arr = null;
        Workbook wb = null;
 
        try {
            wb = new HSSFWorkbook(file.getInputStream(), true);//2003
        } catch (Exception e) {
            wb = new XSSFWorkbook(file.getInputStream());//2007
        }
        int sheetNum = wb.getNumberOfSheets();
        for (int i = 0; i < sheetNum; i++) {
            Sheet childSheet = wb.getSheetAt(i);
            String ModelType = wb.getSheetName(i);
 
            int rowNum = childSheet.getPhysicalNumberOfRows();
 
            for (int j = 0; j < rowNum; j++) {
                Row row = childSheet.getRow(j);
                if(row!=null){
                    int cellNum = columnNumber + 1;
                    if (j == 0)
                        arr = new String[rowNum][cellNum];
                    for (int k = 0; k < cellNum; k++) {
                        Cell cell = row.getCell(k);
                        arr[j][k] = parseExcel(cell);
                    }
                }
                 
            }
            mapda.put(ModelType, arr);
 
        }
        maps.put(ModelYear, mapda);
 
        return maps;
    }
     
    /**
     * DecimalFormat to avoid the E-annotation for big double
     */
    private static DecimalFormat DECIMALFORMAT = new DecimalFormat("#.######");
    private static String parseExcel(Cell cell) {
        if (cell == null)
            return "";
        String result = new String();
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_NUMERIC:// 数字类型
            if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、 时间格式
                SimpleDateFormat sdf = null;
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = cell.getDateCellValue();
                result = sdf.format(date);
            } else {
                double va = cell.getNumericCellValue();
                if (va == (int) va)// 去掉数值类型后面的".0"
                    result = String.valueOf((int) va);
                else
//                    result = String.valueOf(va); //if the double value is too big, it will be displayed in E-notation
                    result = DECIMALFORMAT.format(va);
            }
            break;
            case HSSFCell.CELL_TYPE_FORMULA:
            // cell.getCellFormula();
            try {
                result = String.valueOf(cell.getNumericCellValue());
            } catch (IllegalStateException e) {
                result = String.valueOf(cell.getRichStringCellValue());
            }
            break;
        case HSSFCell.CELL_TYPE_STRING:// String类型
            result = cell.getRichStringCellValue().toString();
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            result = "";
        default:
            result = "";
            break;
        }
 
        return result;
    }
     
     
    public static void exportExcels(String fileName,String[][] excles, OutputStream os) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("new sheet");
        int rows = 0;
        for(String[] cells : excles){
            HSSFRow row = sheet.createRow(rows++);
            int cols = 0;
            for(String cellValue : cells){
                HSSFCell cell = row.createCell(cols++);
                cell.setCellValue(cellValue);
            }
        }
        wb.write(os);
    }
    public static  byte[] exportExcels(String[][] excles) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HSSFSheet sheet = wb.createSheet("new sheet");
        int rows = 0;
        for(String[] cells : excles){
            HSSFRow row = sheet.createRow(rows++);
            int cols = 0;
            for(String cellValue : cells){
                HSSFCell cell = row.createCell(cols++);
                cell.setCellValue(cellValue);
            }
        }
        wb.write(os);
        return os.toByteArray();
    }
}