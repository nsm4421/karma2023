import 'package:flutter_prj/model/message_model.dart';
import 'package:flutter_prj/model/user_model.dart';
import 'package:flutter_prj/service/message_service.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:rethinkdb_dart/rethinkdb_dart.dart';

import 'util_for_test.dart';

void main() {
  Rethinkdb _db = Rethinkdb();
  Connection _connection;
  MessageService sut;

  setUp(() async {
    _connection = await _db.connect(host: '127.0.0.1', port: 28015);
    await createDatabase(_db, _connection);
    sut = MessageService(_db, _connection);
  });

  tearDown(() async {
    sut.dispose();
    await cleanDatabase(_db, _connection);
  });

  final sender =
      User.fromJson({'id': '1', 'active': true, 'lastSeen': DateTime.now()});

  final receiver =
      User.fromJson({'id': '2', 'active': true, 'lastSeen': DateTime.now()});

  test('if message sent successfully, then return true', () async {
    Message message = Message(
        from: sender.id,
        to: receiver.id,
        timestamp: DateTime.now(),
        contents: 'test message');
    final result = await sut.send(message);
    expect(result, true);
  });

  test('sending message works successfully', () async {
    sut.messages(user: receiver).listen(expectAsync1((message) {
          expect(message.to, receiver.id);
          expect(message.id, isNotEmpty);
        }, count: 2));
    Message firstMessage = Message(
        from: sender.id,
        to: receiver.id,
        timestamp: DateTime.now(),
        contents: 'test message');
    Message secondMessage = Message(
        from: sender.id,
        to: receiver.id,
        timestamp: DateTime.now(),
        contents: 'test message');
    await sut.send(firstMessage);
    await sut.send(secondMessage);
  });

  test('successfully subscribe and receive messages', () async {
    sut.messages(user: receiver).listen(expectAsync1((message) {
          expect(message.to, receiver.id);
          expect(message.id, isNotEmpty);
        }, count: 2));

    Message message = Message(
      from: sender.id,
      to: receiver.id,
      timestamp: DateTime.now(),
      contents: 'this is a message',
    );

    Message secondMessage = Message(
      from: sender.id,
      to: receiver.id,
      timestamp: DateTime.now(),
      contents: 'this is another message',
    );

    await sut.send(message);
    await sut.send(secondMessage);
  });

  test('successfully subscribe and receive new messages ', () async {
    Message message = Message(
      from: sender.id,
      to: receiver.id,
      timestamp: DateTime.now(),
      contents: 'this is a message',
    );

    Message secondMessage = Message(
      from: sender.id,
      to: receiver.id,
      timestamp: DateTime.now(),
      contents: 'this is another message',
    );

    await sut.send(message);
    await sut.send(secondMessage).whenComplete(
          () => sut.messages(user: receiver).listen(
                expectAsync1((message) {
                  expect(message.to, receiver.id);
                }, count: 2),
              ),
        );
  });
}
