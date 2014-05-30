package com.base.core.office;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
/** 
* @（#）:OutWord.java 
* @description: excel导出模块 
* @author:  hualidong 2011/03/21 
* @version:  
* @modify: 
* @Copyright:  黑龙江中科方德软件有限公司 
*/
public class OutExcel {
	public static HSSFWorkbook printExcel(String[] tmpContentCn,List<Object[]> dataList){
        HSSFWorkbook workbook = null;
        String[] titles_CN = tmpContentCn;
        try{
             //创建工作簿实例
             workbook = new HSSFWorkbook();
             //创建工作表实例
            HSSFSheet sheet = workbook.createSheet("sheet1");
             //设置列宽
             setSheetColumnWidth(titles_CN,sheet);
           //获取样式
             if(dataList != null){
                  //创建第一行标题
                   HSSFRow row = sheet.createRow((short)0);// 建立新行
                   sheet.createFreezePane(0, 1);
                   HSSFCell cell;
                   for(int i=0;i<titles_CN.length;i++){
                	   HSSFCellStyle style = workbook.createCellStyle();
                	   style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                	   HSSFFont font=workbook.createFont();
                       font.setFontHeightInPoints((short)10);
                       font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                       style.setFont(font);
	                   createCell(row, i, style, HSSFCell.CELL_TYPE_STRING,
	                          titles_CN[i]);
                   }
                   //给excel填充数据
                  for(int i=0;i<dataList.size();i++){
                	  row=sheet.createRow(i+1);
                	  HSSFCellStyle style = workbook.createCellStyle();
                	  style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
                	  HSSFFont font=workbook.createFont();
                	  font.setFontHeightInPoints((short)10);
                	  style.setFont(font);
                	  for(int j=0;j<dataList.get(i).length+1;j++){
                		  cell=row.createCell(j);
                		  if(j==0){
                			  cell.setCellValue(i+1);
                			  cell.setCellStyle(style);
                		  }else{
                			  cell.setCellValue((dataList.get(i)[j-1]==null ? "":dataList.get(i)[j-1])+""); 
                			  cell.setCellStyle(style);
                		  }
      				  }
                 }
          }else{
        	  HSSFCellStyle style = createTitleStyle(workbook);
              createCell(sheet.createRow(0), 0, style,HSSFCell.CELL_TYPE_STRING, "查无资料");
          }
     }catch(Exception e){
     }
    return workbook;
}

/**
 * 设置列宽
 */
@SuppressWarnings("deprecation")
private static void setSheetColumnWidth(String[] titles_CN,HSSFSheet sheet){
      // 根据你数据里面的记录有多少列，就设置多少列
     for(int i=0;i<titles_CN.length;i++){
             sheet.setColumnWidth((short)i, (short) 3000);
     }

}

/**
 * 设置excel的title样式 
 */
private static HSSFCellStyle createTitleStyle(HSSFWorkbook wb) {
     HSSFFont boldFont = wb.createFont();
     boldFont.setFontHeight((short) 200);
     HSSFCellStyle style = wb.createCellStyle();
     style.setFont(boldFont);
     style.setDataFormat(HSSFDataFormat.getBuiltinFormat("###,##0.00"));
     return style;  
}
  
/**
 * 创建Excel单元格    
 * @param row
 * @param column
 * @param style
 * @param cellType
 * @param value
 */
@SuppressWarnings("deprecation")
private static void createCell(HSSFRow row, int column, HSSFCellStyle style,int cellType,Object value) {
     HSSFCell cell = row.createCell((short) column);
     if (style != null) {
          cell.setCellStyle(style);
     }  
     switch(cellType){
          case HSSFCell.CELL_TYPE_BLANK: {} break;
          case HSSFCell.CELL_TYPE_STRING: {cell.setCellValue(value.toString()+"");} break;
          case HSSFCell.CELL_TYPE_NUMERIC: {
          cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
              cell.setCellValue(Double.parseDouble(value.toString()));}break;
          default: break;
    }  

}

/**
 * 写入输入流中
 * @param excelStream
 * @param workbook
 * @param fileName
 * @return
 */
public static InputStream exportExcel(InputStream excelStream, HSSFWorkbook workbook,String fileName){
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      try {
		workbook.write(baos);
		baos.flush();
		byte[] aa = baos.toByteArray();
		excelStream = new ByteArrayInputStream(aa, 0, aa.length);
		baos.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return excelStream;
}
}
