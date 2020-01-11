package com.demeng7215.inventoryviewer.commands;

import com.demeng7215.demlib.api.Common;
import com.demeng7215.demlib.api.CustomCommand;
import com.demeng7215.demlib.api.messages.MessageUtils;
import com.demeng7215.inventoryviewer.InventoryViewer;
import com.demeng7215.inventoryviewer.utils.IVItem;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;

public class IVCmd extends CustomCommand {

	private InventoryViewer i;

	public IVCmd(InventoryViewer i) {
		super("inventoryviewer");

		this.i = i;

		setDescription("Main command for InventoryViewer.");
		setAliases(Collections.singletonList("iv"));
	}

	@Override
	protected void run(CommandSender sender, String[] args) {

		if (args.length < 1 || !args[0].equalsIgnoreCase("give")) {
			MessageUtils.tellWithoutPrefix(sender, "&bRunning InventoryViewer v" + Common.getVersion() + ".");
			return;
		}

		if (!sender.isOp()) {
			MessageUtils.tell(sender, i.getSettings().getString("no-perms"));
			return;
		}

		if (Bukkit.getServer().getPlayerExact(args[1]) == null) {
			MessageUtils.tell(sender, i.getSettings().getString("player-offline"));
			return;
		}

		int amount = 1;

		if (args.length == 3 && isNumeric(args[2])) amount = Integer.parseInt(args[2]);

		final Player target = Bukkit.getServer().getPlayerExact(args[1]);

		target.getInventory().addItem(IVItem.getItem(amount));

		MessageUtils.tell(sender, i.getSettings().getString("successfully-given")
				.replace("%amount%", String.valueOf(amount))
				.replace("%target%", target.getName()));
	}

	public static boolean isNumeric(String strNum) {
		try {
			int number = Integer.parseInt(strNum);
		} catch (final NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}