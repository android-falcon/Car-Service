package com.example.carservice.feature.addTicket.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CarResponse(
    @SerializedName("CAR_ID")
    val carId: String?,
    @SerializedName("PHONE_NUMBER")
    val phoneNumber: String?,
    @SerializedName("CUSTOMER_NAME")
    val customerName: String?,
    @SerializedName("CUSTOMER_TYPE")
    val customerType: String?,
    @SerializedName("CAR_TYPE")
    val carType: String?,
    @SerializedName("CAR_COLOR")
    val carColor: String?,
    @SerializedName("CAR_MODEL")
    val carModel: String?
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(carId)
        dest.writeString(phoneNumber)
        dest.writeString(customerName)
        dest.writeString(customerType)
        dest.writeString(carType)
        dest.writeString(carColor)
        dest.writeString(carModel)
    }

    companion object CREATOR : Parcelable.Creator<CarResponse> {
        override fun createFromParcel(parcel: Parcel): CarResponse {
            return CarResponse(parcel)
        }

        override fun newArray(size: Int): Array<CarResponse?> {
            return arrayOfNulls(size)
        }
    }

}