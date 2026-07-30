import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../models/song.dart';
import '../providers/settings_provider.dart';
import '../services/song_service.dart';
import 'song_view_page.dart';

class SongListPage extends StatelessWidget {
  final String? searchQuery;

  const SongListPage({super.key, this.searchQuery});

  @override
  Widget build(BuildContext context) {
    final settings = Provider.of<SettingsProvider>(context);
    final isSearching = searchQuery != null && searchQuery!.isNotEmpty;

    return Scaffold(
      appBar: AppBar(
        title: Text(isSearching ? 'Találatok' : 'Tartalom'),
      ),
      body: FutureBuilder<List<Song>>(
        future: isSearching 
            ? SongService().searchSongs(searchQuery!) 
            : SongService().getAllSongs(),
        builder: (context, snapshot) {
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }

          if (snapshot.hasError) {
            return Center(child: Text('Hiba: ${snapshot.error}'));
          }

          final songs = snapshot.data ?? [];

          if (songs.isEmpty) {
            return const Center(
              child: Text(
                'Nincs találat',
                style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
              ),
            );
          }

          return ListView.separated(
            padding: const EdgeInsets.symmetric(vertical: 16),
            itemCount: songs.length,
            itemBuilder: (context, index) {
              final song = songs[index];
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
                onTap: () {
                  Navigator.push(
                    context,
                    MaterialPageRoute(
                      builder: (context) => SongViewPage(
                        initialSongId: song.id,
                        playlist: isSearching ? songs.map((s) => s.id).toList() : null,
                      ),
                    ),
                  );
                },
              );
            },
            separatorBuilder: (context, index) => const Divider(height: 1),
          );
        },
      ),
    );
  }
}
