import 'package:flutter/material.dart';

class BusinessCards extends StatelessWidget {
  final String title;
  final List<Widget> children;

  const BusinessCards({super.key, required this.title, required this.children});

  @override
  Widget build(BuildContext context) => SizedBox(
        height: 404,
        child: SizedBox(
          height: 404,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Container(
                padding: const EdgeInsets.all(8),
                child:
                    Text(title, style: Theme.of(context).textTheme.titleLarge),
              ),
              SizedBox(
                height: 360,
                child: ListView(
                  scrollDirection: Axis.horizontal,
                  children: children,
                ),
              )
            ],
          ),
        ),
      );
}
