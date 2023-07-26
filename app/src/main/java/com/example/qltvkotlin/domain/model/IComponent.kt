package com.example.qltvkotlin.domain.model

import com.example.qltvkotlin.domain.enumeration.StringId

interface IComponent
interface ITextInputLayoutField
interface IPhoneNumberField
interface IDateField
interface ISelectTextField

interface IInputLayoutField :
    IComponent, IChar, Updatable,
    HasIsValid, Validable, HasValue,
    HasError, HasFieldsId,
    IHasPropertiesField, IBackUp, HasChange, HasValueKey

interface IBackUp {
    val backUp: Any
}

interface HasValueKey {
    var key: Any
}

interface HasMaxUpdate {
    fun setMax(value: String)

}

interface IHorizontalLine : IComponent
interface IAddView : IComponent


interface IPhotoField : IImage,
    IComponent, HasIImagse,
    Updatable, HasIsValid,
    Validable, HasImage, IBackUp, HasChange


interface HasValue {
    fun getValue(): String
}

interface HasFieldsId {
    fun getFieldsID(): StringId
}

interface IHasPropertiesField : IHasListener {
    var hint: Int
    var maxEms: Int
    var inputType: Int
    var singleLine: Boolean
    var enabled: Boolean

}

interface IHasItemStart {
    val hasItem: Boolean
    var resId: Int
}

interface IHasListener {
    var hasListener: Boolean
}