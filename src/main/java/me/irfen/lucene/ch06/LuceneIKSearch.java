package me.irfen.lucene.ch06;

import java.io.File;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneIKSearch {
	// 声明一个IndexSearcher对象
	private IndexSearcher searcher = null;
	// 声明一个Query对象
	private Query query = null;
	private String field = "contents";

	public LuceneIKSearch(String indexStorePath) {
		try {
			IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(indexStorePath)));
			searcher = new IndexSearcher(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 返回查询结果
	public final TopDocs search(String keyword) {
		System.out.println("正在检索关键字 : " + keyword);
		try {
			Analyzer analyzer = new IKAnalyzer();
			QueryParser parser = new QueryParser(Version.LUCENE_4_9, field, analyzer);
			// 将关键字包装成Query对象
			query = parser.parse(keyword);
			Date start = new Date();
			TopDocs results = searcher.search(query, 5 * 2);
			Date end = new Date();
			System.out.println("检索完成，用时" + (end.getTime() - start.getTime()) + "毫秒");
			return results;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 打印结果
	 * @param results
	 */
	public void printResult(TopDocs results) {
		ScoreDoc[] h = results.scoreDocs;
		if (h.length == 0) {
			System.out.println("对不起，没有找到您要的结果。");
		} else {
			for (int i = 0; i < h.length; i++) {
				try {
					System.out.print("这是第" + i + "个检索到的结果，文档：");
					System.out.println("doc: " + h[i].doc + ", score: " + h[i].score);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("--------------------------");
	}
}
