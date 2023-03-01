package net.minevn.flexitem;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minevn.flexitem.object.FlexContainer;
import net.minevn.flexitem.object.TypeContainer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class Utils {

    public static void sendMessages(Player player, UUID uuid) {
        TextComponent message = new TextComponent(player.getDisplayName() + " vừa khoe 1 cái gì đó");
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/flexitem open " + uuid));
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.spigot().sendMessage(message);
        }
    }

    public static void openContainer(FlexContainer container) {
        var player = Bukkit.getPlayer(container.getOwner());
        if (container.getType() == TypeContainer.Armor) {
            ChestGui gui = new ChestGui(4, "Khoe vật phẩm của" + player.getDisplayName());
            StaticPane pane = new StaticPane(4, 1, 1, 4);
            int x = 13;
            for (int i = 0; i < container.getItems().size(); i++) {
                pane.addItem(new GuiItem(container.getItems().get(i)), x, 0);
                x += 9;
            }
            pane.setOnClick(event -> event.setCancelled(true));
            gui.addPane(pane);
            gui.show(player);
        }
    }
}
