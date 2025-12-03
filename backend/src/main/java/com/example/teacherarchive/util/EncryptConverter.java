package com.example.teacherarchive.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Converter
public class EncryptConverter implements AttributeConverter<String, String> {
    @Autowired
    private EncryptUtil encryptUtil;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return encryptUtil.encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return encryptUtil.decrypt(dbData);
    }
}
