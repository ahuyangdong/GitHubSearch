package com.github.search.network;

import android.content.Context;
import android.widget.Toast;

import com.github.search.R;
import com.github.search.network.result.BaseResult;
import com.github.search.util.Constant;
import com.github.search.util.NetworkUtil;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Retrofit请求类
 */
public class RetrofitRequest {
    private static int TIME_OUT = 30;

    // 自定义一个信任所有证书的TrustManager，添加SSLSocketFactory的时候要用到
    private static X509TrustManager trustAllCert = new X509TrustManager() {
        @Override
        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }
    };
    private static SSLSocketFactory sslSocketFactory = new SSLSocketFactoryCompat(trustAllCert);

    // httpclient
    public static OkHttpClient client = new OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCert).
                    connectTimeout(TIME_OUT, TimeUnit.SECONDS).
                    readTimeout(TIME_OUT, TimeUnit.SECONDS).
                    writeTimeout(TIME_OUT, TimeUnit.SECONDS).build();

    // 网络框架
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Constant.URL_BASE)
            .client(client)
            .build();

    /**
     * 发送GET网络请求
     *
     * @param url 请求地址
     */
    public static <T extends BaseResult> void sendGetRequest(String url, final Class<T> clazz, final ResultHandler<T> resultHanlder) {
        // 判断网络连接状况
        if (resultHanlder.isNetDisconnected()) {
            resultHanlder.onAfterFailure();
            return;
        }
        GetRequest getRequest = retrofit.create(GetRequest.class);

        // 构建请求
        Call<ResponseBody> call = getRequest.getUrl(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                resultHanlder.onBeforeResult();
                try {
                    ResponseBody body = response.body();
                    if (body == null) {
                        resultHanlder.onServerError();
                        return;
                    }
                    String string = body.string();
                    T t = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create().fromJson(string, clazz);

                    resultHanlder.onResult(t);
                } catch (IOException e) {
                    e.printStackTrace();
                    resultHanlder.onFailure(e);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                resultHanlder.onFailure(t);
                resultHanlder.onAfterFailure();
            }
        });
    }

    public static abstract class ResultHandler<T> {
        Context context;

        public ResultHandler(Context context) {
            this.context = context;
        }

        /**
         * 判断网络是否未连接
         *
         * @return
         */
        public boolean isNetDisconnected() {
            return NetworkUtil.isNetDisconnected(context);
        }

        /**
         * 请求成功之前
         */
        public abstract void onBeforeResult();

        /**
         * 请求成功时
         *
         * @param t 结果数据
         */
        public abstract void onResult(T t);

        /**
         * 服务器出错
         */
        public void onServerError() {
            // 服务器处理出错
            Toast.makeText(context, R.string.net_server_error, Toast.LENGTH_SHORT).show();
        }

        /**
         * 请求失败后的处理
         */
        public abstract void onAfterFailure();

        /**
         * 请求失败时的处理
         *
         * @param t
         */
        public void onFailure(Throwable t) {
            if (t instanceof SocketTimeoutException || t instanceof ConnectException) {
                // 连接异常
                if (NetworkUtil.isNetworkConnected(context)) {
                    // 服务器连接出错
                    Toast.makeText(context, R.string.net_server_connected_error, Toast.LENGTH_SHORT).show();
                } else {
                    // 手机网络不通
                    Toast.makeText(context, R.string.net_not_connected, Toast.LENGTH_SHORT).show();
                }
            } else if (t instanceof Exception) {
                // 功能异常
                Toast.makeText(context, R.string.net_unknown_error, Toast.LENGTH_SHORT).show();
            }
        }

    }
}
