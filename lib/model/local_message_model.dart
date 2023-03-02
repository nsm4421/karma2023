import '../chat/chat_helper.dart';

class LocalMessage {
  String _id;

  String get id => _id;
  String chatId;
  Message message;
  ReceiptStatus receiptStatus;

  LocalMessage(this.chatId, this.message, this.receiptStatus);

  Map<String, dynamic> toMap() => {
        'chat_id': chatId,
        'id': message.id,
        'receipt_status': receiptStatus.value(),
        ...message.toJson()
      };

  factory LocalMessage.fromMap(Map<String, dynamic> json) {
    final message = Message(
        from: json['from'],
        to: json['to'],
        timestamp: json['timestamp'],
        contents: json['contents']);
    final localMessage =
        LocalMessage(json['chat_id'], message, json['receipt_status']);
    localMessage._id = json['id'];
    return localMessage;
  }
}
