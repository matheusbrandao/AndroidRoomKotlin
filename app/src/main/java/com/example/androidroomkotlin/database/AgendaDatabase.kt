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
    private const val NOME_DO_BANCO = "listaalunos-database"

    fun instance(context: Context): AgendaDatabase {
        synchronized(this) {
            if (::database.isInitialized)
                return database

            database = Room
                .databaseBuilder(context, AgendaDatabase::class.java, NOME_DO_BANCO)
                .allowMainThreadQueries()
                .build()

            return database
        }
    }
}