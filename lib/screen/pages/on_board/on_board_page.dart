import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

import '../../widgets/elevated_button_widget.dart';
import '../../widgets/logo_widget.dart';
import '../../widgets/text_field_widget.dart';
import '../../widgets/upload_profile_widget.dart';

class OnBoarding extends StatefulWidget {
  const OnBoarding({Key key}) : super(key: key);

  @override
  State<OnBoarding> createState() => _OnBoardingState();
}

class _OnBoardingState extends State<OnBoarding> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Colors.transparent,
        ),
        resizeToAvoidBottomInset: false,
        body: SafeArea(
          child: Container(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.center,
              children: <Widget>[
                logoWidget(context),
                const Spacer(),
                // TODO : on Tap
                ProfileUploadWidget(
                  onTap: () {},
                ),
                const Spacer(
                  flex: 1,
                ),
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: CustomTextField(
                      hint: '닉네임을 입력해주세요',
                      height: 45.0,
                      // TODO : onChanged
                      onChanged: (text) {},
                      textInputAction: TextInputAction.done),
                ),
                const SizedBox(
                  height: 20.0,
                ),
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: ElevatedBtn(
                      onPressed: () {}, btnText: "Join Us", height: 45.0),
                ),
                const Spacer(
                  flex: 2,
                )
              ],
            ),
          ),
        ));
  }
}
