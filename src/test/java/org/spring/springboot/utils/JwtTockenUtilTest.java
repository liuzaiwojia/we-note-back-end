package org.spring.springboot.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JwtTockenUtilTest {

    @Autowired
    private JwtTockenUtil jwtTockenUtil;

    @Test
    void addition() {
        assertEquals(2, 2, "1 + 1 should equal 2");
    }
}
