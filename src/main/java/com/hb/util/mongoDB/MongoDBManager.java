package com.hb.util.mongoDB;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.hb.util.commonUtil.DateUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

/**
 * MongoDB Manager
 * 
 * @author dhdu@qq.com
 * 
 */
public class MongoDBManager {
	// instance
	private final static MongoDBManager instance = new MongoDBManager();

	//
	private MongoDBManager() {
	}

	//
	private static Mongo m = null;
	//
	private static DB db = null;
	//
	private static DBCollection coll = null;
	//
	private static GridFS gridFS = null;
	// static block for init
	static {
		try {
//			m = new Mongo("db.host", Integer.valueOf("db.port"));
//			db = m.getDB("db.dbname");
//			coll = db.getCollection("db.collname");
			m = new Mongo("127.0.0.1", Integer.valueOf("27017"));
			db = m.getDB("myTestDBName");
			coll = db.getCollection("db.collname");
			gridFS = new GridFS(db);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public static MongoDBManager getInstance() throws Exception {
		return instance;
	}

	/**
	 * 
	 * @return
	 */
	public DBCollection getCollection() {
		return coll;
	}

	/**
	 * 
	 * @return
	 */
	public GridFS getGridFS() {
		return gridFS;
	}

	/**
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Object insert(Map map) throws Exception {
		int n = coll.insert(map2Obj(map)).getN();
		return n;
	}

	/**
	 * 
	 * @param list
	 */
	public void insertBatch(List<Map<String, Object>> list) {
		if (list == null || list.isEmpty()) {
			return;
		}
		List<DBObject> listDB = new ArrayList<DBObject>();
		for (int i = 0; i < list.size(); i++) {
			DBObject dbObject = map2Obj(list.get(i));
			listDB.add(dbObject);
		}
		coll.insert(listDB);
	}

	/**
	 * 
	 * @param map
	 */
	public void delete(Map<String, Object> map) {
		DBObject obj = map2Obj(map);
		coll.remove(obj);
	}

	/** 
  *  
  *  
  */
	public void deleteAll() {
		List<DBObject> rs = getAll();
		if (rs != null && !rs.isEmpty()) {
			for (int i = 0; i < rs.size(); i++) {
				coll.remove(rs.get(i));
			}
		}
	}

	/**
	 * 
	 * @param list
	 */
	public void deleteBatch(List<Map<String, Object>> list) {
		if (list == null || list.isEmpty()) {
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			coll.remove(map2Obj(list.get(i)));
		}
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	public long getCount(Map<String, Object> map) {
		return coll.getCount(map2Obj(map));
	}

	/**
	 * 
	 * @param setFields
	 * @param whereFields
	 */
	public void update(Map<String, Object> setFields,
			Map<String, Object> whereFields) {
		DBObject obj1 = map2Obj(setFields);
		DBObject obj2 = map2Obj(whereFields);
		getCollection().updateMulti(obj1, obj2);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Map<String, Object> getById(String id) {
		DBObject obj = new BasicDBObject();
		obj.put("id", new Long(id));
		DBObject result = getCollection().findOne(obj);
		return result.toMap();
	}

	/**
	 * 
	 * @return
	 */
	public long getCount() {
		return coll.find().count();
	}

	/**
	 * 
	 * @return
	 */
	public List<DBObject> getAll() {
		return coll.find().toArray();
	}

	/**
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public DBObject findOne(Map map) throws Exception {
		return coll.findOne(map2Obj(map));
	}

	/**
	 * 
	 * @param <DBObject>
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public <DBObject> List find(Map map) throws Exception {
		DBCursor c = coll.find(map2Obj(map));
		if (c != null)
			return c.toArray();
		else
			return null;
	}

	/***
	 * ---------------------file
	 ***/

	/**
	 * 
	 * @param in
	 * @param filename
	 */
	public void saveFile(InputStream in, String filename) {
		GridFSFile mongofile = gridFS.createFile(in, filename);
		// 文件名
		mongofile.put("filename", filename);
		// 保存时间
		mongofile.put("uploadDate", DateUtil.getCurrentDate());
		// 文件类型
		mongofile.put("contentType",
				filename.split("\\.")[filename.split("\\.").length - 1]);
		mongofile.save();
	}

	/**
	 * 
	 * @param file
	 * @param filename
	 */
	public void saveFile(File file, String filename) {
		try {
			GridFSFile mongofile = gridFS.createFile(file);
			// 文件名
			mongofile.put("filename", filename);
			// 保存时间
			mongofile.put("uploadDate", DateUtil.getCurrentDate());
			// 文件类型
			mongofile.put("contentType",
					filename.split("\\.")[filename.split("\\.").length - 1]);
			mongofile.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param bytes
	 * @param filename
	 */
	public void saveFile(byte[] bytes, String filename) {
		GridFSFile mongofile = gridFS.createFile(bytes);
		// 文件名
		mongofile.put("filename", filename);
		// 保存时间
		mongofile.put("uploadDate", DateUtil.getCurrentDate());
		// 文件类型
		mongofile.put("contentType",
				filename.split("\\.")[filename.split("\\.").length - 1]);
		mongofile.save();
	}

	/**
	 * 
	 * @param in
	 * @param fn
	 * @param map
	 */
	public void saveFile(InputStream in, String fn, Map map) {
		GridFSFile mongofile = gridFS.createFile(in, fn);
		if (map != null) {
			Object os[] = map.keySet().toArray();
			for (int i = 0; i < os.length; i++) {
				mongofile.put(String.valueOf(os[i]), map.get(os[i]));
			}
		}
		// 文件名
		mongofile.put("filename", fn);
		// 保存时间
		mongofile.put("uploadDate", DateUtil.getCurrentDate());
		// 文件类型
		mongofile.put("contentType",
				fn.split("\\.")[fn.split("\\.").length - 1]);
		mongofile.save();
	}

	/**
	 * 
	 * @param file
	 * @param fn
	 * @param map
	 */
	public void saveFile(File file, String fn, Map map) {
		GridFSFile mongofile;
		try {
			mongofile = gridFS.createFile(file);
			// DBObject o=map2Obj(map);
			if (map != null) {
				Object os[] = map.keySet().toArray();
				for (int i = 0; i < os.length; i++) {
					mongofile.put(String.valueOf(os[i]), map.get(os[i]));
				}
			}
			// 文件名
			mongofile.put("filename", fn);
			// 保存时间
			mongofile.put("uploadDate", DateUtil.getCurrentDate());
			// 文件类型
			mongofile.put("contentType",
					fn.split("\\.")[fn.split("\\.").length - 1]);
			mongofile.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public List<GridFSDBFile> findFiles(Map map) {
		List<GridFSDBFile> list = gridFS.find(map2Obj(map));
		return list;
	}

	/**
	 * 
	 * @param fileName
	 * @return
	 */
	public List<GridFSDBFile> findFilesByName(String fileName) {
		List<GridFSDBFile> list = gridFS.find(fileName);
		return list;
	}

	/**
	 * 
	 * @param filename
	 * @return
	 */
	public GridFSDBFile findFileByName(String filename) {
		return gridFS.findOne(filename);
	}

	/**
	 * 
	 * @param filename
	 * @return
	 */
	public InputStream getFileInputStream(String filename) {
		return gridFS.findOne(filename).getInputStream();
	}

	/**
	 * 
	 * @param map
	 * @return
	 */
	public GridFSDBFile findFirstFile(Map<String, Object> map) {
		return gridFS.findOne(map2Obj(map));
	}

	/**
	 * 
	 * @param filename
	 */
	public void removeFile(String filename) {
		gridFS.remove(filename);
	}

	/**
	 * 
	 * @param query
	 */
	public void removeFile(BasicDBObject query) {
		gridFS.remove(query);
	}

	/**
	 * 
	 * @param id
	 */
	public void removeFile(ObjectId id) {
		gridFS.remove(id);
	}

	/** 
  *  
  */
	public void removeAllFile() {
		gridFS.remove(new BasicDBObject());
		System.out.println("remove all files ok!");
	}

	/**
	 * 
	 * @return
	 */
	public long getFileCount() {
		return gridFS.getFileList().count();
	}

	/**
	 * 
	 * @return
	 */
	public List<GridFSDBFile> getAllFiles() {
		return gridFS.find(new BasicDBObject());
	}

	/**
	 * ------------------------------------------------------------------------
	 * 
	 **/
	/**
	 * map2Obj
	 * 
	 * @param map
	 * @return
	 */
	private DBObject map2Obj(Map<String, Object> map) {
		DBObject obj = new BasicDBObject();
		obj.putAll(map);
		return obj;
	}

	/**
	 * encoder obj2str
	 * 
	 * @param o
	 * @return
	 * @throws Exception
	 */
	private String encoder(Object o) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		DataOutputStream dataOutStream = new DataOutputStream(outStream);
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				dataOutStream);
		objectOutputStream.writeObject(o);
		objectOutputStream.flush();
		objectOutputStream.close();
		byte[] bytes = outStream.toByteArray();
		BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		return encoder.encode(bytes);
	}

	/**
	 * decoder str2obj
	 * 
	 * @param s
	 * @return
	 * @throws Exception
	 */
	private Object decoder(Object s) throws Exception {
		BASE64Decoder decoder = new sun.misc.BASE64Decoder();
		byte[] bytes = decoder.decodeBuffer((String) s);
		ByteArrayInputStream inStream = new ByteArrayInputStream(bytes);
		DataInputStream dataInStream = new DataInputStream(inStream);
		ObjectInputStream objectInputStream = new ObjectInputStream(
				dataInStream);
		return objectInputStream.readObject();
	}

	/**
	 * ------------------------------------------------------------------------
	 * 
	 **/
	/**
	 * main
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// Map m = new HashMap();
		// m.put("id", "123");
		// m.put("name", "ddh");
		// System.out.println(getInstance().insert(m));
		// System.out.println(getInstance().findOne(m));
		// System.out.println(getInstance().find(m));
		/*
		 * String basepath =
		 * Test.class.getResource("/").getPath().replaceFirst("/WEB-INF/classes/"
		 * , "/").replaceFirst("/", ""); String filePath = basepath + "file/" +
		 * "h.mp4"; File file = new File(filePath); String
		 * k=filePath.split("\\.")[filePath.split("\\.").length-1]; String
		 * newfn=UUID.randomUUID().toString(); newfn=newfn+"."+k; if
		 * (file.exists()) { try{ //getInstance().saveFile(file,newfn);
		 * //System.out.println("save ok!"); } catch (Exception e) {
		 * e.printStackTrace(); } }
		 */

		/*
		 * GridFSDBFile gf=getInstance().findFileByName(
		 * "5a89dab7-10ab-4d0e-b22f-c0336d574d1f.mp4"); gf.writeTo(basepath +
		 * "file/"+gf.getFilename());
		 */

		/*
		 * Map m = new HashMap(); m.put("filename",
		 * "5a89dab7-10ab-4d0e-b22f-c0336d574d1f.mp4");
		 * System.out.println(getInstance().getInstance().findFiles(m));
		 */

		/*
		 * getInstance().deleteAll();
		 * System.out.println(getInstance().getCount());
		 * System.out.println(getInstance().getAll());
		 */
	}
}