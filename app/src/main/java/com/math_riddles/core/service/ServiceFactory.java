package com.math_riddles.core.service;

import com.math_riddles.BuildConfig;
import com.math_riddles.common.AppPreferences;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author phuocns on 26/11/2018.
 */

public class ServiceFactory {
    private static volatile RestApi mRestApi = null;
    private static volatile OkHttpClient mOkHttpClient = null;
    public static String SERVER_VN_URL = "https://vnapi.bbitex.com/api/";
    public static String SERVER_MALAY_URL = "https://myapi.bbitex.com/api/";

    private static String mCurrentApi = SERVER_VN_URL;

    private static Retrofit.Builder retrofitBuilder;

    public synchronized static RestApi getServiceInstance() {
        if (mRestApi == null) {
            mRestApi = ServiceFactory.create(mCurrentApi);
        }

        return mRestApi;
    }

    private static RestApi create(String baseUrl) {

        Gson gson = new GsonBuilder().serializeNulls().create();
        RxJava2CallAdapterFactory callAdapter = RxJava2CallAdapterFactory.create();

        OkHttpClient client = createHttpClient();

        retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.baseUrl(baseUrl);
        retrofitBuilder.client(client);

        retrofitBuilder.addConverterFactory(GsonConverterFactory.create(gson));
        retrofitBuilder.addCallAdapterFactory(callAdapter);

        Retrofit retrofit = retrofitBuilder.build();

        return retrofit.create(RestApi.class);
    }

    public static synchronized void changeApiBaseUrl(String newApiBaseUrl) {
        mRestApi = create(newApiBaseUrl);
    }

    private static OkHttpClient createHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpInterceptor = new HttpLoggingInterceptor();
        httpClient.addInterceptor(httpInterceptor);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor interceptorBody = new HttpLoggingInterceptor();
            interceptorBody.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(interceptorBody);
        }
        httpClient.addInterceptor(chain -> {
            Request request = chain.request();
            Request newRequest;
            newRequest = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + AppPreferences.getInstance().getToken())
                    .build();
            return chain.proceed(newRequest);
        });

        httpClient.connectTimeout(120, TimeUnit.SECONDS);
        httpClient.readTimeout(120, TimeUnit.SECONDS);
        httpClient.writeTimeout(120, TimeUnit.SECONDS);
        httpClient.hostnameVerifier((hostname, session) -> true);
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {

                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            }};

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts,
                    new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext
                    .getSocketFactory();

            httpClient = httpClient.sslSocketFactory(sslSocketFactory);
            httpClient = httpClient.hostnameVerifier((hostname, session) -> true);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return httpClient.build();
    }
}
