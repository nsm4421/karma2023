import 'package:flutter_prj/model/user_model.dart';
import 'package:rethinkdb_dart/rethinkdb_dart.dart';

String databaseName = 'test';
List<String> tablesNames = ["users", "messages", "receipts"];

Future<void> createDatabase(Rethinkdb db, Connection connection) async {
  await db.dbCreate(databaseName).run(connection).catchError(print);
  for (String tn in tablesNames) {
    await db.tableCreate(tn).run(connection).catchError(print);
  }
}

Future<void> cleanDatabase(Rethinkdb db, Connection connection) async {
  for (String tn in tablesNames) {
    await db.table(tn).delete().run(connection);
  }
}
