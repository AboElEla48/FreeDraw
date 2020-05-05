package eg.foureg.freedraw.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Board (val boardKey : String, var name: String, val shapes : ArrayList<Shape>) : Parcelable