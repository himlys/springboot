package com.spring.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "a")
public class AProperty {
    private final B b = new B();


    public static class B {
        private int c;

        public int getC() {
            return c;
        }

        public void setC(int c) {
            this.c = c;
        }
    }

    public B getB() {
        return b;
    }
}
