package com.demeng7215.inventoryviewer;

import com.demeng7215.demlib.DemLib;
import com.demeng7215.demlib.api.BlacklistSystem;
import com.demeng7215.demlib.api.Common;
import com.demeng7215.demlib.api.Registerer;
import com.demeng7215.demlib.api.files.CustomConfig;
import com.demeng7215.demlib.api.messages.MessageUtils;
import com.demeng7215.inventoryviewer.commands.IVCmd;
import com.demeng7215.inventoryviewer.listeners.IVWandUseEvent;
import com.demeng7215.inventoryviewer.listeners.OtherPlayerInventoryClickEvent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class InventoryViewer extends JavaPlugin {

	private static InventoryViewer instance;

	public CustomConfig settingsFile;

	@Override
	public void onEnable() {

		instance = this;

		DemLib.setPlugin(this, "HujFney2xdbaw9Z2");
		MessageUtils.setPrefix("&8[&bInventoryViewer&8] ");

		try {
			if (BlacklistSystem.checkBlacklist()) {
				MessageUtils.error(null, 0, "Plugin has been forcibly disabled.", true);
				return;
			}
		} catch (final IOException ex) {
			MessageUtils.error(ex, 2, "Failed to connect to auth server.", false);
		}

		getLogger().info("Loading configuration files...");

		try {
			this.settingsFile = new CustomConfig("settings.yml");
		} catch (final Exception ex) {
			MessageUtils.error(ex, 1, "Failed to load settings.yml.", true);
			return;
		}

		MessageUtils.setPrefix(getSettings().getString("prefix"));

		getLogger().info("Registering commands...");
		Registerer.registerCommand(new IVCmd(this));

		getLogger().info("Registering listeners...");
		Registerer.registerListeners(new IVWandUseEvent(this));
		Registerer.registerListeners(new OtherPlayerInventoryClickEvent());


		MessageUtils.console("&aInventoryViewer v" + Common.getVersion() +
				" has been successfully enabled.");
	}

	@Override
	public void onDisable() {
		MessageUtils.console("&cInventoryViewer v" + Common.getVersion() +
				" has been successfully disabled.");
	}

	public FileConfiguration getSettings() {
		return settingsFile.getConfig();
	}

	public static InventoryViewer getInstance() {
		return instance;
	}
}
