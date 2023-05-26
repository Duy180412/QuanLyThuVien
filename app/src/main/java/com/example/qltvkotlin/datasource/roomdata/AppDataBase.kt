package com.example.qltvkotlin.datasource.roomdata


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.qltvkotlin.data.model.LoginRequest
import com.example.qltvkotlin.data.model.SachDTO

@Database(entities = [LoginRequest::class, SachDTO::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun ThuVienDao(): ThuVienDao

}