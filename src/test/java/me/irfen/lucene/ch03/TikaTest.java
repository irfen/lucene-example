package me.irfen.lucene.ch03;

import java.io.File;

import org.junit.Test;

public class TikaTest {

	@Test
	public void testTika() {
		String content = "";
		content = TikaBasicUtil.extractContent(new File("F:/lucene/ch03/ip.xlsx"));
		System.out.println("下面是ip.xlsx文件内容");
		System.out.println(content);
		content = TikaBasicUtil.extractContent(new File("F:/lucene/ch03/工作安排.docx"));
		System.out.println("下面是工作安排.docx文件内容");
		System.out.println(content);
		content = TikaBasicUtil.extractContent(new File("F:/lucene/ch03/ip.csv"));
		System.out.println("下面是ip.csv文件内容");
		System.out.println(content);
	}
}
