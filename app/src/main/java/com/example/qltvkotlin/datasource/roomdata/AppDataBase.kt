package com.example.qltvkotlin.datasource.roomdata


import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.qltvkotlin.data.model.DocGiaDTO
import com.example.qltvkotlin.data.model.LoginRequest
import com.example.qltvkotlin.data.model.SachDTO

@Database(
    entities = [LoginRequest::class, SachDTO::class, DocGiaDTO::class],
    version = 1,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun ThuVienDao(): ThuVienDao

    companion object {
         private var thuVienData: AppDataBase? = null

        fun init(context: Application) {
            thuVienData = databaseBuilder(
                context,
                AppDataBase::class.java, "data"
            )
                .build()
        }

        fun getInstance(): ThuVienDao {
            return thuVienData!!.ThuVienDao()
        }
    }
}

