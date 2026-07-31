import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/settings_provider.dart';
import '../services/song_service.dart';
import 'song_view_page.dart';
import 'song_list_page.dart';


class SearchPage extends StatefulWidget {
  final bool initialSearchByNumber;
  const SearchPage({super.key, this.initialSearchByNumber = true});

  @override
  State<SearchPage> createState() => _SearchPageState();
}

class _SearchPageState extends State<SearchPage> {
  late final TextEditingController _controller;
  late bool _searchByNumber;

  @override
  void initState() {
    super.initState();
    _controller = TextEditingController();
    _searchByNumber = widget.initialSearchByNumber;
  }

  void _handleSearch() {
    final query = _controller.text.trim();
    if (query.isEmpty) return;

    if (_searchByNumber) {
      final number = int.tryParse(query);
      if (number != null && number >= 1 && number <= 463) {
        Navigator.push(
          context,
          MaterialPageRoute(
            builder: (context) => SongViewPage(initialSongId: number),
          ),
        );
      } else {
        ScaffoldMessenger.of(context).showSnackBar(
          const SnackBar(content: Text('Érvénytelen énekszám (1-463)')),
        );
      }
    } else {
      Navigator.push(
        context,
        MaterialPageRoute(
          builder: (context) => SongListPage(searchQuery: query),
        ),
      );
    }
  }

  @override
  Widget build(BuildContext context) {
    final settings = Provider.of<SettingsProvider>(context);
    final double fontSize = 18 * settings.fontSizeScale;

    return Scaffold(
      appBar: AppBar(title: const Text('Keresés')),
      body: Padding(
        padding: const EdgeInsets.all(24.0),
        child: Column(
          children: [

            const SizedBox(height: 40),

            TextField(
              controller: _controller,
              keyboardType: _searchByNumber ? TextInputType.number : TextInputType.text,
              autofocus: true,
              style: TextStyle(fontSize: fontSize),
              decoration: InputDecoration(
                labelText: _searchByNumber ? 'Énekszám' : 'Cím, első sor...',
                border: const OutlineInputBorder(),
                prefixIcon: Icon(_searchByNumber ? Icons.numbers : Icons.text_fields),
              ),
              onSubmitted: (_) => _handleSearch(),
            ),

            const SizedBox(height: 12),

            OutlinedButton(
              onPressed: () {
                Navigator.pushReplacement(
                  context,
                  PageRouteBuilder(
                    pageBuilder: (context, animation1, animation2) =>
                        SearchPage(initialSearchByNumber: !_searchByNumber),
                    transitionDuration: Duration.zero,
                    reverseTransitionDuration: Duration.zero,
                  ),
                );
              },
              child: Text(
                _searchByNumber ? 'Keresés cím és szöveg alapján' : 'Keresés szám alapján',
              ),
            ),

            const SizedBox(height: 40),

            SizedBox(
              width: 200,
              height: 50,
              child: ElevatedButton(
                onPressed: _handleSearch,
                child: 
                  const Text('Keresés'),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
