package io.vokal.hightower.api.model

import io.realm.RealmObject
import java.text.DecimalFormat


public open class Player (
       public open var STEAMID : String = "",
       public open var DOMINATION : Int = 0,
       public open var KW_taunt_sniper : Int = 0,
       public open var KW_taunt_soldier : Int = 0,
       public open var KW_taunt_heavy : Int = 0,
       public open var KW_taunt_guitar_kill : Int = 0,
       public open var KW_taunt_scout: Int = 0,
       public open var KW_taunt_spy: Int = 0,
       public open var KW_taunt_demoman: Int = 0,
       public open var KW_taunt_pyro: Int = 0,
       public open var Revenge : Int = 0,
       public open var K_backstab : Int = 0,
       public open var KO_SentryGun: Int = 0,
       public open var KO_Dispenser : Int = 0,
       public open var KO_TeleporterEntrance : Int = 0,
       public open var KO_TeleporterExit : Int = 0,
       public open var NAME: String = "",
       public open var KILLS : Int = 0,
       public open var KillAssist : Int = 0,
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

public fun Player.getTauntKills() : Int {
       return KW_taunt_demoman + KW_taunt_guitar_kill
       + KW_taunt_heavy + KW_taunt_pyro + KW_taunt_scout + KW_taunt_sniper
       + KW_taunt_soldier + KW_taunt_spy
}
