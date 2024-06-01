import 'package:bubble/presentation/screen/tab/bubble/message.dart';

class BubbleState {
  List<Message> messages = [
    const Message(author: "Bubble", body: "body", sentAt: "")
  ];

  BubbleState(this.messages);

  BubbleState copyWith({required List<Message> messages}) {
    return BubbleState(messages);
  }
}



class BubbleViewModel {

}