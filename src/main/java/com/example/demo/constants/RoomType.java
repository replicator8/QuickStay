package com.example.demo.constants;

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
}
