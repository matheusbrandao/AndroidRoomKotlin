package com.example.androidroomkotlin.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.androidroomkotlin.database.dao.AlunoDAO
import com.example.androidroomkotlin.model.Aluno

@Database(entities = [Aluno::class], version = 1, exportSchema = false)
abstract class AgendaDatabase : RoomDatabase() {
    abstract fun alunoDao(): AlunoDAO
}

object Database {

    @Volatile
    private lateinit var database: AgendaDatabase

    fun instance(context: Context): AgendaDatabase {
        synchronized(this) {
            if (::database.isInitialized)
                return database

            database = Room
                .databaseBuilder(context, AgendaDatabase::class.java,"listaalunos-database")
                .allowMainThreadQueries()
                .build()

            return database
        }
    }
}