package net.minevn.flexitem.object;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FlexContainer {

    private final UUID uuid;
    private final UUID owner;

    private final TypeContainer type;
    private List<ItemStack> items;

    private final long expiredAt;

    public FlexContainer(UUID uuid, UUID owner, TypeContainer type, long expiredAt) {
        this.uuid = uuid;
        this.owner = owner;
        this.type = type;
        this.expiredAt = expiredAt;
    }

    public UUID getUUID() {
        return uuid;
    }

    public UUID getOwner() {
        return owner;
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    public void addItem(ItemStack item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
    }

    public TypeContainer getType() {
        return type;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() >= expiredAt;
    }
}
