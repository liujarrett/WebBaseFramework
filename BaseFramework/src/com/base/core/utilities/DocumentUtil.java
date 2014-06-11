package com.base.core.utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;


/**
 * 生成表单工具类
 */
public class DocumentUtil  {

	private final static int fontFlagTitle = 1;// 标题字体
	private final static int fontFlagContext = 2;// 正文字体
	private final static int fontFlagContextMin = 3;// 小字体
	private final static int fontFlagContextUL = 4;// 下划线字体
	private final static int fontFlagTitle22 = 22;// 下划线字体
	private final static int fontFlagTitle36 = 36;
	private final static BaseColor bgcolor = new BaseColor(255, 255, 255); // 底色白色

	/*
	 * 批量打印
	 * 
	 * @param files 要合并的文件数组
	 * 
	 * @param newfile 合并后产生的新文件
	 * 
	 * @return boolean 成功返回true 失败返回false
	 */
	public static boolean batPrint(String newfile, String... files) {
		boolean retValue = false;
		Document document = null;
		try {
			document = new Document(new PdfReader(files[0]).getPageSize(1));
			PdfCopy copy = new PdfCopy(document, new FileOutputStream(newfile));
			document.open();
			for (int i = 0; i < files.length; i++) {
				PdfReader reader = new PdfReader(files[i]);
				int n = reader.getNumberOfPages();
				for (int j = 1; j <= n; j++) {
					document.newPage();
					PdfImportedPage page = copy.getImportedPage(reader, j);
					copy.addPage(page);
				}
			}
			retValue = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			document.close();
		}
		for (String f : files) {
			File ioFile = new File(f);
			ioFile.delete();
		}
		return retValue;
	}

	
	/**
	 * 抽样取证凭证
	 * 调用
	 * @param pdfPath
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static Document cyqzpzDoc(String pdfPath, String[][] context) {
		Document document = new Document(PageSize.B5, 0f, 0f, 20f, 10f); // 创建PDF文档对象
		try {
			PdfWriter.getInstance(document, new FileOutputStream(pdfPath));// 创建PDF文档
			document.open(); // 打开PDF文档
			PdfPTable table = null;
			table = initTable(24f, 19f, 19f, 19f, 19f);
			table.addCell(createCell("抽样取证凭证", null, createFont(fontFlagTitle),
					5, null, 50, 0, Element.ALIGN_CENTER, Element.ALIGN_MIDDLE,
					Rectangle.NO_BORDER, bgcolor));

			table.addCell(createCell("当事人：", null, createFont(fontFlagContext),
					null, null, 18, 0, Element.ALIGN_LEFT,
					Element.ALIGN_MIDDLE, Rectangle.NO_BORDER, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					4, null, 18, 0, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE,
					Rectangle.BOTTOM, bgcolor));

			table.addCell(createCell("时  间：", null,
					createFont(fontFlagContext), null, null, 18, 0,
					Element.ALIGN_LEFT, Element.ALIGN_MIDDLE,
					Rectangle.NO_BORDER, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					4, null, 18, 0, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE,
					Rectangle.BOTTOM, bgcolor));

			table.addCell(createCell("地  点：", null,
					createFont(fontFlagContext), null, null, 18, 0,
					Element.ALIGN_LEFT, Element.ALIGN_MIDDLE,
					Rectangle.NO_BORDER, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					4, null, 18, 0, Element.ALIGN_LEFT, Element.ALIGN_MIDDLE,
					Rectangle.BOTTOM, bgcolor));

			table.addCell(createCell("\t\t因你（公司）涉嫌xxxxx一案，本机关需对你（公司）下列物品抽样取证。",
					null, createFont(fontFlagContext), 5, null, 18, 0,
					Element.ALIGN_LEFT, Element.ALIGN_MIDDLE,
					Rectangle.NO_BORDER, bgcolor));

			table.addCell(createCell("物品名称", null, createFont(fontFlagContext),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));

			table.addCell(createCell("商标", null, createFont(fontFlagContext),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));

			table.addCell(createCell("生产单位", null, createFont(fontFlagContext),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));

			table.addCell(createCell("许可号", null, createFont(fontFlagContext),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));

			table.addCell(createCell("生产日期（批号）", null,
					createFont(fontFlagContext), null, null, 36, 0,
					Element.ALIGN_CENTER, Element.ALIGN_MIDDLE, Rectangle.BOX,
					bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));

			table.addCell(createCell("样品规格", null, createFont(fontFlagContext),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));

			table.addCell(createCell("抽样数量", null, createFont(fontFlagContext),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));

			table.addCell(createCell("样本基数", null, createFont(fontFlagContext),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			table.addCell(createCell("", null, createFont(fontFlagContextMin),
					null, null, 36, 0, Element.ALIGN_CENTER,
					Element.ALIGN_MIDDLE, Rectangle.BOX, bgcolor));
			document.add(table);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			document.close();
		}
		return document;
	}
	public static Document cyqzpzDocs(String pdfPath,  String [][] context) throws Exception{
		// 创建一个对中文字符集支持的基础字体

		BaseFont bfChinese = BaseFont.createFont( "c:\\windows\\fonts\\simsun.ttc,1" , BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

		// 使用基础字体对象创建新字体对像，粗体12号红色字

		Font font = new Font(bfChinese, 12, Font.BOLD, BaseColor.BLUE);

		Document document = new Document(PageSize.A4); // 创建document对象

		PdfWriter.getInstance(document, new FileOutputStream(
				pdfPath));// 创建书写器

		document.open(); // 打开文档

		String title = "用户列表信息"; // 文档内容

		Paragraph paragraph = new Paragraph(title, font); // 创建段落，并设置字体

		paragraph.setAlignment(Paragraph.ALIGN_CENTER); // 设置段落居中

		document.add(paragraph); // 将段落添加到文档中

		PdfPTable table = new PdfPTable(6); // 建立一个6列的空白表格对象

		table.setSpacingBefore(30f); // 设置表格上面空白宽度

		String[] tableTitle = { "序号", "用户名称", "真实姓名", "主机构", "从机构", "状态"};

		for (int i = 0; i < tableTitle.length; i++) {

			paragraph = new Paragraph(tableTitle[i], new Font(bfChinese, 10,

			Font.BOLD));

			PdfPCell cell = new PdfPCell(paragraph); // 建立一个单元格

			cell.setHorizontalAlignment(Element.ALIGN_CENTER); // 设置内容水平居中显示

			cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中

			table.addCell(cell);

		}

		for (int i = 0; i < context.length; i++) {

			for (int j = 0; j < context[i].length; j++) {

				PdfPCell cell = new PdfPCell(new Paragraph(context[i][j],

				new Font(bfChinese, 10))); // 建立一个单元格

				cell.setHorizontalAlignment(Element.ALIGN_CENTER); // 设置内容水平居中显示

				cell.setVerticalAlignment(Element.ALIGN_MIDDLE); // 设置垂直居中

				table.addCell(cell);

			}

		}

		document.add(table);

		document.close();
		return document;
	}
	/**
	 * 
	 * @param context
	 *            单元格内文字
	 * @param table
	 *            使用表格初始化cell
	 * @param font
	 *            字体
	 * @param colspan
	 *            列合并
	 * @param rowspan
	 *            行合并
	 * @param height
	 *            单元格高
	 * @param padding
	 *            单元格与border距离
	 * @param horizontalAlignment
	 *            水平对齐
	 * @param verticalAlignment
	 *            垂直对齐
	 * @param border
	 *            单元格border
	 * @param backgroundcolor
	 *            背景颜色
	 * @return 单元格cell
	 * @throws DocumentException
	 *             文档异常
	 * @throws IOException
	 *             流异常
	 */
	private static PdfPCell createCell(String context, PdfPTable table,
			Font font, Integer colspan, Integer rowspan, Integer height,
			Integer padding, Integer horizontalAlignment,
			Integer verticalAlignment, Integer border, BaseColor backgroundcolor)
			throws DocumentException, IOException {
		PdfPCell cell = null;
		if (context == null) {
			cell = new PdfPCell(table);// 如果正文为空,则传入表格
		} else {
			cell = new PdfPCell(new Paragraph(context, font));// 如果正文不为空,则传入正文
		}
		if (colspan == null) {
			colspan = 1;
		}
		if (rowspan == null) {
			rowspan = 1;
		}
		if (height == null) {
			height = null;
		}
		if (padding == null) {
			padding = 0;
		}
		if (horizontalAlignment == null) {
			horizontalAlignment = Element.ALIGN_CENTER;
		}
		if (verticalAlignment == null) {
			verticalAlignment = Element.ALIGN_MIDDLE;
		}
		if (border == null) {
			border = Rectangle.NO_BORDER;
		}
		if (backgroundcolor == null) {
			backgroundcolor = new BaseColor(255, 255, 255);
		}
		cell.setColspan(colspan);
		cell.setRowspan(rowspan);
		cell.setFixedHeight(height);
		cell.setPadding(padding);
		cell.setHorizontalAlignment(horizontalAlignment);
		cell.setVerticalAlignment(verticalAlignment);
		cell.setBorder(border);
		cell.setBackgroundColor(backgroundcolor);

		return cell;
	}

	/**
	 * 获取字体
	 * 
	 * @param fontFlag
	 *            字体标识
	 * @return 字体
	 * @throws DocumentException
	 * @throws IOException
	 */
	private static Font createFont(int fontFlag) throws DocumentException,
			IOException {
		Font font = null;
		BaseFont bfChinese = BaseFont.createFont("STSongStd-Light",
				"UniGB-UCS2-H", false); // 设定中文字体
		font = new Font(bfChinese);
		switch (fontFlag) {
		case fontFlagTitle:
			font.setSize(26f);// 设定头字体大小
			font.setStyle(Font.BOLD);// 设定为粗体
			break;
		case fontFlagContext:
			font.setSize(14f);// 设定正文字体大小 四号字
			break;
		case fontFlagContextMin:
			font.setSize(12f); // 设定小字体大小 五号字
			break;
		case fontFlagContextUL:
			font.setSize(12f); // 带下划线的五号字
			font.setStyle(Font.UNDERLINE);
			break;
		case fontFlagTitle22:
			font.setSize(22f); // 22号字
			break;
		case fontFlagTitle36:
			font.setSize(36f); // 36号字
			font.setStyle(Font.BOLD);// 设定为粗体
			break;
		default:
			font.setSize(12f); // 设定默认字体大小
			break;
		}
		return font;
	}

	/**
	 * 初始化表格
	 * 
	 * @param widths
	 *            表格列宽
	 * @return
	 * @throws DocumentException
	 */
	private static PdfPTable initTable(float... widths)
			throws DocumentException {

		PdfPTable table = new PdfPTable(widths.length);// 创建一个4列的表格
		// 设置table每一列的宽度，widths里写的是百分比，他们加和需要是1
		table.setWidths(widths);
		// 设置表格在页面上的宽度，设成100表示可以表格填满页面，但是要去掉页面margin
		table.setWidthPercentage(90);
		// 设置表格上端的空白距离，类似css中的margin-top:xxpx;这样在给表格加上标题后，标题就不会跟表格重叠在一起了。
		table.setSpacingBefore(5f);
		table.getDefaultCell().setBorder(0);// 设置表格默认为1边框
		table.getDefaultCell().setBorderColor(new BaseColor(0, 0, 0));// Border颜色为黑
		return table;
	}
	/**
	 * 创建空行
	 * @param table
	 * @throws DocumentException
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private static void createNullCell(PdfPTable table)
			throws DocumentException, IOException {
		table.addCell(createCell("", null, createFont(fontFlagContextMin),
				table.getNumberOfColumns(), null, 10, 0, Element.ALIGN_CENTER,
				Element.ALIGN_MIDDLE, Rectangle.NO_BORDER, bgcolor));
	}
}
