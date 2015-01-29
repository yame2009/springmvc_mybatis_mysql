package com.hb.util.html;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *   jsoup 是一款 Java 的HTML 解析器，可直接解析某个URL地址、HTML文本内容。
 *   它提供了一套非常省力的API，可通过DOM，CSS以及类似于JQuery的操作方法来取出和操作数据。
 *   请参考：
 *          http://jsoup.org/  
 *          http://www.open-open.com/jsoup/set-attributes.htm
 *          http://www.iteye.com/topic/1010581
 *
 */
public class JsoupUtil {

	public static void main(String[] args) throws IOException {
		
		JsoupUtil jsoup = new JsoupUtil();
		
//		jsoup.getListLinks("http://me2-sex.lofter.com/tag/美女摄影?page=1");
		
		jsoup.parse("http://me2-sex.lofter.com/tag/美女摄影?page=1");
	}

	public void getListLinks(String url) throws IOException {
		Document doc = Jsoup.connect(url).get();
		Elements links = doc.select("a[href]");
		Elements media = doc.select("[src]");
		Elements imports = doc.select("link[href]");

		print("\nMedia: (%d)", media.size());
		for (Element src : media) {
			if (src.tagName().equals("img"))
				print(" * %s: <%s> %sx%s (%s)", src.tagName(),
						src.attr("abs:src"), src.attr("width"),
						src.attr("height"), trim(src.attr("alt"), 20));
			else
				print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
		}

		print("\nImports: (%d)", imports.size());
		for (Element link : imports) {
			print(" * %s <%s> (%s)", link.tagName(), link.attr("abs:href"),
					link.attr("rel"));
		}

		print("\nLinks: (%d)", links.size());
		for (Element link : links) {
			print(" * a: <%s>  (%s)", link.attr("abs:href"),
					trim(link.text(), 35));
		}
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

	private static String trim(String s, int width) {
		if (s.length() > width)
			return s.substring(0, width - 1) + ".";
		else
			return s;
	}
	
	public  void parse(String urlStr) {
		// 返回结果初始化。

		Document doc = null;
		try {
			doc = Jsoup
					.connect(urlStr)
					.userAgent(
							"Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)") // 设置User-Agent
					.timeout(5000) // 设置连接超时时间
					.get();
		} catch (Exception e) {
			System.out.println("e.getMessage(); "+e.getMessage());
			return ;
		} 
		System.out.println(doc.title());
		
		Element head = doc.head();
		Elements metas = head.select("meta");
		for (Element meta : metas) {
			String content = meta.attr("content");
			if ("content-type".equalsIgnoreCase(meta.attr("http-equiv"))
					&&  !content.startsWith("text/html")) {
				System.out.println( "urlStr + "+urlStr);
				return ;
			}
			if ("description".equalsIgnoreCase(meta.attr("name"))) {
				System.out.println("content +  "+meta.attr("content"));
			}
		}
		Element body = doc.body();
		for (Element img : body.getElementsByTag("img")) {
			String imageUrl = img.attr("abs:src");//获得绝对路径
//			print(imageUrl);
			System.out.println("imageUrl +  "+imageUrl);
			
//			for (String suffix : IMAGE_TYPE_ARRAY) {
//				if(imageUrl.indexOf("?")>0){
//					imageUrl=imageUrl.substring(0,imageUrl.indexOf("?"));
//				}
//				if (StringUtils.endsWithIgnoreCase(imageUrl, suffix)) {
//					imgSrcs.add(imageUrl);
//					break;
//				}
//			}
		}
	}

}
