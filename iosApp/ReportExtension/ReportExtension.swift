//
//  ReportExtension.swift
//  ReportExtension
//
//  Created by Jesus Daniel Medina Cruz on 06/07/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import DeviceActivity
import SwiftUI

@main
struct ReportExtension: DeviceActivityReportExtension {
    var body: some DeviceActivityReportScene {
        // Create a report for each DeviceActivityReport.Context that your app supports.
        TotalActivityReport { totalActivity in
            TotalActivityView(totalActivity: totalActivity)
        }
        // Add more reports here...
    }
}
