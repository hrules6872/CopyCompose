/*
 * Copyright (c) 2019. Héctor de Isidro - hrules6872
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hrules.composer

import android.app.*
import android.os.Bundle

class App : Application() {
  override fun onCreate() {
    super.onCreate()
    registerActivityLifecycleCallbacks(AppLifecycle())
  }
}

class AppLifecycle : Application.ActivityLifecycleCallbacks {
  companion object {
    private var foregroundActivityCount = 0

    val isAppInForeground: Boolean
      get() = foregroundActivityCount.isGreaterThanZero()
  }

  override fun onActivityCreated(
    activity: Activity?,
    savedInstanceState: Bundle?
  ) {
  }

  override fun onActivityStarted(activity: Activity) {}

  override fun onActivityResumed(activity: Activity) {
    foregroundActivityCount++
  }

  override fun onActivityPaused(activity: Activity) {
    foregroundActivityCount--
  }

  override fun onActivityStopped(activity: Activity) {}

  override fun onActivitySaveInstanceState(
    activity: Activity,
    outState: Bundle
  ) {
  }

  override fun onActivityDestroyed(activity: Activity) {}
}

private fun Int.isGreaterThanZero() = this > 0
