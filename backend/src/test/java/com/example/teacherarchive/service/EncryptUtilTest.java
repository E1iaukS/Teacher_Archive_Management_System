package com.example.teacherarchive.service;

import com.example.teacherarchive.util.EncryptUtil;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

public class EncryptUtilTest {

    @Test
    void encryptAndDecrypt() {
        EncryptUtil util = new EncryptUtil();
        ReflectionTestUtils.setField(util, "rawKey", "test-key-123456789012345678901234");
        util.init();
        String cipher = util.encrypt("hello");
        assertNotEquals("hello", cipher);
        String plain = util.decrypt(cipher);
        assertEquals("hello", plain);
    }
}
