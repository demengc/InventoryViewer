package com.demeng7215.inventoryviewer.utils;

import com.demeng7215.demlib.api.messages.MessageUtils;
import com.demeng7215.inventoryviewer.InventoryViewer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public final class IVItem {

	private static Map<UUID, Long> cooldown = new HashMap<>();

	public static ItemStack getItem(int amount) {

		InventoryViewer i = InventoryViewer.getInstance();

		ItemStack stack = new ItemStack(Material.valueOf(i.getSettings().getString("item.material")), amount);
		ItemMeta meta = stack.getItemMeta();

		List<String> lore = new ArrayList<>();
		for (String s : i.getSettings().getStringList("item.lore")) lore.add(MessageUtils.colorize(s));

		meta.setDisplayName(MessageUtils.colorize(i.getSettings().getString("item.display-name")));
		meta.setLore(lore);

		for (String s : i.getSettings().getConfigurationSection("item.enchants").getKeys(false))
			if (!s.equalsIgnoreCase("nothing"))
				meta.addEnchant(XEnchantment.valueOf(s).parseEnchantment(),
						i.getSettings().getInt("item.enchants." + s), true);

		if (i.getSettings().getBoolean("item.hide-enchants")) meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

		stack.setItemMeta(meta);

		return stack;
	}

	public static Map<UUID, Long> getPlayersOnCooldown() {
		return cooldown;
	}

}
