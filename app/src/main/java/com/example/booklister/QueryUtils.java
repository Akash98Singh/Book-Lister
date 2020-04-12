package com.example.booklister;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class QueryUtils {
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();


    public static ArrayList<Book> fetchBookData(String requestUrl){
        URL url = createUrl(requestUrl);
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        }
        catch (IOException e){
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        ArrayList<Book> books = extractFeatures(jsonResponse);
        return books;
    }

    public static ArrayList<Book> extractFeatures(String jsonResponse) {
        ArrayList<Book> books = new ArrayList<>();
        if(TextUtils.isEmpty(jsonResponse)){
            return null;
        }

        try{
            JSONObject rootObject = new JSONObject(jsonResponse);
            JSONArray items = rootObject.getJSONArray("items");
            Log.i(LOG_TAG,"Length = " + items.length());
            for(int i = 0; i<items.length();i++){
                JSONObject temp = items.getJSONObject(i);
                JSONObject volumeInfo = temp.optJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                JSONArray authorArray = volumeInfo.optJSONArray("authors");
                if(authorArray==null)
                    continue;
                String author = authorArray.get(0).toString();
                double averageRating = volumeInfo.optDouble("averageRating",0.0);
                int pageCount = volumeInfo.optInt("pageCount", 0);
                if(title==null || authorArray == null || pageCount == 0 || averageRating==0.0)
                    continue;
                books.add(new Book(title,author,pageCount,averageRating));
            }
        }
        catch (JSONException e){
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return books;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if(url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else{
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }
        catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);

        }
        finally {
            if(inputStream != null){
                inputStream.close();
            }
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line!=null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String requestUrl) {
        URL url = null;
        try {
            url = new URL(requestUrl);
        }
        catch (MalformedURLException e){
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }
}
