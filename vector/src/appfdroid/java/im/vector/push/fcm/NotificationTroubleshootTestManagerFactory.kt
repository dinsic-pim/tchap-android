/*
 * Copyright 2018 New Vector Ltd
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
package im.vector.push.fcm

import androidx.fragment.app.Fragment
import im.vector.Matrix
import im.vector.fragments.troubleshoot.*
import im.vector.push.fcm.troubleshoot.TestAutoStartBoot
import im.vector.push.fcm.troubleshoot.TestBackgroundRestrictions
import im.vector.push.fcm.troubleshoot.TestBatteryOptimization
import org.matrix.androidsdk.MXSession

class NotificationTroubleshootTestManagerFactory {

    companion object {
        fun createTestManager(fragment: Fragment, session: MXSession?): NotificationTroubleshootTestManager {
            val mgr = NotificationTroubleshootTestManager(fragment)
            mgr.addTest(TestSystemSettings(fragment))
            if (session != null) {
                mgr.addTest(TestAccountSettings(fragment, session))
            }
            mgr.addTest(TestDeviceSettings(fragment))
            if (session != null) {
                mgr.addTest(TestBingRulesSettings(fragment, session))
            }
            // mgr.addTest(TestNotificationServiceRunning(fragment))
            // mgr.addTest(TestServiceRestart(fragment))
            mgr.addTest(TestAutoStartBoot(fragment))
            mgr.addTest(TestBackgroundRestrictions(fragment))

            // Test the Battery Optimization for RealTime sync mode
            val pushManager = Matrix.getInstance(fragment.context).pushManager
            if (pushManager.idFdroidSyncModeOptimizedForRealTime()) {
                mgr.addTest(TestBatteryOptimization(fragment))
            }
            return mgr
        }
    }

}