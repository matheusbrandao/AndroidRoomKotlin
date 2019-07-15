package com.example.androidroomkotlin.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.androidroomkotlin.model.Aluno

@Dao
interface AlunoDAO {

    @Query("SELECT * from aluno where id = :id LIMIT 1")
    fun loadUserById(id: Long): Aluno

    @Query("SELECT * FROM aluno ORDER BY id DESC")
    fun all(): LiveData<List<Aluno>>

    @Insert
    fun add(vararg aluno: Aluno)

    @Update
    fun update(aluno: Aluno)

    @Delete
    fun delete(aluno: Aluno)
}