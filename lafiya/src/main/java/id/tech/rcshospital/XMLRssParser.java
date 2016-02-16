package id.tech.rcshospital;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.util.Log;

public class XMLRssParser {
public XMLRssParser(){
		
	}
	
	public String getXmlFromUrl(String url) {
		String xml = null;

		try {
			// defaultHttpClient
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			xml = EntityUtils.toString(httpEntity);

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// return XML
		return xml;
	}
	
	/**
	 * Getting XML DOM element
	 * @param XML string
	 * */
	public Document getDomElement(String xml){
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {

			DocumentBuilder db = dbf.newDocumentBuilder();

			InputSource is = new InputSource();
		        is.setCharacterStream(new StringReader(xml));
		        doc = db.parse(is); 

			} catch (ParserConfigurationException e) {
				Log.e("Error: ", e.getMessage());
				return null;
			} catch (SAXException e) {
				Log.e("Error: ", e.getMessage());
	            return null;
			} catch (IOException e) {
				Log.e("Error: ", e.getMessage());
				return null;
			}

	        return doc;
	}
	
	/** Getting node value
	  * @param elem element
	  */
	 public final String ambilElemenNilai( Node elem ) {
	     Node child;
	     if( elem != null){
	         if (elem.hasChildNodes()){
	             for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
	                 if( child.getNodeType() == Node.TEXT_NODE  ){
	                     return child.getNodeValue();
	                 }
	             }
	         }
	     }
	     return "";
	 }
	 
	 /**
	  * Getting node value
	  * @param Element node
	  * @param key string
	  * */
	 public String ambilNilai(Element item, String str) {		
			NodeList n = item.getElementsByTagName(str);		
			return this.ambilElemenNilai(n.item(0));
		}
	 
	 public final static String getElementValuePermalink(Node elem) {
			Node child;
			if (elem != null) {
				if (elem.hasChildNodes()) {
					for (child = elem.getFirstChild(); child != null; child = child
							.getNextSibling()) {
						// if( child.getNodeType() == Node.TEXT_NODE ){
						if (child.getNodeType() == Node.TEXT_NODE) {
							return child.getTextContent();
						}
					}
				}
			}
			return ":V :V :V";
		}
	 
	 public final static String getElementValueCDATA(Node elem) {
			Node child;
			if (elem != null) {
				if (elem.hasChildNodes()) {
					for (child = elem.getFirstChild(); child != null; child = child
							.getNextSibling()) {
						// if( child.getNodeType() == Node.TEXT_NODE ){
						if (child.getNodeType() == Node.CDATA_SECTION_NODE) {
							return child.getTextContent();
						}
					}
				}
			}
			return ":V :V :V";
		}
	 
	 public final static String getElementValueImgInCDATA(Node elem){
		 Node child;
		 if(elem != null){
			 if(elem.hasChildNodes()){
				 for(child = elem.getFirstChild(); child != null; child = child
							.getNextSibling()){
					 
					 if (child.getNodeType() == Node.CDATA_SECTION_NODE) {
						 String embeded = child.getTextContent();
							
							if(embeded.startsWith("src") && embeded.endsWith(".png")){
								embeded.substring(1, embeded.length()-1);
							}
							
							return embeded;
					 }
					 
				 }
			 }
		 }
		 
		 return ":V :V :V";
	 }
	 
	 public String getValueCDATA(Element item, String str) {
			NodeList n = item.getElementsByTagName(str);
			String ses = XMLRssParser.getElementValueCDATA(n.item(0));
			return ses;
		}
	 
	 public String getURLImage(Element item, String str){
		 NodeList n = item.getElementsByTagName(str);
		 String ses = XMLRssParser.getElementValueImgInCDATA(n.item(0));
		 return null;
	 }
	 
	 public String getIDPostFeed(Element item, String str){
		 NodeList n = item.getElementsByTagName(str);
		 String id = XMLRssParser.getElementValuePermalink(n.item(0));
		 return id;
	 }

}
