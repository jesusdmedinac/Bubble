//
//  UsageAPIImpl.swift
//  iosApp
//
//  Created by Jesus Daniel Medina Cruz on 11/07/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import ComposeApp

class UsageAPIImpl : UsageAPI {
  func getDailyUsageStatsForWeek() -> [DailyUsageStats] {
    return []
  }
  
  func getUsageEvents(beginTime: Int64, endTime: Int64) -> KotlinMutableDictionary<NSString, KotlinLong> {
    return KotlinMutableDictionary()
  }
  
  func hasPermission() -> Bool {
    return false
  }
  
  func onHasUsagePermissionStateChange(onChange: @escaping (HasUsagePermissionState) -> Void) async throws {
  }
  
  func packagesToFilter() -> [String] {
    return []
  }
  
  func queryUsageStats(beginTime: Int64, endTime: Int64) -> [UsageStats] {
    return []
  }
  
  func requestUsagePermission() {
  }
  
}
