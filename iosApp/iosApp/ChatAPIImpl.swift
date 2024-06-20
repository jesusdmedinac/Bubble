//
//  ChatAPIImpl.swift
//  iosApp
//
//  Created by Jesus Daniel Medina Cruz on 20/06/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import ComposeApp
import GoogleGenerativeAI

struct ChatReward : Decodable  {
  let id: Int
  let title: String
  let description: String
  let image: String
  let points: Int
}

struct ChatChallenge : Decodable {
  let id: Int
  let title: String
  let description: String
  let image: String
  let rewards: [ChatReward]
}

struct ChatBody : Decodable {
  let message: String
  let challenge: ChatChallenge?
}

class ChatAPIImpl : ChatAPI {
  var challenges: [Challenge] = []
  var json: JSONDecoder = JSONDecoder()
  
  // Access your API key from your on-demand resource .plist file (see "Set up your API key" above)
  let generativeModel = GenerativeModel(
    name: "gemini-1.5-flash", 
    apiKey: APIKey.default,
    systemInstruction: ModelContent(role: "model", parts: [.text(ChatAPIKt.systemInstructions())])
  )
  
  func sendMessage(messages: [Message]) async throws -> Message {
    let reversedMessages = messages
      .reversed()
    let history: [ModelContent] = reversedMessages
      .map({ message in
        ModelContent(role: message.author, parts: [.text(message.body.message ?? "")])
      })
      .dropLast(1)
    let chat = generativeModel.startChat(
      history: history
    )
    let response = try await chat.sendMessage(
      reversedMessages.last?.body.message ?? "Empty message"
    )
    let jsonAsString = ChatAPIKt.toJsonAsString(response.text ?? """
{
  "message": "No tengo respuesta",
  "challenge": null
}
""")
    let chatBody = try json.decode(ChatBody.self, from: (jsonAsString).data(using: .utf8)!)
    let body: Body
    if (chatBody.challenge == nil) {
      body = Body(message: chatBody.message, challenge: nil)
    } else {
      let chatChallenge = chatBody.challenge!
      let challenge = Challenge(id: Int32(chatChallenge.id), title: chatChallenge.title, description: chatChallenge.description, image: chatChallenge.image, rewards: chatChallenge.rewards.map({ reward in
        Reward(id: Int32(reward.id), title: reward.title, description: reward.description, image: reward.image, points: Int32(reward.points))
      }))
      body = Body(message: chatBody.message, challenge: challenge)
    }
    return Message(author: "model", body: body)
  }
}
