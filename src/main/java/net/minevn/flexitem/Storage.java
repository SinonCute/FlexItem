package net.minevn.flexitem;

import net.minevn.flexitem.object.FlexContainer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Storage {

    private final ConcurrentHashMap<UUID, FlexContainer> containers = new ConcurrentHashMap<>();

    public Map<UUID, FlexContainer> getContainers() {
        return containers;
    }

    public FlexContainer getContainer(UUID uuid) {
        return containers.get(uuid);
    }

    public void removeContainer(FlexContainer container) {
        containers.remove(container.getUUID());
    }

    public void addContainer(FlexContainer container) {
        containers.put(container.getUUID(), container);
    }
}
