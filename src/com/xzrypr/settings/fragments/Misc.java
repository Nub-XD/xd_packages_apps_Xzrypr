/*
 * Copyright (C) 2019-2021 The xzryprOS Project
 * Copyright (C) 2021 xdroid, xyzprjkt
 * Copyright (C) 2023 xdclo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.xzrypr.settings.fragments;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.os.Handler;
import java.net.InetAddress;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceGroup;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceCategory;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceFragment;
import androidx.preference.SwitchPreference;
import com.android.settings.R;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settingslib.search.SearchIndexable;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;
import java.util.Locale;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;
import com.android.internal.logging.nano.MetricsProto;

import com.xzrypr.settings.preferences.SystemPropSwitchPreference;

public class Misc extends SettingsPreferenceFragment
             implements Preference.OnPreferenceChangeListener {

    private static final String PREF_ADBLOCK = "persist.xd.hosts_block";

    private Handler mHandler = new Handler();

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        addPreferencesFromResource(R.xml.xzrypr_settings_misc);
        PreferenceScreen prefSet = getPreferenceScreen();
        final Resources res = getResources();
        final PreferenceScreen prefScreen = getPreferenceScreen();

        findPreference(PREF_ADBLOCK).setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (PREF_ADBLOCK.equals(preference.getKey())) {
            // Flush the java VM DNS cache to re-read the hosts file.
            // Delay to ensure the value is persisted before we refresh
            mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        InetAddress.clearDnsCache();
                    }
            }, 1000);
            return true;
        }
            return false;
    }



   @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.XD_ZONE;
    }
}