package net.minevn.flexitem;

import net.minevn.flexitem.object.FlexContainer;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Storage {

    private static Storage instance;

    private Map<UUID, FlexContainer> containers;

    public Storage() {
        instance = this;
        containers = new HashMap<>();
    }

    public static Storage getInstance() {
        return instance;
    }

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
