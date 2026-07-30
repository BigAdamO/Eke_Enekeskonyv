import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/settings_provider.dart';
import 'song_view_page.dart';

class PlaylistPage extends StatefulWidget {
  const PlaylistPage({super.key});

  @override
  State<PlaylistPage> createState() => _PlaylistPageState();
}

class _PlaylistPageState extends State<PlaylistPage> {
  final TextEditingController _controller = TextEditingController();
  final List<int> _selectedIds = [];

  void _addSong() {
    final text = _controller.text.trim();
    if (text.isEmpty) return;

    final id = int.tryParse(text);
    if (id != null && id >= 1 && id <= 463) {
      if (_selectedIds.length < 10) {
        setState(() {
          _selectedIds.add(id);
          _controller.clear();
        });
      } else {
        _showError('Maximum 10 ének adható hozzá.');
      }
    } else {
      _showError('Nincs ilyen számú ének! (1-463)');
      _controller.clear();
    }
  }

  void _showError(String message) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(content: Text(message)),
    );
  }

  void _startSinging() {
    if (_selectedIds.isEmpty) return;
    Navigator.push(
      context,
      MaterialPageRoute(
        builder: (context) => SongViewPage(
          initialSongId: _selectedIds.first,
          playlist: _selectedIds,
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    final settings = Provider.of<SettingsProvider>(context);
    final double fontSize = 18 * settings.fontSizeScale;

    return Scaffold(
      appBar: AppBar(title: const Text('Ének lista')),
      body: Padding(
        padding: const EdgeInsets.all(24.0),
        child: Column(
          children: [
            const SizedBox(height: 20),
            TextField(
              controller: _controller,
              keyboardType: TextInputType.number,
              style: TextStyle(fontSize: fontSize),
              decoration: const InputDecoration(
                labelText: 'Énekszám',
                border: OutlineInputBorder(),
              ),
              onSubmitted: (_) => _addSong(),
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: _addSong,
              child: const Text('Felvesz'),
            ),
            const SizedBox(height: 30),
            Expanded(
              child: ListView.builder(
                itemCount: _selectedIds.length,
                itemBuilder: (context, index) {
                  final id = _selectedIds[index];
                  return Card(
                    margin: const EdgeInsets.only(bottom: 8),
                    color: Theme.of(context).colorScheme.secondaryContainer,
                    child: ListTile(
                      title: Text(
                        '#$id',
                        textAlign: TextAlign.center,
                        style: TextStyle(fontSize: 20 * settings.fontSizeScale, fontWeight: FontWeight.bold),
                      ),
                      trailing: IconButton(
                        icon: const Icon(Icons.delete),
                        onPressed: () => setState(() => _selectedIds.removeAt(index)),
                      ),
                    ),
                  );
                },
              ),
            ),
            const SizedBox(height: 20),
            Row(
              mainAxisAlignment: MainAxisAlignment.spaceEvenly,
              children: [
                FloatingActionButton(
                  heroTag: 'clear',
                  onPressed: () => setState(() => _selectedIds.clear()),
                  backgroundColor: Colors.red[100],
                  child: const Icon(Icons.delete_sweep, color: Colors.red),
                ),
                FloatingActionButton(
                  heroTag: 'start',
                  onPressed: _startSinging,
                  child: const Icon(Icons.play_arrow),
                ),
              ],
            ),
          ],
        ),
      ),
    );
  }
}
