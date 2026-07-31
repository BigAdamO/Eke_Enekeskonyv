import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../models/song.dart';
import '../providers/favorites_provider.dart';
import '../providers/settings_provider.dart';
import '../services/song_service.dart';
import 'song_view_page.dart';

class FavoritesPage extends StatelessWidget {
  const FavoritesPage({super.key});

  @override
  Widget build(BuildContext context) {
    final settings = Provider.of<SettingsProvider>(context);
    final favorites = Provider.of<FavoritesProvider>(context);

    return Scaffold(
      appBar: AppBar(
        title: const Text('Kedvencek'),
      ),
      body: favorites.isLoaded
          ? FutureBuilder<List<Song>>(
              future: SongService().getAllSongs(),
              builder: (context, snapshot) {
                if (snapshot.connectionState == ConnectionState.waiting) {
                  return const Center(child: CircularProgressIndicator());
                }

                if (snapshot.hasError) {
                  return Center(child: Text('Hiba: ${snapshot.error}'));
                }

                final songs = snapshot.data ?? [];
                final favoriteSongs = songs
                    .where((song) => favorites.isFavorite(song.id))
                    .toList();

                if (favoriteSongs.isEmpty) {
                  return Center(
                    child: Text(
                      'Nincsenek mentett kedvencek.',
                      style: TextStyle(
                        fontSize: 18 * settings.fontSizeScale,
                        fontWeight: FontWeight.bold,
                      ),
                      textAlign: TextAlign.center,
                    ),
                  );
                }

                return ListView.separated(
                  padding: const EdgeInsets.symmetric(vertical: 16),
                  itemCount: favoriteSongs.length,
                  itemBuilder: (context, index) {
                    final song = favoriteSongs[index];
                    return ListTile(
                      contentPadding: const EdgeInsets.symmetric(horizontal: 24, vertical: 4),
                      title: Text(
                        '${song.id} - ${song.title}',
                        style: TextStyle(
                          fontSize: 18 * settings.fontSizeScale,
                          color: Theme.of(context).colorScheme.primary,
                          decoration: TextDecoration.underline,
                        ),
                      ),
                      trailing: IconButton(
                        icon: Icon(
                          favorites.isFavorite(song.id)
                              ? Icons.favorite
                              : Icons.favorite_border,
                          color: favorites.isFavorite(song.id)
                              ? Colors.redAccent
                              : Theme.of(context).iconTheme.color,
                        ),
                        onPressed: () => favorites.toggleFavorite(song.id),
                      ),
                      onTap: () {
                        Navigator.push(
                          context,
                          MaterialPageRoute(
                            builder: (context) => SongViewPage(
                              initialSongId: song.id,
                              playlist: favoriteSongs.map((item) => item.id).toList(),
                            ),
                          ),
                        );
                      },
                    );
                  },
                  separatorBuilder: (context, index) => const Divider(height: 1),
                );
              },
            )
          : const Center(child: CircularProgressIndicator()),
    );
  }
}
