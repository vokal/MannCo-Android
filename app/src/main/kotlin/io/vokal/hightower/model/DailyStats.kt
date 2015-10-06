package io.vokal.hightower.model

class KillCount (
        var kills: Int = 0,
        var assists: Int = 0,
        var deaths: Int = 0,
        var dominations: Int = 0,
        var revenges: Int = 0
) {
    override fun toString(): String {
        return java.lang.String.format("%d/%d/%d/%d/%d (kill/death/assist/dom/rev)",
                kills, assists, deaths, dominations, revenges)
    }
}
