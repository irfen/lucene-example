package me.irfen.lucene.ch06;

import org.junit.Test;

public class LuceneIKIndexTest {

	public final static String INDEX_STORE_PATH = "f:/lucene/ch06/index"; // 索引的存放位置
	public final static String TEXT = "不论你采用什么形式的测试过程，什么形式的部署过程，没有代码审查——game over。为什么？因为代码的质量是一种人能看懂的质量。不管你如何测试，有如何严谨的部署流程，只有当另外一个人看了这些代码，并且表明能看懂时，这些代码才有意义。如果看不懂，你认为这样的代码——虽然测试通过、部署符合流程——可以上线吗？"; // 索引的文件的存放路径

	@Test
	public void testIndex() throws Exception {
		// 声明一个对象
		LuceneIKIndex indexer = new LuceneIKIndex(INDEX_STORE_PATH);
		// 建立索引
		long start = System.currentTimeMillis();
		indexer.writeToIndex(TEXT);
		long end = System.currentTimeMillis();

		System.out.println("建立索引用时" + (end - start) + "毫秒");
		indexer.close();
	}

}
