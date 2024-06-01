import 'package:bubble/presentation/screen/main/challenges.cards.dart';
import 'package:flutter/material.dart';

class ProfileTab extends StatelessWidget {
  const ProfileTab({super.key});

  @override
  Widget build(BuildContext context) => Scaffold(
        appBar: AppBar(
          title: const Text("Bubble"),
        ),
        body: ListView(
          children: [
            const ListTile(
              leading: CircleAvatar(),
              title: Text("Juan Perez"),
              subtitle: Text("email@co.co"),
            ),
            SizedBox(
              height: 256,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Container(
                    padding: const EdgeInsets.all(8),
                    child: Text("Retos actuales",
                        style: Theme.of(context).textTheme.titleLarge),
                  ),
                  const ChallengesCards(),
                ],
              ),
            ),
            SizedBox(
              height: 256,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Container(
                    padding: const EdgeInsets.all(8),
                    child: Text("Retos cumplidos",
                        style: Theme.of(context).textTheme.titleLarge),
                  ),
                  const ChallengesCards(),
                ],
              ),
            ),
            SizedBox(
              height: 256,
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Container(
                    padding: const EdgeInsets.all(8),
                    child: Text("Premios obtenidos",
                        style: Theme.of(context).textTheme.titleLarge),
                  ),
                  const ChallengesCards(),
                ],
              ),
            )
          ],
        ),
      );
}
