package com.bartuabihd.antiexplosionreloaded;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class AntiExplosionReloaded extends JavaPlugin implements CommandExecutor {

    private boolean explosionsEnabled = true;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new ExplosionListener(), this);
        getCommand("toggleexplosions").setExecutor(this);
    	getLoggerColored(ChatColor.GRAY + "[" + ChatColor.GREEN + "Anti Explosion Reloaded" + ChatColor.GRAY + "] " + ChatColor.GREEN + "Plugin is enabled!");
        
        new UpdateChecker(this, 114914).getLatestVersion(version -> {
            if (this.getDescription().getVersion().equalsIgnoreCase(version)) {
                getLoggerColored(ChatColor.GRAY + "[" + ChatColor.BLUE + "Anti Explosion Reloaded" + ChatColor.GRAY + "] " + ChatColor.RED + "A new update of the plugin is not available.");
            }
            else {
                getLoggerColored(ChatColor.GRAY + "[" + ChatColor.BLUE + "Anti Explosion Reloaded" + ChatColor.GRAY + "] " + ChatColor.GREEN + "The plugin has a new update. Download link: https://bit.ly/aerplugin");
            }
        });
    }
    
    @Override
    public void onDisable()
	{
		getLoggerColored(ChatColor.GRAY + "[" + ChatColor.GREEN + "Anti Explosion Reloaded" + ChatColor.GRAY + "] " + ChatColor.RED + "Plugin is disabled!");
	}
    
    public class ExplosionListener implements Listener {

        @EventHandler
        public void onExplosion(EntityExplodeEvent event) {
            if (!explosionsEnabled) {
                event.setCancelled(true);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("toggleexplosions")) {
            if (sender.hasPermission("antiexplosionreloaded.toggle")) {
                explosionsEnabled = !explosionsEnabled;
                sender.sendMessage(ChatColor.YELLOW + "Explosions are now " + (explosionsEnabled ? ChatColor.GREEN+"enabled" : ChatColor.RED+"disabled") + ".");
                return true;
            } else {
                sender.sendMessage("You do not have permission to use this command.");
            }
        }
        return false;
    }
    
    private void getLoggerColored(String msg) {
		Bukkit.getConsoleSender().sendMessage(msg);
	}
}
