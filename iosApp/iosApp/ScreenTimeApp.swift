//
//  ScreenTimeApp.swift
//  iosApp
//
//  Created by Jesus Daniel Medina Cruz on 06/07/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import FamilyControls
import DeviceActivity

//@main
struct ScreenTimeApp: App {
  let center = AuthorizationCenter.shared
      //1 added next line here
      @State private var context: DeviceActivityReport.Context = .init(rawValue: "Total Activity")
      @State private var filter = DeviceActivityFilter(
          segment: .daily(
              during: Calendar.current.dateInterval(
                 of: .day, for: .now
              )!
          ),
          users: .all,
          devices: .init([.iPhone, .iPad])
      )
  var body: some Scene {
    WindowGroup {
      ZStack {
        // 2 will do next logic - add
        DeviceActivityReport(context, filter: filter)
      }
      .onAppear {
        Task {
          do {
            try await center.requestAuthorization(for: .individual)
          } catch {
            print("Failed to enroll Aniyah with error: \(error)")
          }
        }
      }
    }
  }
}
