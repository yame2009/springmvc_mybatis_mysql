package com.hb.util.object2file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.nustaq.serialization.FSTConfiguration;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * <pre>
 * 本类集成三种java对象序列化方式和实现框架：FST(优选方案),KRYO,jdk。
 * FST:
 * 	   依赖fst-2.19.jar。项目介绍网址：http://www.oschina.net/p/fst
 * 
 * Kryo：
 * 	   依赖kryo-2.24.0.jar。项目介绍网址：http://www.oschina.net/p/kryo
 * 
 * JDK:自带实现
 * </pre>
 * 
 */
public class ObjectSerializationUtil {

	public ObjectSerializationUtil() {
	}
	
	/**
	 *  FST fast-serialization 是重新实现的 Java 快速对象序列化的开发包。序列化速度更快（2-10倍）、体积更小，而且兼容 JDK 原生的序列化。要求 JDK 1.7 支持。
	 *
	 */
	class FSTSerialization{
		
		private  FSTConfiguration configuration = FSTConfiguration.createStructConfiguration();
		// .createDefaultConfiguration();
		
		/**
		 * 对java 对象进行序列化
		 * @param obj
		 * @return byte[]
		 */
		public  byte[] fstSerialize(Object obj) {
			return configuration.asByteArray(obj);
		}
		

		/**
		 * 反序列化
		 * @param sec
		 * @return Object
		 */
		public  Object fstUnserialize(byte[] sec) {
			return configuration.asObject(sec);
		}
	}

	/**
	 * Kryo 是一个快速高效的Java对象图形序列化框架，主要特点是性能、高效和易用。该项目用来序列化对象到文件、数据库或者网络。
	 *
	 */
	class KryoSerialization{

		private  Kryo kryo = new Kryo();

		/**
		 * 对java 对象进行序列化
		 * @param obj
		 * @return byte[]
		 */ 
		public  byte[] kryoSerizlize(Object obj) {
			Kryo kryo = new Kryo();
			byte[] buffer = new byte[2048];
			try(
					Output output = new Output(buffer);
					) {
				
				kryo.writeClassAndObject(output, obj);
				return output.toBytes();
			} catch (Exception e) {
			}
			return buffer;
		}

		
		/**
		 * 反序列化
		 * @param src
		 * @return Object
		 */
		public  Object kryoUnSerizlize(byte[] src) {
			try(
			Input input = new Input(src);
					){
				return kryo.readClassAndObject(input);
			}catch (Exception e) {
			}
			return kryo;
		}
	}
			
	/**
	 * jdk原生序列换方案
	 *
	 */
	class JdkSerialization{
		/**
		 *  jdk原生序列换方案
		 * @param obj
		 * @return
		 */
		public  byte[] jdkSerialize(Object obj) {
			try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
					ObjectOutputStream oos = new ObjectOutputStream(baos);) {
				oos.writeObject(obj);
				return baos.toByteArray();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 *  jdk原生序列换方案
		 * @param obj
		 * @return
		 */
		public  Object jdkUnSerialize(byte[] bits) {
			try (ByteArrayInputStream bais = new ByteArrayInputStream(bits);
					ObjectInputStream ois = new ObjectInputStream(bais);

			) {
				return ois.readObject();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	
}
