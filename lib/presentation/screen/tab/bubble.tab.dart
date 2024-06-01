import 'package:bubble/presentation/screen/tab/bubble/message.dart';
import 'package:flutter/material.dart';

class BubbleTab extends StatefulWidget {
  const BubbleTab({super.key});

  @override
  State<StatefulWidget> createState() => _BubbleTabState();
}

class _BubbleTabState extends State<BubbleTab> {
  final List<Message> _messages = [
    const Message(author: "Bubble", body: "body", sentAt: "")
  ];
  final TextEditingController _messageController = TextEditingController();

  @override
  Widget build(BuildContext context) => Scaffold(
        appBar: AppBar(
          title: const Text("Bubble"),
        ),
        body: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Expanded(
                child: ListView(
                    children: _messages.map((e) => MessageCard(e)).toList())),
            Container(
                child: Row(
              children: [
                const SizedBox(
                  width: 16,
                ),
                Expanded(
                    child: TextField(
                  controller: _messageController,
                  textInputAction: TextInputAction.done,
                  decoration: const InputDecoration(
                      hintText: 'Mensaje', border: OutlineInputBorder()),
                  onSubmitted: (value) => {
                    setState(() {
                      _messages.add(Message(author: "user", body: value, sentAt: ""));
                      _messageController.text = "";
                    })
                  },
                )),
                IconButton(onPressed: () => {
                  setState(() {
                    _messages.add(Message(author: "user", body: _messageController.text, sentAt: ""));
                    _messageController.text = "";
                  })
                }, icon: const Icon(Icons.send)),
              ],
            ))
          ],
        ),
      );

  Row MessageCard(Message message) => Row(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          SizedBox(
            width: 64,
            height: 64,
            child: Icon(
                message.author == "user" ? Icons.person : Icons.bubble_chart),
          ),
          Container(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Container(
                  height: 64,
                  alignment: Alignment.centerLeft,
                  child: Text(
                    message.author,
                    style: Theme.of(context)
                        .textTheme
                        .titleLarge
                        ?.copyWith(fontWeight: FontWeight.w500),
                  ),
                ),
                Text(
                  message.body,
                  style: Theme.of(context).textTheme.bodyLarge,
                ),
              ],
            ),
          )
        ],
      );
}
