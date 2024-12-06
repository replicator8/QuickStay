package com.example.demo.constants;

import java.util.Random;

public enum RoomType {
    SINGLE("Одиночный"),
    DOUBLE("Двойной"),
    STANDARD("Стандартный"),
    FAMILY("Семейный"),
    LUX("Люкс"),
    DUPLEX("Двухуровневый");

    private final String rus;

    RoomType(String rus) {
        this.rus = rus;
    }

    public String getRus() {
        return rus;
    }

    public static RoomType getRandomRoomType() {
        RoomType[] values = RoomType.values();
        Random random = new Random();
        return values[random.nextInt(values.length)];
    }
}
