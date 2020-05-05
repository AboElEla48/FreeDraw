package eg.foureg.freedraw.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Board (val shapes : ArrayList<Shape>) : Parcelable