//
//  NetworkAPIImpl.swift
//  iosApp
//
//  Created by Jesus Daniel Medina Cruz on 11/07/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Network
import SwiftUI
import ComposeApp

class NetworkAPIImpl : NetworkAPI, ObservableObject {
  static let shared = NetworkAPIImpl()

  private let monitor: NWPathMonitor
  
  @Published var isConnectedPublished = false
  @Published var upstreamBandWidthKbpsPublished = 0
  @Published var downstreamBandWidthKbpsPublished = 0

  private init() {
    monitor = NWPathMonitor()
  
    monitor.pathUpdateHandler = { [weak self] path in
      let isConnected = path.status != .unsatisfied
      self?.isConnectedPublished = isConnected
    }
    
    let queue = DispatchQueue(label: "NetworkConnectivityMonitor")
    monitor.start(queue: queue)
  }
  
  func isConnected(onChange: @escaping (KotlinBoolean) -> Void) async throws {
    $isConnectedPublished
      .sink { onChange(KotlinBoolean(bool: $0)) }
  }
  
  func upstreamBandWidthKbps(onChange: @escaping (KotlinInt) -> Void) async throws {
    $upstreamBandWidthKbpsPublished
      .sink { onChange(KotlinInt(int: Int32($0))) }
  }
  
  func downstreamBandWidthKbps(onChange: @escaping (KotlinInt) -> Void) async throws {
    $downstreamBandWidthKbpsPublished
      .sink { onChange(KotlinInt(int: Int32($0))) }
  }
}
