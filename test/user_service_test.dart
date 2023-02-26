import 'package:flutter_prj/model/user.dart';
import 'package:flutter_prj/service/user_service.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:rethinkdb_dart/rethinkdb_dart.dart';

Future<void> createDatabase(Rethinkdb db, Connection connection) async {
  await db.dbCreate('test').run(connection).catchError((err) => {});
  await db.tableCreate('users').run(connection).catchError((err) => {});
}

Future<void> cleanDatabase(Rethinkdb db, Connection connection) async {
  await db.table('users').delete().run(connection);
}

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
