import 'package:rethinkdb_dart/rethinkdb_dart.dart';

Future<void> createDatabase(Rethinkdb db, Connection connection) async {
  await db.dbCreate('test').run(connection).catchError((err) => {});
  await db.tableCreate('users').run(connection).catchError((err) => {});
  await db.tableCreate('messages').run(connection).catchError((err) => {});
}

Future<void> cleanDatabase(Rethinkdb db, Connection connection) async {
  await db.table('users').delete().run(connection);
  await db.table('messages').delete().run(connection);
}