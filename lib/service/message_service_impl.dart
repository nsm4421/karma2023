import 'package:flutter/cupertino.dart';
import 'package:flutter_prj/model/message_model.dart';

import '../model/user_model.dart';

abstract class IMessageService {
  Future<bool> send(Message message);

  Stream<Message> messages({@required User user});

  dispose();
}
