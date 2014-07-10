package me.irfen.lucene.ch02;

import org.junit.Test;

public class LuceneIndexTest {

	public final static String INDEX_STORE_PATH = "f:/lucene/ch02/index"; // 索引的存放位置
	public final static String INDEX_FILE_PATH = "f:/lucene/ch02/test"; // 索引的文件的存放路径

	@Test
	public void testIndex() throws Exception {
		// 声明一个对象
		LuceneIndex indexer = new LuceneIndex(INDEX_STORE_PATH);
		// 建立索引
		long start = System.currentTimeMillis();
		indexer.writeToIndex(INDEX_FILE_PATH);
		long end = System.currentTimeMillis();

		System.out.println("建立索引用时" + (end - start) + "毫秒");
		indexer.close();
	}
}
