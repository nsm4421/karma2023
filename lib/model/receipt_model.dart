import 'package:flutter/cupertino.dart';

class Receipt {
  String _id;

  String get id => _id;

  final String recipient;
  final String messageId;
  final String status;
  final DateTime timestamp;

  Receipt(
      {@required this.recipient,
      @required this.messageId,
      @required this.status,
      @required this.timestamp});

  Map<String, dynamic> toJson() => {
        'recipient': recipient,
        'message_id': messageId,
        'status': status,
        'timestamp': timestamp
      };

  factory Receipt.fromJson(Map<String, dynamic> json) {
    var receipt = Receipt(
        recipient: json['recipient'],
        messageId: json['message_id'],
        status: json['status'],
        timestamp: json['timestamp']);
    receipt._id = json['id'];
    return receipt;
  }
}
