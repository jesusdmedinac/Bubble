import 'package:flutter/material.dart';

class CategoryContainer extends StatelessWidget {
  final String name;

  const CategoryContainer({super.key, required this.name});

  @override
  Widget build(BuildContext context) => Container(
        padding: const EdgeInsets.all(8),
        child: Column(
          children: [
            CircleAvatar(
              backgroundColor: Theme.of(context).colorScheme.secondary,
              radius: 32,
              child: Text(name[0],
                  style: Theme.of(context).textTheme.bodyLarge?.copyWith(
                      color: Theme.of(context).colorScheme.onSecondary)),
            ),
            Container(
              width: 64,
              alignment: Alignment.center,
              child: Text(name, overflow: TextOverflow.ellipsis),
            )
          ],
        ),
      );
}
