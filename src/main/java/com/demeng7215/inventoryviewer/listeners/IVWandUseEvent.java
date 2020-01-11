package com.demeng7215.inventoryviewer.listeners;

import com.demeng7215.demlib.api.messages.MessageUtils;
import com.demeng7215.inventoryviewer.InventoryViewer;
import com.demeng7215.inventoryviewer.utils.IVItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class IVWandUseEvent implements Listener {

	private InventoryViewer i;

	public IVWandUseEvent(InventoryViewer i) {
		this.i = i;
	}

	@EventHandler
	public void onIVWandUseEvent(PlayerInteractEntityEvent e) {

		if (!e.getEventName().equals("PlayerInteractEntityEvent")) return;

		if (e.getHand() != EquipmentSlot.HAND) return;

		if (!e.getPlayer().getInventory().getItemInMainHand().isSimilar(IVItem.getItem(1))) return;

		if (!(e.getRightClicked() instanceof Player)) return;

		if (IVItem.getPlayersOnCooldown().get(e.getPlayer().getUniqueId()) != null &&
				IVItem.getPlayersOnCooldown().get(e.getPlayer().getUniqueId()) +
						(i.getSettings().getInt("cooldown") * 1000) <= System.currentTimeMillis()) {
			IVItem.getPlayersOnCooldown().remove(e.getPlayer().getUniqueId());
		}

		if (IVItem.getPlayersOnCooldown().containsKey(e.getPlayer().getUniqueId())) {
			MessageUtils.tell(e.getPlayer(), i.getSettings().getString("on-cooldown")
					.replace("%seconds%", String.valueOf((IVItem.getPlayersOnCooldown()
							.get(e.getPlayer().getUniqueId()) + (i.getSettings().getInt("cooldown") * 1000) -
							System.currentTimeMillis()) / 1000)));
			return;
		}

		IVItem.getPlayersOnCooldown().put(e.getPlayer().getUniqueId(), System.currentTimeMillis());

		e.getPlayer().getInventory().setItemInMainHand(IVItem.getItem(
				e.getPlayer().getInventory().getItemInMainHand().getAmount() - 1));

		Player target = (Player) e.getRightClicked();

		e.getPlayer().openInventory(target.getInventory());
	}
}
