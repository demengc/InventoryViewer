package com.demeng7215.inventoryviewer.listeners;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class OtherPlayerInventoryClickEvent implements Listener {

	@EventHandler
	public void onOtherPlayerInventoryClick(InventoryClickEvent e) {

		final Inventory top = e.getView().getTopInventory();
		final InventoryType type = top.getType();

		if (type != InventoryType.PLAYER || top.getHolder() == null || !(top.getHolder() instanceof HumanEntity))
			return;

		if (!e.getWhoClicked().getUniqueId().toString()
				.equals(((Player) top.getHolder()).getUniqueId().toString())) e.setCancelled(true);
	}
}
