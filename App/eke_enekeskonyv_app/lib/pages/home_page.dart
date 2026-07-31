import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/settings_provider.dart';
import 'search_page.dart';
import 'settings_page.dart';
import 'song_list_page.dart';
import 'playlist_page.dart';
import 'favorites_page.dart';

class HomePage extends StatelessWidget {
  const HomePage({super.key});

  @override
  Widget build(BuildContext context) {
    final settings = Provider.of<SettingsProvider>(context);
    final double textSize = 24 * settings.fontSizeScale;

    return Scaffold(

      appBar: AppBar(
        title: const Text(
          'EKE Énekeskönyv',
          style: TextStyle(fontWeight: FontWeight.bold),
        ),
      ),

      body: Stack(
        children: [

          // Background Image
          Positioned.fill(
            child: Opacity(
              opacity: 0.5,
              child: Image.asset(
                'assets/icon/mainpage_icon.png',
                fit: BoxFit.contain,
              ),
            ),
          ),

          // Content          
          Center(
            child: SingleChildScrollView(
              padding: const EdgeInsets.fromLTRB(24.0, 0, 24.0, 100),
              child: Column(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [

                  Text(
                    'Üdvözöljük az új\nEKE Énekeskönyv\nalkalmazásban!',
                    textAlign: TextAlign.center,
                    style: TextStyle(
                      fontSize: textSize,
                      height: 1.25,
                      fontWeight: FontWeight.w500,
                    ),
                  ),

                  const SizedBox(height: 40),
                  const Divider(thickness: 2, height: 40),
                  const SizedBox(height: 20),

                  _HomeButton(
                    text: 'Keresés',
                    icon: Icons.search,
                    onPressed: () => Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => const SearchPage()),
                    ),
                  ),

                  const SizedBox(height: 20),

                  _HomeButton(
                    text: 'Ének lista',
                    icon: Icons.playlist_add_check,
                    onPressed: () => Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => const PlaylistPage()),
                    ),
                  ),

                  const SizedBox(height: 20),

                  _HomeButton(
                    text: 'Tartalom',
                    icon: Icons.list_alt,
                    onPressed: () => Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => const SongListPage()),
                    ),
                  ),

                  const SizedBox(height: 20),

                  _HomeButton(
                    text: 'Kedvencek',
                    icon: Icons.favorite,
                    onPressed: () => Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => const FavoritesPage()),
                    ),
                  ),

                  const SizedBox(height: 40),
                  const Divider(thickness: 2, height: 40),

                  _HomeButton(
                    text: 'Beállítások',
                    icon: Icons.settings,
                    onPressed: () => Navigator.push(
                      context,
                      MaterialPageRoute(builder: (context) => const SettingsPage()),
                    ),
                  ),

                ],
              ),
            ),
          ),
        ],
      ),
    );
  }
}

class _HomeButton extends StatelessWidget {
  final String text;
  final IconData icon;
  final VoidCallback onPressed;

  const _HomeButton({
    required this.text,
    required this.icon,
    required this.onPressed,
  });

  @override
  Widget build(BuildContext context) {
    final settings = Provider.of<SettingsProvider>(context);
    final double fontSize = 18 * settings.fontSizeScale;

    return SizedBox(
      width: MediaQuery.of(context).size.width * 0.5,
      height: 60,
      child: ElevatedButton.icon(
        onPressed: onPressed,
        icon: Icon(icon),
        label: Text(
          text,
          style: TextStyle(fontSize: fontSize),
        ),
      ),
    );
  }
}
