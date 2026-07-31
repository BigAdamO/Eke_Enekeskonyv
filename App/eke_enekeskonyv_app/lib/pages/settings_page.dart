import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import '../providers/settings_provider.dart';

class SettingsPage extends StatelessWidget {
  const SettingsPage({super.key});

  @override
  Widget build(BuildContext context) {
    final settings = Provider.of<SettingsProvider>(context);

    return Scaffold(
      appBar: AppBar(title: const Text('Beállítások')),
      body: ListView(
        padding: const EdgeInsets.all(24.0),
        children: [

          const Text(
            'Beállítások',
            style: TextStyle(fontSize: 30, fontWeight: FontWeight.bold, decoration: TextDecoration.underline),
          ),

          const SizedBox(height: 30),

          Row(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              
              Text(
                'Sötét téma:',
                style: TextStyle(
                  fontSize: 20 * settings.fontSizeScale,
                  fontWeight: FontWeight.bold,
                  color: Theme.of(context).colorScheme.primary,
                ),
              ),

              Switch(
                value: settings.isDarkMode,
                onChanged: (_) => settings.toggleDarkMode(),
                activeColor: Theme.of(context).colorScheme.secondary,
              ),

            ],
          ),
          const SizedBox(height: 30),

          Text(
            'Betüméret:',
            style: TextStyle(
              fontSize: 20 * settings.fontSizeScale,
              fontWeight: FontWeight.bold,
              color: Theme.of(context).colorScheme.primary,
            ),
          ),

          Slider(
            value: settings.fontSizeScale,
            min: 0.5,
            max: 2.0,
            divisions: 6,
            label: '${(settings.fontSizeScale * 100).toInt()}%',
            onChanged: (value) => settings.setFontSizeScale(value),
            activeColor: Theme.of(context).colorScheme.secondary,
          ),

          const SizedBox(height: 20),

          Center(
            child: Text(
              'Minta szöveg a méretezéshez',
              style: TextStyle(fontSize: 16 * settings.fontSizeScale),
            ),
          ),
          
        ],
      ),
    );
  }
}
