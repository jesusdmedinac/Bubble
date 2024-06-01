import 'package:bubble/presentation/screen/main/challenge.card.dart';
import 'package:flutter/cupertino.dart';

class ChallengesCards extends StatelessWidget {
  const ChallengesCards({super.key});

  @override
  Widget build(BuildContext context) => SizedBox(
        height: 188,
        child: ListView(
          scrollDirection: Axis.horizontal,
          children: const [
            SizedBox(
              width: 16,
            ),
            ChallengeCard(
              title: "Reto",
              description: "Descripción",
              image: "https://picsum.photos/250?image=9",
            ),
            ChallengeCard(
              title: "Reto",
              description: "Descripción",
              image: "https://picsum.photos/250?image=9",
            ),
            ChallengeCard(
              title: "Reto",
              description: "Descripción",
              image: "https://picsum.photos/250?image=9",
            ),
            ChallengeCard(
              title: "Reto",
              description: "Descripción",
              image: "https://picsum.photos/250?image=9",
            ),
            ChallengeCard(
              title: "Reto",
              description: "Descripción",
              image: "https://picsum.photos/250?image=9",
            ),
            SizedBox(
              width: 16,
            ),
          ],
        ),
      );
}
