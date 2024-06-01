import 'package:flutter/material.dart';

class BusinessCard extends StatelessWidget {
  final String superTitle;
  final String title;
  final String description;

  const BusinessCard(
      {super.key,
      this.superTitle = "",
      this.title = "",
      this.description = ""});

  @override
  Widget build(BuildContext context) => Card(
        child: SizedBox(
          width: 300,
          child: Stack(
            children: [
              SizedBox(
                width: MediaQuery.of(context).size.width,
                height: 360,
                child: ClipRRect(
                  borderRadius: BorderRadius.circular(8),
                  child: Container(
                    color: Theme.of(context).colorScheme.primary,
                  ),
                ),
              ),
              Container(
                padding: const EdgeInsets.all(32),
                child: superTitle != ""
                    ? Text(
                        superTitle,
                        style: Theme.of(context).textTheme.titleLarge?.copyWith(
                            fontSize: 40,
                            color: Theme.of(context).colorScheme.onPrimary),
                      )
                    : Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(title,
                              style: Theme.of(context)
                                  .textTheme
                                  .titleLarge
                                  ?.copyWith(
                                fontSize: 36,
                                      color: Theme.of(context)
                                          .colorScheme
                                          .onPrimary)),
                          Text(description,
                              style: Theme.of(context)
                                  .textTheme
                                  .titleLarge
                                  ?.copyWith(
                                  fontSize: 24,
                                      color: Theme.of(context)
                                          .colorScheme
                                          .onPrimary)),
                        ],
                      ),
              )
            ],
          ),
        ),
      );
}
