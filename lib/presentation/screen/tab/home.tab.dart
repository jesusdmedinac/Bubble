import 'package:bubble/presentation/screen/main/challenges.cards.dart';
import 'package:bubble/presentation/screen/tab/home/business.card.dart';
import 'package:bubble/presentation/screen/tab/home/business.cards.dart';
import 'package:bubble/presentation/screen/tab/home/category.container.dart';
import 'package:flutter/material.dart';


class HomeTab extends StatefulWidget {
  const HomeTab({super.key});

  @override
  State<StatefulWidget> createState() => _HomeTabState();
}

class _HomeTabState extends State<HomeTab> {
  @override
  Widget build(BuildContext context) => Scaffold(
      appBar: AppBar(
        title: const Text("Bubble"),
      ),
      body: homeBody());

  ListView homeBody() => ListView(
        children: [
          Categories(),
          ContinueChallenge(),
          ForYou(),
          const BusinessCards(
            title: "Donadores",
            children: [
              SizedBox(
                width: 16,
              ),
              BusinessCard(
                superTitle: "Conoce a las empresas del símbolo de la burbuja",
              ),
              BusinessCard(
                title: "Starbucks",
                description:
                    "Gracias a starbucks puedes disfrutar de tus bebidas favoritas y sentirte mejor",
              ),
              SizedBox(
                width: 16,
              ),
            ],
          ),
          const BusinessCards(
            title: "Patrocinadores",
            children: [
              SizedBox(
                width: 16,
              ),
              BusinessCard(
                superTitle: "Desafíos creados por empresas",
              ),
              BusinessCard(
                title: "Starbucks",
                description:
                    "Presenta tu cuenta junto a un amigo y disfruta de un 2x1 en tu bebida",
              ),
              SizedBox(
                width: 16,
              ),
            ],
          ),
        ],
      );

  SizedBox ForYou() => SizedBox(
        height: 256,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Container(
              padding: const EdgeInsets.all(8),
              child: Text("Para ti",
                  style: Theme.of(context).textTheme.titleLarge),
            ),
            Container(
              padding: const EdgeInsets.all(16),
              width: double.infinity,
              height: 188,
              child: Card(
                  child: Container(
                padding: const EdgeInsets.all(8),
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Text("Lectura",
                        style: Theme.of(context).textTheme.titleSmall),
                    Text("Reto de lectura",
                        style: Theme.of(context).textTheme.titleMedium),
                    Text("Descripción del reto de lectura",
                        style: Theme.of(context).textTheme.bodyMedium),
                    Expanded(child: Container()),
                    Row(
                      children: [
                        ElevatedButton(
                            onPressed: () => {},
                            style: ElevatedButton.styleFrom(
                              foregroundColor:
                                  Theme.of(context).colorScheme.onPrimary,
                              backgroundColor:
                                  Theme.of(context).colorScheme.primary,
                            ),
                            child: const Row(
                              children: [
                                Text("Ir a la categoría"),
                                Icon(Icons.arrow_right_alt_outlined)
                              ],
                            )),
                        Expanded(child: Container()),
                        IconButton(
                            onPressed: () => {},
                            icon: const Icon(Icons.add_circle_outline)),
                      ],
                    )
                  ],
                ),
              )),
            )
          ],
        ),
      );

  SizedBox ContinueChallenge() => SizedBox(
        height: 256,
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Container(
              padding: const EdgeInsets.all(8),
              child: Text("Continuar Reto",
                  style: Theme.of(context).textTheme.titleLarge),
            ),
            const ChallengesCards()
          ],
        ),
      );

  SizedBox Categories() => SizedBox(
        height: 102,
        child: ListView(
          scrollDirection: Axis.horizontal,
          children: const [
            CategoryContainer(name: "Todo"),
            CategoryContainer(name: "Lectura"),
            CategoryContainer(name: "Aire libre"),
            CategoryContainer(name: "Arte"),
            CategoryContainer(name: "Ejercicio y bienestar físico"),
            CategoryContainer(name: "Manualidades y proyectos DIY"),
            CategoryContainer(name: "Cocina y gastronomía"),
            CategoryContainer(name: "Voluntariado y comunidad"),
            CategoryContainer(name: "Desarrollo personal y aprendizaje"),
          ],
        ),
      );
}
