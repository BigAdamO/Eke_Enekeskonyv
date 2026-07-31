import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';

class FavoritesProvider with ChangeNotifier {
  static const String _prefsKey = 'favoriteSongIds';
  final Set<int> _favoriteSongIds = {};
  bool _isLoaded = false;

  FavoritesProvider() {
    _loadFavorites();
  }

  List<int> get favoriteSongIds => _favoriteSongIds.toList();
  bool get isLoaded => _isLoaded;
  int get favoriteCount => _favoriteSongIds.length;

  bool isFavorite(int songId) {
    return _favoriteSongIds.contains(songId);
  }

  Future<void> toggleFavorite(int songId) async {
    if (_favoriteSongIds.contains(songId)) {
      _favoriteSongIds.remove(songId);
    } else {
      _favoriteSongIds.add(songId);
    }
    await _saveFavorites();
    notifyListeners();
  }

  Future<void> _loadFavorites() async {
    final prefs = await SharedPreferences.getInstance();
    final savedIds = prefs.getStringList(_prefsKey) ?? [];
    _favoriteSongIds
      ..clear()
      ..addAll(savedIds.map(int.parse));
    _isLoaded = true;
    notifyListeners();
  }

  Future<void> _saveFavorites() async {
    final prefs = await SharedPreferences.getInstance();
    await prefs.setStringList(
      _prefsKey,
      _favoriteSongIds.map((id) => id.toString()).toList(),
    );
  }
}
