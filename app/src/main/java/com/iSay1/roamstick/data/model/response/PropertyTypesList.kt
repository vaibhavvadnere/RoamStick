package com.iSay1.roamstick.data.model.response

import android.os.Parcel
import android.os.Parcelable

data class PropertyTypesList(
    val Id: Int?,
    val AmenitiesMappingData: List<AmenitiesData>?,
    val ChargesPerUnit: String?,
    val ComplimentoryMappingData: List<ComplimentaryData>?,
    val IsActive: String?,
    val NumberOfUnits: String?,
    val PropertyId: String?,
    val PropertyTypeId: String?,
    val TypeName: String?,
    val UnitChargeType: String?,
    val ValidFrom: String?,
    val ValidTo: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.createTypedArrayList(AmenitiesData),
        parcel.readString(),
        parcel.createTypedArrayList(ComplimentaryData),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(Id)
        parcel.writeTypedList(AmenitiesMappingData)
        parcel.writeString(ChargesPerUnit)
        parcel.writeTypedList(ComplimentoryMappingData)
        parcel.writeString(IsActive)
        parcel.writeString(NumberOfUnits)
        parcel.writeString(PropertyId)
        parcel.writeString(PropertyTypeId)
        parcel.writeString(TypeName)
        parcel.writeString(UnitChargeType)
        parcel.writeString(ValidFrom)
        parcel.writeString(ValidTo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PropertyTypesList> {
        override fun createFromParcel(parcel: Parcel): PropertyTypesList {
            return PropertyTypesList(parcel)
        }

        override fun newArray(size: Int): Array<PropertyTypesList?> {
            return arrayOfNulls(size)
        }
    }
}

