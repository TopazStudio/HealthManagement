package com.flycode.healthbloom.di.module;

import android.app.Application;

import com.flycode.healthbloom.data.Config;
import com.flycode.healthbloom.data.db.Database;
import com.flycode.healthbloom.data.models.User;
import com.flycode.healthbloom.data.network.googleApi.GoogleMapsService;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DatabaseModule {

    @Provides
    public DatabaseDefinition provideDatabaseDefinition(){
        return FlowManager.getDatabase(Database.class);
    }

    @Provides
    @Singleton
    @Named("default_user")
    public User provideDefaultUser(){
        User user = SQLite.select()
                    .from(User.class)
                    .querySingle();

        if(user != null) return user;
        else return new User();
    }

    @Provides
    @Singleton
    public Config provideConfig(Application context){
        return new Config(context);
    }

    @Provides
    @Singleton
    Cache provideCache(Application context) {
        return new Cache(context.getCacheDir(), 100 * 1024 * 1024);//100 MB cache
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        return new OkHttpClient.Builder()
                .cache(cache)
                //.addInterceptor(interceptor)
                //.addInterceptor(httpLoggingInterceptor)
                //.addNetworkInterceptor(stethoInterceptor)
                //.addNetworkInterceptor(networkInterceptor)
                .build();
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    @Singleton
    RxJavaCallAdapterFactory provideRxJava2CallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    @Provides
    @Singleton
    GoogleMapsService provideGoogleMapsService(
            Config config,
            OkHttpClient okHttpClient,
            GsonConverterFactory gsonConverterFactory,
            RxJavaCallAdapterFactory rxJavaCallAdapterFactory
    ){
        return new Retrofit.Builder()
                .baseUrl(config.GOOGLE_MAPS_URL)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .client(okHttpClient)
                .build()
                .create(GoogleMapsService.class);
    }



}
