package me.irfen.lucene.ch04;

import org.apache.lucene.search.TopDocs;
import org.junit.Test;

public class LuceneSearchTest {

	public final static String INDEX_STORE_PATH = "f:/lucene/ch02/index"; // 索引的存放位置

	@Test
	public void testSearch() {
		LuceneSearch test = new LuceneSearch(INDEX_STORE_PATH);
		TopDocs h = null;
		h = test.search("中华");
		test.printResult(h);
		h = test.search("人民");
		test.printResult(h);
		h = test.search("共和国");
		test.printResult(h);
	}
}
