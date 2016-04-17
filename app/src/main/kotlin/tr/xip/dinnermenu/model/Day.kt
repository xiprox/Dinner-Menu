package tr.xip.dinnermenu.model

import android.os.Parcel
import android.os.Parcelable

class Day : Parcelable {
    var day_name: String? = null
    var day: Int? = null
    var main: String? = null
    var side: String? = null
    var soup: String? = null
    var dessert: String? = null
    var salad: String? = null
    var appetizer: String? = null

    constructor(day_name: String?, day: Int?, main: String?, side: String?, soup: String?, dessert: String?, salad: String?, appetizer: String?) {
        this.day_name = day_name
        this.day = day
        this.main = main
        this.side = side
        this.soup = soup
        this.dessert = dessert
        this.salad = salad
        this.appetizer = appetizer
    }

    constructor(source: Parcel) {
        this.day_name = source.readString()
        this.day = source.readInt()
        this.main = source.readString()
        this.side = source.readString()
        this.soup = source.readString()
        this.dessert = source.readString()
        this.salad = source.readString()
        this.appetizer = source.readString()
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(day_name)
        dest.writeInt(day ?: 0)
        dest.writeString(main)
        dest.writeString(side)
        dest.writeString(soup)
        dest.writeString(dessert)
        dest.writeString(salad)
        dest.writeString(appetizer)
    }

    override fun describeContents(): Int = 0

    override fun toString(): String{
        return "Day(day_name=$day_name, day=$day, main=$main, side=$side, soup=$soup, dessert=$dessert, salad=$salad, appetizer=$appetizer)"
    }

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<Day> {
            override fun createFromParcel(`in`: Parcel): Day {
                return Day(`in`)
            }

            override fun newArray(size: Int): Array<Day?> {
                return arrayOfNulls(size)
            }
        }
    }
}