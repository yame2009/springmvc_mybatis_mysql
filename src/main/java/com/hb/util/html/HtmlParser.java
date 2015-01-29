package com.hb.util.html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HtmlParser { // 继承ParserCallback，解析结果驱动这些回调方法

	public static String readURL(String url) throws IOException {
		StringBuffer html = new StringBuffer();

		URL addrUrl = null;
		URLConnection urlConn = null;
		BufferedReader br = null;
		try {
			addrUrl = new URL(url);
			urlConn = addrUrl.openConnection();
			br = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));

			String buf = null;
			while ((buf = br.readLine()) != null) {
				html.append(buf + "\r\n");
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}

		return html.toString();
	}

	public static void main(String args[]) {

		try {
			System.out
					.println(readURL("http://me2-sex.lofter.com/tag/美女摄影?page=1"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}