import 'package:flutter/material.dart';
import 'home/home.dart';

void main() => runApp(IceColaApp());

class IceColaApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return new MaterialApp(
      title: 'Welcome to Flutter',
      theme: new ThemeData(
        primaryColor: Color(0xFF20700F),
      ),
      home: new Home(),
    );
  }
}