package com.abhishek.wikisearcher.utils;

import com.abhishek.wikisearcher.Constants;
import com.abhishek.wikisearcher.models.Image;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by abhishek on 03/03/16 at 5:02 PM.
 */
public class NetworkUtil {

    public final static OkHttpClient client = new OkHttpClient();


    public static ArrayList<Image> fetchImageJson(String query) {

        ArrayList<Image> images = new ArrayList<>();

        try {
            JSONObject object;
            query = URLEncoder.encode(query, "UTF-8");
            Request request = new Request.Builder()
                    .url(Constants.URL + query)
                    .build();
            Response response = client.newCall(request).execute();
            object = new JSONObject(response.body().string());

            JSONArray jsonArray = getImageArrayFromJson(object);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                int pageId = jsonObject.getInt("pageid");
                String title = jsonObject.getString("title");
                String thumbnail = jsonObject.getJSONObject("thumbnail").getString("source");

                images.add(new Image(pageId, title, thumbnail));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return images;
    }

    private static JSONArray getImageArrayFromJson(JSONObject jsonObject) {

        JSONArray jsonArray = new JSONArray();
        try {
            JSONObject pages = jsonObject.getJSONObject("query").getJSONObject("pages");

            Iterator<?> keys = pages.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                if (pages.get(key) instanceof JSONObject) {
                    JSONObject imageObject = pages.getJSONObject(key);
                    if (imageObject.has("thumbnail")) {
                        jsonArray.put(imageObject);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;
    }

    public static String getUrlFromPageId(int pageid) {

        JSONObject object;
        Request request = new Request.Builder()
                .url(Constants.HTML_URL + pageid)
                .build();
        try {
            Response response = client.newCall(request).execute();
            object = new JSONObject(response.body().string());

            object = object.getJSONObject("query").getJSONObject("pages").getJSONObject(String.valueOf(pageid));
            return object.getString("fullurl");
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
