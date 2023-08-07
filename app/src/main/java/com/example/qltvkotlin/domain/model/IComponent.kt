package com.example.qltvkotlin.domain.model

import com.example.qltvkotlin.domain.enumeration.FieldsId
import com.example.qltvkotlin.presentation.widget.fields.NumberFields
import com.example.qltvkotlin.presentation.widget.fields.SelectTextField

interface IComponent
interface ITextInputLayoutField
interface IPhoneNumberField
interface INumberField
interface IDateField
interface ISelectTextField
interface IHasText {
    fun getSelectField(): SelectTextField
}

interface IHasNumber {
    fun getNumberField(): NumberFields
}

interface ITextAndNumberFields : IComponent

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
    fun getFieldsID(): FieldsId
}


interface IFieldsCustom : IComponent,IHasText,IHasNumber
interface IHasPropertiesField : IHasListener {
    var iHint: IHint
    var maxEms: Int
    var inputType: Int
    var singleLine: Boolean
    var enabled: Boolean

}
interface IIntHint:IHint{
    val hint:Int
}
interface ICharsHint:IHint{
    val hint:CharSequence
}
interface IHint

interface IHasItemStart {
    val hasItem: Boolean
    var resId: Int
}

interface IHasListener {
    var hasListener: Boolean
}