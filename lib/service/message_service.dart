import 'dart:async';

import 'package:flutter_prj/model/message_model.dart';
import 'package:flutter_prj/model/user_model.dart';
import 'package:flutter_prj/service/message_service_impl.dart';
import 'package:logger/logger.dart';
import 'package:rethinkdb_dart/rethinkdb_dart.dart';

class MessageService implements IMessageService {
  final Connection _connection;
  final Rethinkdb _db;

  var _logger = Logger();

  final _controller = StreamController<Message>.broadcast();
  StreamSubscription _changefeed;

  MessageService(this._db, this._connection);

  @override
  dispose() {
    _changefeed?.cancel();
    _controller?.close();
  }

  @override
  Stream<Message> messages({User user}) {
    _startReceivingMessages(user);
    return _controller.stream;
  }

  @override
  Future<bool> send(Message message) async {
    Map record =
    await _db.table('messages').insert(message.toJson()).run(_connection);
    return record['inserted'] == 1;
  }

  _startReceivingMessages(User user) {
    _changefeed = _db
        .table('messages')
        .filter({'to': user.id})
        .changes({'include_initial': true})
        .run(_connection)
        .asStream()
        .cast<Feed>()
        .listen((event) {
      event
          .forEach((feedData) {
        if (feedData['new_val'] == null) return;

        final message = _messageFromFeed(feedData);
        _controller.sink.add(message);
        _removeDeliveredMessage(message);
      })
          .catchError((err) => _logger.e(err))
          .onError((err, stackTrace) => _logger.e(err));
    });
  }

  Message _messageFromFeed(feedData) {
    return Message.fromJson(feedData['new_val']);
  }

  _removeDeliveredMessage(Message message) {
    _db
        .table('messages')
        .get(message.id)
        .delete({'return_changes': false}).run(_connection);
  }
}
