package com.codingblocks.suraksha;

import android.content.Context;
import android.util.Log;

import com.codingblocks.suraksha.Models.RouteDetails;
import com.codingblocks.suraksha.Models.TripResponse;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UtilFunctions {
    public static final String TAG="TAG";

    public static void fetchRouteDetails(Context context, String longitude, String latitude) {
        String baseUrl = "https://api.tomtom.com/routing/1/calculateRoute/";
        String latitudes="28.677050,77.112090:";
        String longitudes = "28.645380,77.203160/";
        String json = "jsonp?key=";
        String apiKey = context.getString(R.string.api_key);
        String url = baseUrl+latitudes+longitudes+json+apiKey;

        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Gson gson = new Gson();
                        String result1 = response.body().string();
                        String result = result1.replace("callback(", "");
                        result = result.substring(0, result.length()-1);
                        RouteDetails routeDetails = gson.fromJson(result, RouteDetails.class);
                        Log.d(TAG, "onResponse: "+routeDetails.getRoutes()[0].getSummary().getTravelTimeInSeconds());
                    }
                });
    }
}
