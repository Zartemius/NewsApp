package com.example.artem.newsapp;

import android.content.Context;
import com.example.artem.newsapp.dataBase.AppDataBase;
import com.example.artem.newsapp.dataBase.Article;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class Downloader  {

    private Context context;
    private AppDataBase appDataBase;
    private URL url;

    public Downloader(Context context){
        this.context = context;
        appDataBase = AppDataBase.getDatabase(context);
    }

    public void ProcessXml(String urlOfResource){
        Document data = GetData(urlOfResource);
        if(data !=null){
            if(urlOfResource.equals( "https://lifehacker.com/rss")) {
                org.w3c.dom.Element root = data.getDocumentElement();
                Node channel = root.getChildNodes().item(0); // change to 1 for TechCrunch
                NodeList items = channel.getChildNodes();
                for (int i = 0; i < items.getLength(); i++) {
                    Node currentChild = items.item(i);
                    if (currentChild.getNodeName().equalsIgnoreCase("item")) {
                        Article article = new Article();
                        NodeList itemchilds = currentChild.getChildNodes();
                        for (int j = 0; j < itemchilds.getLength(); j++) {
                            Node current = itemchilds.item(j);
                            if (current.getNodeName().equalsIgnoreCase("title")) {
                                article.setTitle(current.getTextContent());
                            } else if (current.getNodeName().equalsIgnoreCase("pubDate")) {
                                article.setDate(current.getTextContent());
                            } else if (current.getNodeName().equalsIgnoreCase("link")) {
                                article.setLink(current.getTextContent());
                            } else if (current.getNodeName().equalsIgnoreCase("description")) {
                                String url = current.getTextContent();
                                if (url.startsWith("<img ")) {
                                    String cleanUrl = url.substring(url.indexOf("src=") + 5, url.indexOf("/>") - 2);
                                    article.setThumbnailUrl(cleanUrl);
                                }
                            }

                        }
                        appDataBase.articleDao().addArticle(article);
                    }
                }
            }else{
                org.w3c.dom.Element root = data.getDocumentElement();
                Node channel = root.getChildNodes().item(1);
                NodeList items = channel.getChildNodes();
                for (int i = 0; i < items.getLength(); i++) {
                    Node currentChild = items.item(i);
                    if (currentChild.getNodeName().equalsIgnoreCase("item")) {
                        Article article = new Article();
                        NodeList itemchilds = currentChild.getChildNodes();
                        for (int j = 0; j < itemchilds.getLength(); j++) {
                            Node current = itemchilds.item(j);
                            if (current.getNodeName().equalsIgnoreCase("title")) {
                                article.setTitle(current.getTextContent());
                            } else if (current.getNodeName().equalsIgnoreCase("pubDate")) {
                                article.setDate(current.getTextContent());
                            } else if (current.getNodeName().equalsIgnoreCase("link")) {
                                article.setLink(current.getTextContent());
                            } else if (current.getNodeName().equalsIgnoreCase("media:thumbnail")) {
                                String cleanUrl = current.getAttributes().item(0).getTextContent();
                                article.setThumbnailUrl(cleanUrl);
                            }
                        }
                        appDataBase.articleDao().addArticle(article);
                    }
                }
            }
        }
    }

    private Document GetData(String urlOfResource){
        try{
            url = new URL(urlOfResource);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
