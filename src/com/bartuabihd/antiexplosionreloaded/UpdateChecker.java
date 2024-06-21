package com.bartuabihd.antiexplosionreloaded;

import java.util.function.*;
import org.bukkit.*;
import java.net.*;
import java.util.*;
import java.io.*;

public class UpdateChecker {

    private AntiExplosionReloaded plugin;
    private int resourceId;

    public UpdateChecker(AntiExplosionReloaded plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void getLatestVersion(final Consumer<String> consumer) {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
                 Scanner scanner = new Scanner(inputStream)) {
            if (scanner.hasNext()) {
                consumer.accept(scanner.next());
            }
        } catch (IOException exception) {
                plugin.getLogger().info("[Anti Explosion Reloaded] Cannot call updates: " + exception.getMessage());
            }
        });
    }
}