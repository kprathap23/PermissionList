/*
 * Copyright (C) 2013-2014 Mike Cunningham
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.emetophobe.permissionlist;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SettingsHelper {
	private static final String SHOW_SYSTEM_APPS = "pref_show_system_apps";
	private static final String APP_SORT_ORDER = "pref_app_sort_order";
	private static final String PERM_SORT_ORDER = "pref_perm_sort_order";

	private SettingsHelper() {
	}

	public static boolean getShowSystemApps(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getBoolean(SHOW_SYSTEM_APPS, true);
	}

	public static boolean getAppSortOrder(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getBoolean(APP_SORT_ORDER, false);
	}

	public static boolean getPermissionSortOrder(Context context) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getBoolean(PERM_SORT_ORDER, false);
	}
}
