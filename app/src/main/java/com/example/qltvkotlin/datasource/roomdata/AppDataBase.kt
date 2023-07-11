package com.example.qltvkotlin.datasource.roomdata


import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.qltvkotlin.data.model.DocGiaDTO
import com.example.qltvkotlin.data.model.LoginRequest
import com.example.qltvkotlin.data.model.MuonThue
import com.example.qltvkotlin.data.model.SachDTO
import com.example.qltvkotlin.data.model.ThongTinThue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@TypeConverters(Converters::class)
@Database(
    entities = [LoginRequest::class, SachDTO::class, DocGiaDTO::class, MuonThue::class],
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

class Converters {
    @TypeConverter
    fun fromList(list: List<ThongTinThue>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun toList(jsonString: String): List<ThongTinThue> {
        val type = object : TypeToken<List<ThongTinThue>>() {}.type
        val gson = Gson()
        return gson.fromJson(jsonString, type)
    }
}

