package com.blongho.currencyconverter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.io.IOException;

/**
 * The type Converter.
 */
public class Converter {

  private TextView updateView;

  static {
    System.loadLibrary("native-lib");
  }

  private native String stringFromJNI();

  private static OkHttpClient client = new OkHttpClient();

  private static final String TAG = "Converter";
  private CurrencyCode from;
  private CurrencyCode to;

  /**
   * Instantiates a new Converter.
   *
   * @param from the from
   * @param to the to
   */
  public Converter(CurrencyCode from, CurrencyCode to) {
    this.from = from;
    this.to = to;
  }

  private String requestString() {
    return "https://free.currconv.com/api/v7/convert?q=" + from.name() + "_" + to.name()
        + "&compact=ultra&apiKey=" + stringFromJNI();
  }

  /**
   * Make request.
   *
   * @param context the context
   */
  public void makeRequest(final Context context) {
    Request request = new Request.Builder()
        .url(requestString())
        .build();
    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Request request, IOException e) {
        Log.e(TAG, "onFailure: " + e.getMessage());
      }

      @Override
      public void onResponse(Response response) {
        final String value;
        try {
          value = response.body().string().split(":")[1];
          Log.d(TAG, "onResponse: " + Double.parseDouble(value.substring(0, value.length() - 1)));
          final double rate = Double.parseDouble(value.substring(0, value.length() - 1));
          new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
              updateView.setText(String.valueOf(rate));
            }
          });
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    });
  }

  void setTextView(TextView tv) {
    this.updateView = tv;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Converter{");
    sb.append("from='").append(from).append('\'');
    sb.append(", to='").append(to).append('\'');
    sb.append('}');
    return sb.toString();
  }
}

