import 'dart:convert';
import 'package:flutter/services.dart';
import '../models/song.dart';

class SongService {
  static final SongService _instance = SongService._internal();
  factory SongService() => _instance;
  SongService._internal();

  List<Song>? _cachedSongs;

  Future<List<Song>> getAllSongs() async {
    if (_cachedSongs != null) return _cachedSongs!;

    final String response = await rootBundle.loadString('assets/data/songs.json');
    final data = await json.decode(response) as List<dynamic>;
    
    _cachedSongs = data.map((json) => Song.fromJson(json)).toList();
    
    // Sort by ID just in case
    _cachedSongs!.sort((a, b) => a.id.compareTo(b.id));
    
    return _cachedSongs!;
  }

  Future<Song?> getSongById(int id) async {
    final songs = await getAllSongs();
    try {
      return songs.firstWhere((song) => song.id == id);
    } catch (e) {
      return null;
    }
  }

  Future<List<Song>> searchSongs(String query) async {
    final songs = await getAllSongs();
    final lowerQuery = query.toLowerCase();
    return songs.where((song) {
      return song.title.toLowerCase().contains(lowerQuery) ||
             song.lyrics.toLowerCase().contains(lowerQuery) ||
             song.id.toString() == query;
    }).toList();
  }
}
