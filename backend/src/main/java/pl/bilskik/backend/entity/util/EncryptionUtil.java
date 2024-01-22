package pl.bilskik.backend.entity.util;

public interface EncryptionUtil {
    String encrypt(String value);
    String decrypt(String value);
}
