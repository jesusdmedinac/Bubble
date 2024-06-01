import 'package:flutter/material.dart';

class ChallengeCard extends StatelessWidget {
  final String title;
  final String description;
  final String image;

  const ChallengeCard(
      {super.key,
      required this.title,
      required this.description,
      required this.image});

  @override
  Widget build(BuildContext context) => SizedBox(
        width: 256,
        child: Card(
          child: Column(
            children: [
              SizedBox(
                width: MediaQuery.of(context).size.width,
                height: 102,
                child: ClipRRect(
                  borderRadius: BorderRadius.circular(8),
                  child: Image.network(
                    image,
                    fit: BoxFit.cover,
                  ),
                ),
              ),
              Container(
                padding: const EdgeInsets.fromLTRB(8, 4, 8, 4),
                alignment: Alignment.centerLeft,
                child: Text(
                  title,
                  style: Theme.of(context).textTheme.titleLarge,
                ),
              ),
              Container(
                padding: const EdgeInsets.fromLTRB(8, 4, 8, 4),
                alignment: Alignment.centerLeft,
                child: Text(
                  description,
                  style: Theme.of(context).textTheme.titleSmall,
                ),
              )
            ],
          ),
        ),
      );
}
