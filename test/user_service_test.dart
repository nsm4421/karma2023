import 'package:flutter_prj/model/user_model.dart';
import 'package:flutter_prj/service/user_service.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:rethinkdb_dart/rethinkdb_dart.dart';

import 'util_for_test.dart';

void main() {
  Rethinkdb _db = Rethinkdb();
  Connection _connection;
  UserService sut;

  setUp(() async {
    _connection = await _db.connect(host: "127.0.0.1", port: 28015);
    await createDatabase(_db, _connection);
    sut = UserService(_db, _connection);
  });

  tearDown(() async {
    await cleanDatabase(_db, _connection);
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
