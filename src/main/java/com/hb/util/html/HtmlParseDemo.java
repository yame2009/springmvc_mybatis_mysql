package com.hb.util.html;

import java.io.*;
import java.net.*;
import java.util.List;

import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

import com.hb.util.net.DownloadURLFile;
 
/**
 * This small demo program shows how to use the
 * HTMLEditorKit.Parser and its implementing class
 * ParserDelegator in the Swing system.
 */
 
public class HtmlParseDemo {
    public static void main(String [] args) {
    	List<String> harfList = getHarf();
        
        for(String s:harfList)
        {
        	getImageUrl(s);
        }
    	
//    	String s = "http://imglf0.ph.126.net/uWXUZfh8JY5tFS7zgPsUwA==/6630605970814815756.jpg";
//    	if(!s.endsWith(".jpg"))
//		{
//			System.out.println();
//		}
    }

	private static List<String> getHarf() {
		Reader r;
        String specUrl = "http://me2-sex.lofter.com/tag/美女摄影?page=1";
        try {
            if (specUrl.indexOf("://") > 0) {
                URL u = new URL(specUrl);
                Object content = u.getContent();
                if (content instanceof InputStream) {
                    r = new InputStreamReader((InputStream)content);
                }
                else if (content instanceof Reader) {
                    r = (Reader)content;
                }
                else {
                    throw new Exception("Bad URL content type.");
                }
            }
            else {
                r = new FileReader(specUrl);
            }
 
            HTMLEditorKit.Parser parser;
            System.out.println("About to parse " + specUrl);
            parser = new ParserDelegator();
            HTMLParseLister cb = new HTMLParseLister();
			parser.parse(r, cb, true);
            r.close();
            
        	return cb.getHarfList();
        	
        }
        catch (Exception e) {
            System.err.println("Error: " + e);
            e.printStackTrace(System.err);
            return null;
        }
	}
        

    	private static void getImageUrl(String specUrl) {
    		Reader r;
//            String specUrl = "http://me2-sex.lofter.com/tag/美女摄影?page=1";
            try {
                if (specUrl.indexOf("://") > 0) {
                    URL u = new URL(specUrl);
                    Object content = u.getContent();
                    if (content instanceof InputStream) {
                        r = new InputStreamReader((InputStream)content);
                    }
                    else if (content instanceof Reader) {
                        r = (Reader)content;
                    }
                    else {
                        throw new Exception("Bad URL content type.");
                    }
                }
                else {
                    r = new FileReader(specUrl);
                }
     
                HTMLEditorKit.Parser parser;
                System.out.println("About to parse " + specUrl);
                parser = new ParserDelegator();
                HTMLParseLister cb = new HTMLParseLister();
    			parser.parse(r, cb, true);
                r.close();
                
            	List<String> imgUrlList = cb.getImgUrlList();
            	
            	for(String s: imgUrlList)
            	{
            		//"http://imglf1.ph.126.net/Y-Ck3sGAqu5CfukxlAC1JA==/2438980673215511332.jpg" 
            		String[] names = s.split("==/");
            		String name = "";
            		if(names.length == 1)
            		{
            			name = names[0];
            		}
            		else
            		{
            			name = names[1];
            		}
            		
            		Thread.sleep(200);  
            		
            		DownloadURLFile.downLoadFromUrl(s, name,"d:/images");
            	}
            }
            catch (Exception e) {
                System.err.println("Error: " + e);
                e.printStackTrace(System.err);
            }
	}
    
    
}
