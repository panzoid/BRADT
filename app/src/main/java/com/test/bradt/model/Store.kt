package com.test.bradt.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Store {

    @SerializedName("address")
    @Expose
    var address: String? = null
    @SerializedName("city")
    @Expose
    var city: String? = null
    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("latitude")
    @Expose
    var latitude: Double = 0.toDouble()
    @SerializedName("zipcode")
    @Expose
    var zipcode: String? = null
    @SerializedName("storeLogoURL")
    @Expose
    var storeLogoURL: String? = null
    @SerializedName("phone")
    @Expose
    var phone: String? = null
    @SerializedName("longitude")
    @Expose
    var longitude: Double = 0.toDouble()
    @SerializedName("storeID")
    @Expose
    var storeID: String? = null
    @SerializedName("state")
    @Expose
    var state: String? = null

    val formattedName: String
        get() = StringBuilder().append(name).append(" #").append(storeID).toString()

    val formattedAddress: String
        get() {
            val builder = StringBuilder()
            builder.append(address).append("\n")
            builder.append(city).append(", ").append(state).append(" ").append(zipcode).append("\n")
            builder.append("(").append(latitude).append(", ").append(longitude).append(")")
            return builder.toString()
        }

    fun withAddress(address: String): Store {
        this.address = address
        return this
    }

    fun withCity(city: String): Store {
        this.city = city
        return this
    }

    fun withName(name: String): Store {
        this.name = name
        return this
    }

    fun withLatitude(latitude: Double): Store {
        this.latitude = latitude
        return this
    }

    fun withZipcode(zipcode: String): Store {
        this.zipcode = zipcode
        return this
    }

    fun withStoreLogoURL(storeLogoURL: String): Store {
        this.storeLogoURL = storeLogoURL
        return this
    }

    fun withPhone(phone: String): Store {
        this.phone = phone
        return this
    }

    fun withLongitude(longitude: Double): Store {
        this.longitude = longitude
        return this
    }

    fun withStoreID(storeID: String): Store {
        this.storeID = storeID
        return this
    }

    fun withState(state: String): Store {
        this.state = state
        return this
    }
}