package com.base.core.utilities;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;

/**
 * @（#）:OutWord.java
 * @description: word导出模块
 */
public class OutWord
{
	private static Document document;

	public static InputStream createDocContext(InputStream excelStream,String fileNames,Integer colmun,String[] tableheader,List<Object[]> dataList)
	{
		// 设置纸张大小   
		document=new Document(PageSize.A4);
		ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
		// 建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中   
		RtfWriter2.getInstance(document,outputStream);
		document.open();
		try
		{
			Paragraph title=new Paragraph(new String(fileNames.getBytes("ISO-8859-1"),"gbk")+"\n\n");
			Font titleFont=new Font(BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED),15,Element.ALIGN_CENTER);
			// 设置标题格式对齐方式   
			title.setAlignment(Element.ALIGN_CENTER);
			title.setFont(titleFont);
			document.add(title);
			// 设置 Table 表格   
			Table aTable=new Table(colmun);
			int[] width=new int[colmun];
			for(Integer i=0;i<colmun;i++)
				width[i]=colmun/100;
			aTable.setWidths(width);//设置每列所占比例   
			aTable.setWidth(90); // 占页面宽度 90%   

			aTable.setAutoFillEmptyCells(true); //自动填满   
			aTable.setBorderWidth(1); //边框宽度   
			aTable.setBorderColor(new Color(0,0,0)); //边框颜色   
			aTable.setBorder(0);//边框   

			//设置表头   
			/**
			 * cell.setHeader(true);是将该单元格作为表头信息显示； cell.setColspan(3);指定了该单元格占3列； 为表格添加表头信息时，要注意的是一旦表头信息添加完了之后， 必须调用 endHeaders()方法，否则当表格跨页后，表头信息不会再显示
			 */
			if(tableheader.length!=0)
			{
				for(Integer i=0;i<tableheader.length;i++)
				{
					Font fontChinese=new Font(BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED),10,Font.BOLD);
					Cell headercell=new Cell(new Phrase(tableheader[i],fontChinese));
					headercell.setVerticalAlignment(Element.ALIGN_CENTER);
					headercell.setHorizontalAlignment(Element.ALIGN_CENTER);
					aTable.addCell(headercell);
				}
			}

			aTable.endHeaders();
			if(dataList!=null)
			{
				for(Integer i=0;i<dataList.size();i++)
				{
					for(int j=0;j<dataList.get(i).length+1;j++)
					{
						Font fontChinese=new Font(BaseFont.createFont("STSongStd-Light","UniGB-UCS2-H",BaseFont.NOT_EMBEDDED),10);
						Cell headercell;
						if(j==0)
						{
							headercell=new Cell(new Phrase((i+1)+"",fontChinese));
							headercell.setVerticalAlignment(Element.ALIGN_CENTER);
							headercell.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						else
						{
							headercell=new Cell(new Phrase((dataList.get(i)[j-1]==null?"":dataList.get(i)[j-1])+"",fontChinese));
							headercell.setVerticalAlignment(Element.ALIGN_CENTER);
							headercell.setHorizontalAlignment(Element.ALIGN_CENTER);
						}
						aTable.addCell(headercell);
					}
				}
			}
			document.add(aTable);
			document.close();

			outputStream.flush();
			byte[] aa=outputStream.toByteArray();
			excelStream=new ByteArrayInputStream(aa,0,aa.length);
			outputStream.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return excelStream;
	}
}
