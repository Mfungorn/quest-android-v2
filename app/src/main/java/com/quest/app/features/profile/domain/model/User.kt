package com.quest.app.features.profile.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty

data class User (
    @JsonProperty(value = "id") val id: Long,
    @JsonProperty(value = "name") val name: String,
    @JsonProperty(value = "login") val login: String,
    @JsonProperty(value = "email") val email: String,
    @JsonProperty(value = "imageUrl") val imageUrl: String?,
    @JsonProperty(value = "clientToken") var clientToken: String?,
    @JsonProperty(value = "currentXp") val currentXp: Int,
    @JsonProperty(value = "level") val level: Int,
    @JsonProperty(value = "provider") val provider: String,
    @JsonProperty(value = "providerId") var providerId: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(name)
        parcel.writeString(login)
        parcel.writeString(email)
        parcel.writeString(imageUrl)
        parcel.writeString(clientToken)
        parcel.writeInt(currentXp)
        parcel.writeInt(level)
        parcel.writeString(provider)
        parcel.writeString(providerId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}