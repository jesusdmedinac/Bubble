import 'package:bubble/presentation/screen/tab/bubble.tab.dart';
import 'package:bubble/presentation/screen/tab/home.tab.dart';
import 'package:bubble/presentation/screen/tab/profile.tab.dart';
import 'package:flutter/material.dart';

class MainScreen extends StatefulWidget {
  const MainScreen({super.key});

  @override
  State<StatefulWidget> createState() => _MainScreenState();
}

class _MainScreenState extends State<MainScreen> {
  int currentPageIndex = 1;

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        bottomNavigationBar: NavigationBar(
          onDestinationSelected: (int index) {
            setState(() {
              currentPageIndex = index;
            });
          },
          indicatorColor: Theme.of(context).colorScheme.primary,
          selectedIndex: currentPageIndex,
          destinations: const <Widget>[
            NavigationDestination(
              selectedIcon: Icon(Icons.person, color: Colors.white),
              icon: Icon(Icons.person),
              label: 'Perfil',
            ),
            NavigationDestination(
              selectedIcon: Icon(Icons.home, color: Colors.white),
              icon: Icon(Icons.home),
              label: 'Para ti',
            ),
            NavigationDestination(
              selectedIcon: Icon(Icons.bubble_chart, color: Colors.white),
              icon: Icon(Icons.bubble_chart),
              label: 'Bubble',
            ),
          ],
        ),
        body: currentPageIndex == 0
            ? const ProfileTab()
            : currentPageIndex == 1
                ? const HomeTab()
                : const BubbleTab());
  }
}
