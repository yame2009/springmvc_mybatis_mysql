package com.hb.util.mongoDB;

import java.net.UnknownHostException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

/**
 * MongoDB操作类
 * 
 * @author dyk
 *
 */
public class MongoDbManager_old {
	protected static Logger logger = LoggerFactory
			.getLogger("MongoDbManagerImpl");
	private static final String DBNAME = "clementine";
	private Mongo mongo = null;
	private DB dbConnection = null;
	private String mongoServerAddr;
	private int mongoServerPort;
	private static Map<String, DBCollection> dbCollectionMap = new ConcurrentHashMap<String, DBCollection>();

	public void setMongoServerAddr(String mongoServerAddr) {
		this.mongoServerAddr = mongoServerAddr;
	}

	public void setMongoServerPort(int mongoServerPort) {
		this.mongoServerPort = mongoServerPort;
	}

	public void init() {
		if (this.mongo == null) {
			try {
				this.mongo = new Mongo(this.mongoServerAddr,
						this.mongoServerPort);
				if (null != this.mongo) {
					this.dbConnection = this.mongo.getDB(DBNAME);
				}
			} catch (UnknownHostException e) {
				logger.error("连接mongoDb失败, 服务器地址: " + this.mongoServerAddr
						+ ", 端口: " + this.mongoServerPort);
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * get an table
	 * 
	 * @param collectionName
	 * @return
	 */
	private DBCollection getDBCollection(String collectionName) {
		DBCollection collection = null;
		if (dbCollectionMap.containsKey(collectionName)) {
			collection = dbCollectionMap.get(collectionName);
		} else {
			collection = this.dbConnection.getCollection(collectionName);
			if (null != collection) {
				dbCollectionMap.put(collectionName, collection);
			}
		}
		return collection;
	}

	/**
	 * check if doc exsit
	 * 
	 * @param collectionName
	 *            table name
	 * @param query
	 *            target document
	 */
	public boolean isDocumentExsit(String collectionName, DBObject query) {
		boolean result = false;
		DBCursor dbCursor = null;
		DBCollection collection = this.getDBCollection(collectionName);
		if (null != collection) {
			dbCursor = collection.find(query);
			if (null != dbCursor && dbCursor.hasNext()) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * query an record
	 * 
	 * @param collectionName
	 *            table name
	 * @param query
	 *            target document
	 * @return
	 */
	public DBObject selectDocument(String collectionName, DBObject query) {
		DBObject result = null;
		DBCursor dbCursor = null;
		DBCollection collection = this.getDBCollection(collectionName);
		if (null != collection) {
			dbCursor = collection.find(query);
			if (null != dbCursor && dbCursor.hasNext()) {
				result = dbCursor.next();
			}
		}
		return result;
	}

	/**
	 * /** insert an new record
	 * 
	 * @param collectionName
	 *            table name
	 * @param newDocument
	 *            new doc
	 * @param query
	 *            target document
	 */
	public void insertDocument(String collectionName, DBObject newDocument) {
		DBCollection collection = this.getDBCollection(collectionName);
		if (null != collection) {
			if (!this.isDocumentExsit(collectionName, newDocument)) {// insert
																		// only
																		// doc
																		// not
																		// exist
				collection.insert(newDocument);
			}
		}
	}

	/**
	 * update an document
	 * 
	 * @param collectionName
	 * @param query
	 *            target document
	 * @param updatedDocument
	 * @return
	 */
	public boolean updateDocument(String collectionName, DBObject query,
			DBObject updatedDocument) {
		boolean result = false;
		WriteResult writeResult = null;
		DBCollection collection = this.getDBCollection(collectionName);
		if (null != collection) {
			writeResult = collection.update(query, updatedDocument);
			if (null != writeResult) {
				if (writeResult.getN() > 0) {
					result = true;
				}
			}
		}
		return result;
	}

	/**
	 * delete an document
	 * 
	 * @param collectionName
	 * @param document
	 *            target document
	 * @return
	 */
	public boolean deleteDocument(String collectionName, DBObject query) {
		boolean result = false;
		WriteResult writeResult = null;
		DBCollection collection = this.getDBCollection(collectionName);
		if (null != collection) {
			writeResult = collection.remove(query);
			if (null != writeResult) {
				if (writeResult.getN() > 0) {
					result = true;
				}
			}
		}
		return result;
	}

	public static void main(String[] args) throws UnknownHostException {
		MongoDbManager_old m = new MongoDbManager_old();
		m.setMongoServerAddr("10.235.164.180");
		m.setMongoServerPort(27017);
		m.init();
		String collectionName = "myTest";

		// insert
		String json = "{'num' : 1}";
		DBObject doc1 = (DBObject) JSON.parse(json);
		m.insertDocument(collectionName, doc1);

		// select
		DBObject doc2 = null;
		DBObject query = new BasicDBObject();
		query.put("num", 1);
		doc2 = m.selectDocument(collectionName, query);

		System.out.println(doc2);

		// update
		DBObject updatedDocument = new BasicDBObject();
		updatedDocument.put("$set", new BasicDBObject().append("num", 100));
		boolean result = m.updateDocument(collectionName, query,
				updatedDocument);
		System.out.println(result);
		query.put("num", 100);
		// //remove
		result = m.deleteDocument(collectionName, query);
		System.out.println(result);
	}

}