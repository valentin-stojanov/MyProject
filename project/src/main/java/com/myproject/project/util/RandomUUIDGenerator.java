package com.myproject.project.util;

import java.util.UUID;

public class RandomUUIDGenerator {

    public String generateUUID(){
        return UUID.randomUUID().toString();
    }
}
