package com.example.qltvkotlin.domain.model

interface IAccount : HasId, HasPassword
interface HasId {
    val id: CharSequence
}

interface HasPassword {
    val password: CharSequence
}