class Song {
  final int id;
  final String title;
  final String lyrics;
  final String sheets;

  Song({
    required this.id,
    required this.title,
    required this.lyrics,
    required this.sheets,
  });

  factory Song.fromJson(Map<String, dynamic> json) {
    return Song(
      id: int.parse(json['id'].toString()),
      title: json['title'] as String,
      lyrics: json['lyrics'] as String,
      sheets: json['sheets'] as String,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'title': title,
      'lyrics': lyrics,
      'sheets': sheets,
    };
  }
}
