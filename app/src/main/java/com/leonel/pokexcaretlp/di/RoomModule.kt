package com.leonel.pokexcaretlp.di

import android.content.Context
import androidx.room.Room
import com.leonel.pokexcaretlp.database.PokeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val DATABASE_NAME = "poke_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, PokeDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideUSerDao(db: PokeDatabase) = db.getPokemonesDao()

}