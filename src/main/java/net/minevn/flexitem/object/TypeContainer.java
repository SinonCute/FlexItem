package net.minevn.flexitem.object;

public enum TypeContainer {
    Armor,
    Hand,
    All,
    Item;

    public static TypeContainer getType(String stringValue) {
        return switch (stringValue) {
            case "Armor" -> Armor;
            case "Hand" -> Hand;
            case "All" -> All;
            default -> Item;
        };
    }
}
