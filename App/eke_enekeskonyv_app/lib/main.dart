import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'pages/home_page.dart';
import 'providers/settings_provider.dart';
import 'providers/favorites_provider.dart';

void main() {
  WidgetsFlutterBinding.ensureInitialized();
  runApp(
    MultiProvider(
      providers: [
        ChangeNotifierProvider(create: (context) => SettingsProvider()),
        ChangeNotifierProvider(create: (context) => FavoritesProvider()),
      ],
      child: const EkeApp(),
    ),
  );
}

class EkeApp extends StatelessWidget {
  const EkeApp({super.key});

  @override
  Widget build(BuildContext context) {
    return Consumer<SettingsProvider>(
      builder: (context, settings, child) {
        return MaterialApp(
          title: 'EKE Énekeskönyv',
          debugShowCheckedModeBanner: false,
          theme: _buildTheme(Brightness.light),
          darkTheme: _buildTheme(Brightness.dark),
          themeMode: settings.isDarkMode ? ThemeMode.dark : ThemeMode.light,
          home: const HomePage(),
        );
      },
    );
  }

  ThemeData _buildTheme(Brightness brightness) {
    final bool isDark = brightness == Brightness.dark;
    
    // Primary colors based on #000066
    const Color appDarkBlue = Color(0xFF1b1b4b);
    const Color appBlue = Color(0xFF1A1A80);
    const Color appLightBlue = Color(0xFF9999FF);
    const Color grayDark = Color(0xFF161545); // Slightly lighter than background for surfaces

    return ThemeData(
      brightness: brightness,
      primaryColor: appDarkBlue,
      colorScheme: ColorScheme.fromSeed(
        seedColor: appDarkBlue,
        brightness: brightness,
        primary: isDark ? appLightBlue : appDarkBlue,
        secondary: appBlue,
        surface: isDark ? grayDark : Colors.white,
      ),
      scaffoldBackgroundColor: isDark ? appDarkBlue : Colors.white,
      appBarTheme: const AppBarTheme(
        backgroundColor: Color(0xFF000066),
        foregroundColor: Colors.white,
        surfaceTintColor: Colors.transparent, // Prevents M3 surface tint from changing the color
        centerTitle: false,
        elevation: 0,
      ),
      elevatedButtonTheme: ElevatedButtonThemeData(
        style: ElevatedButton.styleFrom(
          backgroundColor: appBlue,
          foregroundColor: Colors.white,
          textStyle: const TextStyle(fontWeight: FontWeight.bold),
          // shape: RoundedCornerShape(8),
        ),
      ),
      useMaterial3: true,
    );
  }
}
