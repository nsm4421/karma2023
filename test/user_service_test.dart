import 'package:flutter_prj/model/user_model.dart';
import 'package:flutter_prj/service/user/user_service.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:rethinkdb_dart/rethinkdb_dart.dart';

import 'util_for_test.dart';

void main() {
  Rethinkdb db = Rethinkdb();
  Connection connection;
  UserService sut;

  setUp(() async {
    connection = await db.connect(host: "127.0.0.1", port: 28015);
    await createDatabase(db, connection);
    sut = UserService(db, connection);
  });

  tearDown(() async {
    await cleanDatabase(db, connection);
  });

  test('create new user and its id is not empty', () async {
    final user = User(
        username: 'test username',
        photoUrl: 'test photo url',
        active: true,
        lastSeen: DateTime.now());
    final userWithId = await sut.connect(user);
    expect(userWithId.id, isNotEmpty);
  });
}
