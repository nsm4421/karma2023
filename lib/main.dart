import 'package:flutter/material.dart';
import 'package:flutter_prj/screen/custom_desgin/theme.dart';
import 'package:flutter_prj/screen/pages/on_board/on_board_page.dart';
void main() => runApp(const MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        title: "Chat App",
        theme: lightTheme(context),
        darkTheme: darkTheme(context),
        home: OnBoarding());
  }

  const MyApp({key}) : super(key: key);
}
