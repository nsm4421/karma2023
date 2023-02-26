import 'package:flutter_prj/model/user.dart';

abstract class IUserService {
  Future<User> connect(User user);
  Future<List<User>> online();
  Future<void> disconnect(User user);
}