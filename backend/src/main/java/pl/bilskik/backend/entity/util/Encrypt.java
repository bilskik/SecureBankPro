package pl.bilskik.backend.entity.util;

import jakarta.persistence.AttributeConverter;

public class Encrypt implements AttributeConverter<String, String> {

    private final EncryptionUtil encryptionUtil;

    public Encrypt(EncryptionUtil encryptionUtil) {
        this.encryptionUtil = encryptionUtil;
    }
    @Override
    public String convertToDatabaseColumn(String toDBVal) {
        return encryptionUtil.encrypt(toDBVal);
    }

    @Override
    public String convertToEntityAttribute(String fromDBVal) {
        return encryptionUtil.decrypt(fromDBVal);
    }
}
