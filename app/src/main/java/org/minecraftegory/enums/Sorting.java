package org.minecraftegory.enums;

import lombok.Getter;

@Getter
public enum Sorting {

    ALPHANUM(0, "name"),
    CREATION_DATE(1, "creationDate"),
    CHILDREN_NBR(2, "childrenNumber");

    private final int value;
    private final String name;

    Sorting(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Sorting getByValue(int value) {
        return switch (value) {
            case 1 -> CREATION_DATE;
            case 2 -> CHILDREN_NBR;
            default -> ALPHANUM;
        };
    }

}
