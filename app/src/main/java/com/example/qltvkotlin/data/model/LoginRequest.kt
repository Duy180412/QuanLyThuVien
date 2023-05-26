package com.example.qltvkotlin.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login")
data class LoginRequest(
    @PrimaryKey val idlogin: String,
    var pass: String
)