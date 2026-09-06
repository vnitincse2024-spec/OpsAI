package com.example.opsai.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.opsai.data.local.IncidentDao
import com.example.opsai.data.local.OpsAIDatabase
import com.example.opsai.data.remote.OpsAIApi
import com.example.opsai.data.repository.*
import com.example.opsai.domain.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideOpsAIApi(client: OkHttpClient): OpsAIApi {
        return Retrofit.Builder()
            .baseUrl("https://api.opsai.com/") // Fallback if BuildConfig is not yet available
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(OpsAIApi::class.java)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): OpsAIDatabase {
        return Room.databaseBuilder(
            context,
            OpsAIDatabase::class.java,
            "opsai_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideIncidentDao(db: OpsAIDatabase): IncidentDao = db.incidentDao

    @Provides
    @Singleton
    fun provideIncidentRepository(api: OpsAIApi, dao: IncidentDao): IncidentRepository {
        return IncidentRepositoryImpl(api, dao)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(api: OpsAIApi, dataStore: DataStore<Preferences>): AuthRepository {
        return AuthRepositoryImpl(api, dataStore)
    }

    @Provides
    @Singleton
    fun provideWebSocketManager(client: OkHttpClient): com.example.opsai.websocket.IncidentWebSocketManager {
        return com.example.opsai.websocket.IncidentWebSocketManager(client)
    }
}
