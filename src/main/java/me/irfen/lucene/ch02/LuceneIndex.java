package me.irfen.lucene.ch02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneIndex {


	// 索引器
	private IndexWriter writer = null;

	public LuceneIndex(String indexStorePath) {
		try {
			// 索引文件的保存位置
			Directory dir = FSDirectory.open(new File(indexStorePath));
			// 分析器
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_4_9);
			// 配置类
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_4_9, analyzer);
			iwc.setOpenMode(OpenMode.CREATE);// 创建模式 OpenMode.CREATE_OR_APPEND 添加模式
			writer = new IndexWriter(dir, iwc);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 将要建立索引的文件构造成一个Document对象，并添加一个域"content"
	private Document getDocument(File f) throws Exception {
		Document doc = new Document();

		FileInputStream is = new FileInputStream(f);
		Reader reader = new BufferedReader(new InputStreamReader(is));
		// 字符串 StringField LongField TextField
		Field pathField = new StringField("path", f.getAbsolutePath(), Field.Store.YES);
		Field contenField = new TextField("contents", reader);
		// 添加字段
		doc.add(contenField);
		doc.add(pathField);
		return doc;
	}

	public void writeToIndex(String indexFilePath) throws Exception {
		File folder = new File(indexFilePath);

		if (folder.isDirectory()) {
			String[] files = folder.list();
			for (int i = 0; i < files.length; i++) {
				File file = new File(folder, files[i]);
				Document doc = getDocument(file);
				System.out.println("正在建立索引 : " + file + "");
				writer.addDocument(doc);
			}
		}
	}

	public void close() throws Exception {
		writer.close();
	}
}
