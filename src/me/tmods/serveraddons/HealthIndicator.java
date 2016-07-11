package me.tmods.serveraddons;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

import me.tmods.serverutils.Methods;

public class HealthIndicator extends JavaPlugin implements Listener{
	public File maincfgfile = new File("plugins/TModsServerUtils", "config.yml");
	public FileConfiguration maincfg = YamlConfiguration.loadConfiguration(maincfgfile);
	public String name = "";
	@Override
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
	}
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		try {
		if (event.getEntity() instanceof LivingEntity && maincfg.getBoolean("showMobHealth")) {
			String newName = event.getEntity().getCustomName();
			if (newName == null) {
				newName = "";
			}
			if (!newName.contains("health")) {
				name = newName;
			}
			event.getEntity().setCustomName(((LivingEntity)event.getEntity()).getHealth() / 2 + " " + "health");
			Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
				@Override
				public void run() {
						event.getEntity().setCustomName(name);
				}	
			}, 20);
		}
		} catch (Exception e) {
			Methods.log(e);
		}
	}
}
