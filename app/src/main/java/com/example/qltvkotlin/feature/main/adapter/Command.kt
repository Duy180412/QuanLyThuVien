package com.example.qltvkotlin.feature.main.adapter

import androidx.room.Update
import com.example.qltvkotlin.domain.model.Chars
import com.example.qltvkotlin.domain.model.IThongTinSachThueGeneral
import com.example.qltvkotlin.domain.model.Updatable

interface Command

class ThemSachCmd(val sach: IThongTinSachThueGeneral) : Command
class ThemDocGiaCmd() : Command
class XoaSachCmd(val sach: IThongTinSachThueGeneral) : Command
class ThemThemSachRongCmd() : Command
