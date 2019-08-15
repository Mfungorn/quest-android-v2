package com.quest.app.features.quests.domain.model

import android.os.Parcel
import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonProperty
import com.quest.app.features.profile.domain.model.User
import java.util.*

data class Quest(
    @JsonProperty(value = "id") val id: Long,
    @JsonProperty(value = "title") val title: String,
    @JsonProperty(value = "description") val description: String,
    @JsonProperty(value = "date") val date: Date,
    @JsonProperty(value = "xp") val xp: Int,
    @JsonProperty(value = "status") val status: String,
    @JsonProperty(value = "author") val author: User,
    @JsonProperty(value = "target") val target: User
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        Date(parcel.readLong()),
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(User::class.java.classLoader),
        parcel.readParcelable(User::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeLong(date.time)
        parcel.writeInt(xp)
        parcel.writeString(status)
        parcel.writeParcelable(author, 0)
        parcel.writeParcelable(target, 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Quest> {
        override fun createFromParcel(parcel: Parcel): Quest {
            return Quest(parcel)
        }

        override fun newArray(size: Int): Array<Quest?> {
            return arrayOfNulls(size)
        }
    }
}