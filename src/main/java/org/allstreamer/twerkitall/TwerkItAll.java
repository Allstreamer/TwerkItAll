package org.allstreamer.twerkitall;

import org.allstreamer.twerkitall.events.CrouchListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import  org.bukkit.plugin.PluginManager;
import java.util.logging.Logger;

public final class TwerkItAll extends JavaPlugin {
    public static Logger logger = Bukkit.getLogger();
    public static FileConfiguration config;

    @Override
    public void onEnable() {
        if (config == null) {
            config = this.getConfig();
        }

        config.addDefault("x_range", 2);
        config.addDefault("y_range", 2);
        config.addDefault("z_range", 2);

        config.addDefault("grow_chance", 0.1);

        config.options().copyDefaults(true);
        saveConfig();

        PluginManager manager = getServer().getPluginManager();

        manager.registerEvents(new CrouchListener(), this);

        logger.info("TwerkItAll Installed");
    }
}
