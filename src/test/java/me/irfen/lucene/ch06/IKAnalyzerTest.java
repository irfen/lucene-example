package me.irfen.lucene.ch06;

import java.io.File;
import java.io.StringReader;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.AtomicReaderContext;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.DocsEnum;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.DocIdSetIterator;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class IKAnalyzerTest {

	public final static String INDEX_STORE_PATH = "f:/lucene/ch06/index"; // 索引的存放位置
	public static final String TEXT = "不论你采用什么形式的测试过程，什么形式的部署过程，没有代码审查——game over。为什么？因为代码的质量是一种人能看懂的质量。不管你如何测试，有如何严谨的部署流程，只有当另外一个人看了这些代码，并且表明能看懂时，这些代码才有意义。如果看不懂，你认为这样的代码——虽然测试通过、部署符合流程——可以上线吗？";

	@Test
	public void testIKAnalyzer() throws Exception {
		@SuppressWarnings("resource")
		Analyzer analyzer = new IKAnalyzer();// IK分词
		TokenStream token = analyzer.tokenStream("a", new StringReader(TEXT));
		token.reset();

		CharTermAttribute term = token.addAttribute(CharTermAttribute.class);// term信息
		OffsetAttribute offset = token.addAttribute(OffsetAttribute.class);// 位置数据

		while (token.incrementToken()) {
			System.out.println(term + "   " + offset.startOffset() + "   " + offset.endOffset());
		}

		token.end();
		token.close();
	}

	@Test
	public void testTermFrep() {
		try {
			Directory directroy = FSDirectory.open(new File(INDEX_STORE_PATH));
			IndexReader reader = DirectoryReader.open(directroy);
			for (int i = 0; i < reader.numDocs(); i++) {
				System.out.println("第" + (i + 1) + "篇文档：");
				Terms terms = reader.getTermVector(i, "contents");
				if (terms == null)
					continue;
				TermsEnum termsEnum = terms.iterator(null);
				BytesRef thisTerm = null;
				while ((thisTerm = termsEnum.next()) != null) {
					String termText = thisTerm.utf8ToString();
					DocsEnum docsEnum = termsEnum.docs(null, null);
					while ((docsEnum.nextDoc()) != DocIdSetIterator.NO_MORE_DOCS) {
						System.out.println("关键字：" + termText + " \t词频:  " + docsEnum.freq());
					}
				}
			}
			reader.close();
			directroy.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testInverseDocumentFrep() {
		try {
			Directory directroy = FSDirectory.open(new File(INDEX_STORE_PATH));
			IndexReader reader = DirectoryReader.open(directroy);
			List<AtomicReaderContext> list = reader.leaves();
			for (AtomicReaderContext ar : list) {
				AtomicReader areader = ar.reader();
				Terms term = areader.terms("contents");
				TermsEnum tn = term.iterator(null);

				BytesRef text;
				while ((text = tn.next()) != null) {
					System.out.println("关键字=" + text.utf8ToString() + " \tIDF : " + tn.docFreq()
							+ " \t全局词频 :  " + tn.totalTermFreq()
							);
				}
			}
			reader.close();
			directroy.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
