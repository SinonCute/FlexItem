package net.minevn.flexitem.object;

public enum TypeContainer {
    Armor,
    Hand,
    Item;

    public static TypeContainer getType(String stringValue) {
        switch (stringValue) {
            case "Armor":
                return Armor;
            case "Hand":
                return Hand;
            default:
                return Item;
        }
    }
}
