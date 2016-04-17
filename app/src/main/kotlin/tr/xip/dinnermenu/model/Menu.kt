package tr.xip.dinnermenu.model

import android.os.Parcel
import android.os.Parcelable

class Menu : Parcelable {
    var month: Int? = null
    var year: Int? = null
    var days: MutableList<Day>? = null

    constructor(month: Int?, year: Int?, days: MutableList<Day>?) {
        this.month = month
        this.year = year
        this.days = days
    }

    constructor(source: Parcel) {
        month = source.readInt()
        year = source.readInt()
        source.readList(days, Day::class.java.classLoader)
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(month ?: 0)
        dest.writeInt(year ?: 0)
        dest.writeList(days)
    }

    override fun describeContents(): Int = 0

    override fun toString(): String{
        return "Menu(month=$month, year=$year, days=$days)"
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Menu> {
            override fun createFromParcel(`in`: Parcel): Menu {
                return Menu(`in`)
            }

            override fun newArray(size: Int): Array<Menu?> {
                return arrayOfNulls(size)
            }
        }
    }
}