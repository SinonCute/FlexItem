package net.minevn.flexitem.object;

public enum TypeContainer {
    Armor,
    Hand,
    Item;

    public static TypeContainer getType(String stringValue) {
        return switch (stringValue) {
            case "Armor" -> Armor;
            case "Hand" -> Hand;
            default -> Item;
        };
    }
}
