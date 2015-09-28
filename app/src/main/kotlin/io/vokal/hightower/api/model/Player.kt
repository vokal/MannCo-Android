package io.vokal.hightower.api.model

import io.realm.RealmObject
import java.text.DecimalFormat


public open class Player (
       public open var STEAMID : String = "",
       public open var NAME: String = "",
       public open var KILLS : Int = 0,
       public open var Death: Int = 0,
       public open var POINTS : Int = 0,
       public open var HeadshotKill : Int = 0,
       public open var profile_image_url : String = ""
) : RealmObject()  {}

public fun Player.getKdr() : Double {
       if (Death == 0) Death = 10000000
       val doub : Double = KILLS.toDouble() / Death.toDouble()
       return doub
}
