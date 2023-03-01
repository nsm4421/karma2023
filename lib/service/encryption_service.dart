import 'package:encrypt/encrypt.dart';
import 'package:flutter_prj/service/encryption_service_impl.dart';

class EncryptionService extends IEncryptionService {
  final Encrypter _encryptor;
  final _iv = IV.fromLength(16);

  EncryptionService(this._encryptor);

  @override
  String decrypt(String encryptedText) =>
      _encryptor.decrypt(Encrypted.fromBase64(encryptedText), iv: _iv);

  @override
  String encrypt(String text) => _encryptor.encrypt(text, iv: _iv).base64;
}
