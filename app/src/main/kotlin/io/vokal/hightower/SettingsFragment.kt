package io.vokal.hightower

import android.graphics.Color
import android.os.Bundle
import android.support.v7.preference.*
import android.view.*
import co.mann.backpack.SteamAPI
import rx.schedulers.Schedulers
import timber.log.Timber


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(p0: Bundle?, p1: String?) {
        addPreferencesFromResource(R.xml.settings)

        var username = findPreference("username_setting")

        var prefs = PreferenceManager.getDefaultSharedPreferences(activity)
        var u = prefs.getString("username_setting", null)
        if (u != null) {
            username.summary = u;
        }

        username.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, key ->
            preference.summary = "$key"

            Timber.d("username: %s", key);
            SteamAPI.instance(activity).getSteamId(key as String)
                    .subscribeOn(Schedulers.io())
                    .doOnNext { id -> prefs.edit()
                                .putLong(getString(R.string.settings_key_steam_id), id)
                                .apply()
                    }
                    .compose(SteamAPI.steamIdToCommunityId())
                    .subscribe ({ id -> prefs.edit()
                                .putString(getString(R.string.settings_key_steam_id), id)
                                .apply()
                        Timber.d("Community ID: %s", id)
                    }, { e -> Timber.e(e, "failed to get ID for username: %s", key) })
            true
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v = super.onCreateView(inflater, container, savedInstanceState)
        v.setBackgroundColor(Color.WHITE)
        return v
    }
}
