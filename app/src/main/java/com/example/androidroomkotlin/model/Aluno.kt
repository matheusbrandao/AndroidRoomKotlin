package com.example.androidroomkotlin.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
class Aluno (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome: String,
    val telefone: String,
    val email: String) : Parcelable