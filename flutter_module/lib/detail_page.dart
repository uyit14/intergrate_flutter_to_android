import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class DetailPage extends StatelessWidget {
  static const platform = const MethodChannel("samples.flutter.dev/launchfragment");

  Future<void> _launchFragment() async{
    try {
      final String result = await platform.invokeMethod("launchFragment");
      print("Success: " + result);
    } on PlatformException catch (e) {
      print("Fail: " + e.message);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text("Detail Page"),
      ),
      body: SafeArea(
        child: Container(
          child: Center(
              child: FlatButton(
                  onPressed: _launchFragment,
                  color: Colors.blue,
                  padding: EdgeInsets.symmetric(horizontal: 4, vertical: 8),
                  splashColor: Colors.red,
                  child: Text(
                    "This is detail page!",
                    style: TextStyle(fontSize: 28),
                  ))),
        ),
      ),
    );
  }
}
