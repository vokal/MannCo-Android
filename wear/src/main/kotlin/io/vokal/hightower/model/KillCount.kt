package io.vokal.hightower.model

import io.realm.*
import io.realm.annotations.*

class RKillCount (
        var kills: Int = 0,
        var assists: Int = 0,
        var deaths: Int = 0,
        var dominations: Int = 0,
        var revenges: Int = 0
) {
    public fun toKillCount(): KillCount {
        return KillCount(
            kills,
            assists,
            deaths,
            dominations,
            revenges)
    }
}

@RealmClass
open class KillCount (
        open var kills: Int = 0,
        open var assists: Int = 0,
        open var deaths: Int = 0,
        open var dominations: Int = 0,
        open var revenges: Int = 0
):RealmObject() {}
