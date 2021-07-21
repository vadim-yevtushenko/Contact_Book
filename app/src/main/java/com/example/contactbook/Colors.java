package com.example.contactbook;

public enum Colors {
    RED("#F44336"),
    GREEN("#FF8BC34A"),
    BLUE("#FF03A9F4");

    private String color;

    Colors(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }
}
