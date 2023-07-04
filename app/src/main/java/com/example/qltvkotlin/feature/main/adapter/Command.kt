package com.example.qltvkotlin.feature.main.adapter

import com.example.qltvkotlin.domain.model.IThongTinSachThueCreate

interface Command

class ThemSachCmd(val sach: IThongTinSachThueCreate) : Command
class ThemDocGiaCmd() : Command
class XoaSachCmd(val sach: IThongTinSachThueCreate) : Command
class ThemThemSachRongCmd() : Command