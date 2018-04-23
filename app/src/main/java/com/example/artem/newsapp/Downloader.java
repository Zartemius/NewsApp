package com.example.artem.newsapp;

import android.content.Context;
import android.util.Log;

import com.example.artem.newsapp.dataBase.AppDataBase;
import com.example.artem.newsapp.dataBase.Article;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Downloader  {

    private Context context;
    private AppDataBase appDataBase;
    private URL url;

    public Downloader(Context context){
        this.context = context;
        appDataBase = AppDataBase.getDatabase(context);
    }

    private List<Article> getListOfProcessedArticles(String urlOfResource){

        List<Article> listOfReceivedArticles = new ArrayList<>();

        Document data = GetData(urlOfResource);
        if(data !=null){
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
                        listOfReceivedArticles.add(article);
                    }
                }
            }
            return listOfReceivedArticles;
        }

    public List<Article> getDataFromDataBase(String urlOfResource){

        List<Article> listOfProcessedArticles = getListOfProcessedArticles(urlOfResource);
        List <Article> articlesFromDataBase = appDataBase.articleDao().getAll();

        Log.i("Info about article", "is empty " + articlesFromDataBase.isEmpty());

        if(!articlesFromDataBase.isEmpty()){
            for(int a = 0; a < articlesFromDataBase.size(); a++){

                if(!articlesFromDataBase.contains(listOfProcessedArticles.get(a))){
                    appDataBase.articleDao().addArticle((listOfProcessedArticles.get(a)));
                }

                if(!listOfProcessedArticles.contains(articlesFromDataBase.get(a))){
                    appDataBase.articleDao().deleteArticle(articlesFromDataBase.get(a));
                }
            }

        }else{
            for(int i = 0; i < listOfProcessedArticles.size(); i++){
                appDataBase.articleDao().addArticle(listOfProcessedArticles.get(i));
            }
        }
        return appDataBase.articleDao().getAll();
    }

    private Document GetData(String urlOfResource){

        OkHttpClient client = new OkHttpClient();

        try{
            url = new URL(urlOfResource);
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            InputStream is = response.body().byteStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document xmlDoc = documentBuilder.parse(is);
            return xmlDoc;

        }catch (IOException|ParserConfigurationException|SAXException e){
            e.printStackTrace();
            return null;
        }
    }
}
