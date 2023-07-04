package com.example.qltvkotlin.feature.main.adapter

interface HasCommandCallback {
    var onCommand: (Command) -> Unit
}