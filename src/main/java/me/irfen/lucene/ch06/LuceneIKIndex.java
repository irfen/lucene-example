package me.irfen.lucene.ch06;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class LuceneIKIndex {

	// 索引器
	private IndexWriter writer = null;

	public LuceneIKIndex(String indexStorePath) {
		try {
			// 索引文件的保存位置
			Directory dir = FSDirectory.open(new File(indexStorePath));
			// 分析器
			Analyzer analyzer = new IKAnalyzer();
			// 配置类
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_9, analyzer);
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);// 创建模式 OpenMode.CREATE_OR_APPEND 添加模式
			writer = new IndexWriter(dir, iwc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 将要建立索引的文件构造成一个Document对象，并添加一个域"content"
	private Document getDocument(String text) throws Exception {
		Document doc = new Document();

		FieldType fieldType = new FieldType();
		fieldType.setIndexed(true);// 存储
		fieldType.setStored(true);// 索引
		fieldType.setStoreTermVectors(true);
		fieldType.setTokenized(true);
		fieldType.setStoreTermVectorPositions(true);// 存储位置
		fieldType.setStoreTermVectorOffsets(true);// 存储偏移量
		Field field = new Field("contents", text, fieldType);
		// 添加字段
		doc.add(field);
		return doc;
	}

	public void writeToIndex(String text) throws Exception {
		System.out.println("正在建立索引....");
		Document doc = getDocument(text);
		writer.addDocument(doc);
	}

	public void close() throws Exception {
		writer.close();
	}
}
