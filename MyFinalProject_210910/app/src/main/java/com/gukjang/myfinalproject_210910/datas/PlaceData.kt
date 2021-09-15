package com.gukjang.myfinalproject_210910.datas

import com.google.gson.annotations.SerializedName

class PlaceData (
    var id : Int,
    @SerializedName("user_id")
    var userId : Int,
    var name : String,
    var latitude : Double,
    var longitude : Double,
    @SerializedName("is_primary")
    var isPrimary : Boolean){

}