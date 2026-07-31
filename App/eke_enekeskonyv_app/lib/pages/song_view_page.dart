import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../models/song.dart';
import '../providers/favorites_provider.dart';
import '../providers/settings_provider.dart';
import '../services/song_service.dart';

class SongViewPage extends StatefulWidget {
  final int initialSongId;
  final List<int>? playlist;

  const SongViewPage({
    super.key,
    required this.initialSongId,
    this.playlist,
  });

  @override
  State<SongViewPage> createState() => _SongViewPageState();
}

class _SongViewPageState extends State<SongViewPage> {
  late PageController _pageController;
  late List<int> _songIds;
  int _currentIndex = 0;
  bool _isLoading = true;
  List<Song> _allSongs = [];

  @override
  void initState() {
    super.initState();
    _loadData();
  }

  Future<void> _loadData() async {
    _allSongs = await SongService().getAllSongs();
    
    if (widget.playlist != null) {
      _songIds = widget.playlist!;
    } else {
      _songIds = _allSongs.map((s) => s.id).toList();
    }

    _currentIndex = _songIds.indexOf(widget.initialSongId);
    if (_currentIndex == -1) _currentIndex = 0;

    _pageController = PageController(initialPage: _currentIndex);
    
    setState(() {
      _isLoading = false;
    });
  }

  @override
  void dispose() {
    _pageController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    if (_isLoading) {
      return const Scaffold(
        body: Center(child: CircularProgressIndicator()),
      );
    }

    return Scaffold(
      appBar: AppBar(
        title: GestureDetector(
          onTap: () => Navigator.of(context).popUntil((route) => route.isFirst),
          child: const Text('EKE Énekeskönyv'),
        ),
        actions: [
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16.0),
            child: Center(
              child: Text(
                '${_currentIndex + 1} / ${_songIds.length}',
                style: const TextStyle(fontSize: 16),
              ),
            ),
          ),
        ],
      ),
      body: PageView.builder(
        controller: _pageController,
        itemCount: _songIds.length,
        onPageChanged: (index) {
          setState(() {
            _currentIndex = index;
          });
        },
        itemBuilder: (context, index) {
          final songId = _songIds[index];
          final song = _allSongs.firstWhere((s) => s.id == songId);
          return _SongDetailView(song: song);
        },
      ),
    );
  }
}

class _SongDetailView extends StatelessWidget {
  final Song song;

  const _SongDetailView({required this.song});

  @override
  Widget build(BuildContext context) {
    final settings = Provider.of<SettingsProvider>(context);
    final favorites = Provider.of<FavoritesProvider>(context);
    final isFavorite = favorites.isFavorite(song.id);

    return SingleChildScrollView(
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          Padding(
            padding: const EdgeInsets.symmetric(vertical: 16.0, horizontal: 10.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Expanded(
                  child: Text(
                    '${song.id} - ${song.title}',
                    style: TextStyle(
                      fontSize: 22 * settings.fontSizeScale,
                      fontWeight: FontWeight.bold,
                    ),
                    textAlign: TextAlign.center,
                  ),
                ),
                IconButton(
                  icon: Icon(
                    isFavorite ? Icons.favorite : Icons.favorite_border,
                    color: isFavorite ? Colors.redAccent : null,
                    size: 28,
                  ),
                  onPressed: () => favorites.toggleFavorite(song.id),
                ),
              ],
            ),
          ),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16.0),
            child: ClipRRect(
              borderRadius: BorderRadius.circular(8),
              child: Image.asset(
                'assets/images/kottak/${song.sheets}.jpg',
                width: double.infinity,
                fit: BoxFit.fitWidth,
                errorBuilder: (context, error, stackTrace) {
                  return Container(
                    padding: const EdgeInsets.all(20),
                    color: Colors.grey[200],
                    child: const Center(child: Text('Kotta nem található')),
                  );
                },
              ),
            ),
          ),
          const SizedBox(height: 20),
          Padding(
            padding: const EdgeInsets.all(20.0),
            child: Text(
              song.lyrics,
              style: TextStyle(
                fontSize: 20 * settings.fontSizeScale,
                height: 1.4,
              ),
            ),
          ),
          const SizedBox(height: 100), // Extra space at bottom for comfortable reading
        ],
      ),
    );
  }
}
