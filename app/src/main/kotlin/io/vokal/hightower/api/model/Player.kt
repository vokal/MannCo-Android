package io.vokal.hightower.api.model

import io.realm.RealmObject


public open class Player (
       public open var steamId : String = "",
       public open var name : String = "",
       public open var totalKills : Int = 0,
       public open var totalDeaths : Int = 0
): RealmObject() {


}

