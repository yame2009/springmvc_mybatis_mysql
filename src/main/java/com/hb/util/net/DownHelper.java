package com.hb.util.net;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 多线程下载
 *
 */
public class DownHelper {
	static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) throws Exception {
		int threadCount = 50; // 线程总数
		// 下载文件地址
		String path = "http://dldir1.qq.com/qqfile/qq/QQ2013/2013Beta6/7354/QQ2013Beta6.exe";
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200) { // 打开连接
			int length = conn.getContentLength(); // 获得文件大小
			// 计算每个线程 分配的下载数据量大小
			int block = length % threadCount == 0 ? length / threadCount
					: length / threadCount + 1;
			// 穿创建一个保存数据的文件
			File file = new File(getFileName(path));
			// 文件访问类 rwd 模式，可写入、可读取，写入后立刻推送到硬盘
			RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
			// 给文件设定大小
			accessFile.setLength(length);
			accessFile.close();
			// 输出文件信息
			System.out.println(DATE_FORMAT.format(new Date()) + " 开始下载文件 ： "
					+ path + " 文件总大小 ： " + (length / 1024D / 1024D) + " mb");
			System.out.println("文件保存路径：" + file.getAbsolutePath());
			for (int i = 0; i < threadCount; i++) {
				// 启动N个线程，为每个线程分配自己的 数据 块
				new DoubleDownThread(i, url, file, block).start();
			}
		}

	}

	private static String getFileName(String path) {

		System.out.println(path.lastIndexOf("/"));
		System.out.println(path.lastIndexOf("/") + 1);
		System.out.println(path.substring(path.lastIndexOf("/") + 1));
		return "C:/" + path.substring(path.lastIndexOf("/") + 1);
	}
}

class DoubleDownThread extends Thread {
	private int id; // 线程ID
	private URL url; // 下载文件的连接
	private File file; // 保存到文件路径
	private int block; // 数据块大小

	public DoubleDownThread(int id, URL url, File file, int block) {
		super();
		this.id = id;
		this.url = url;
		this.file = file;
		this.block = block;
	}

	@Override
	public void run() {
		try {
			// //文件访问类 rwd 模式，可写入、可读取，写入后立刻推送到硬盘
			RandomAccessFile accessFile = new RandomAccessFile(file, "rwd");
			/**
			 * 比如一个长度为 10字节的数据，3个线程下载 根据 算法分配 if(数据长度 % 线程数 == 0){ 分配块 =
			 * 数据长度/线程数 } else { 分配块 = 数据长度/线程数 + 1 } 数据长度为 10 线程为 3 。
			 * 则每个线程分得块大小为 4 第一个线程起止位置是 0 - 3 第二个线程起止位置是 4 - 7 第三个线程起止位置是 8 - 11
			 * 
			 * 因为数据长度总共才 10 字节。所以读取的时候。发现到了10 就读取不到数据，程序自然不会继续读取不存在的 第11个字节
			 */
			// 计算文件写入起始位置
			int start = id * block;
			// 计算文件写入结束位置
			int end = (id + 1) * block - 1;
			// 打开文件下载链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// 设置连接超时异常等待时间
			conn.setConnectTimeout(5000);

			conn.setRequestMethod("GET");
			// 断点下载，不需要请求整个文件，为请求设置请求头。Range 表示局部请求下载文件 bytes=起始位置-结束位置
			conn.setRequestProperty("Range", "bytes=" + start + "-" + end);
			// 局部请求。成功的标志不再是 200， 而是 206
			if (conn.getResponseCode() == 206) {
				// 将本地文件的指针移动到要写入的位置
				accessFile.seek(start);
				// 获得网络文件输入流，开始写入文件
				InputStream in = conn.getInputStream();
				byte[] bys = new byte[1024];
				int length = 0;
				while ((length = in.read(bys, 0, bys.length)) != -1) {
					accessFile.write(bys, 0, length);
				}
				// 下载完成。，关闭流
				accessFile.close();
				in.close();
			}
			// 输出线程下载文件的 提示
			System.out.println(DownHelper.DATE_FORMAT.format(new Date())
					+ " 线程" + (id + 1) + "：下载完成 ");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
