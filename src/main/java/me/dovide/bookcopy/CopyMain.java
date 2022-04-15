package me.dovide.bookcopy;

import me.dovide.bookcopy.commands.Copy;
import me.dovide.bookcopy.commands.CopyAdmin;
import me.dovide.bookcopy.config.CustomConfig;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public final class CopyMain extends JavaPlugin {

    private static CopyMain plugin;
    private CustomConfig config;

    @Override
    public void onEnable() {
        // Instance
        plugin = this;

        // Start Mex
        getLogger().info("BookCopy by Dovide | 2.0");

        // Config
        config = createConfig("config.yml");

        // Commands
        getCommand("copybook").setExecutor(new Copy());
        getCommand("copyadmin").setExecutor(new CopyAdmin());

    }

    @Override
    public void onDisable() {
        getLogger().info("BookCopy by Dovide | 2.0");
    }

    public static CopyMain getInstance() {
        return plugin;
    }

    public CustomConfig getConfig(){
        return config;
    }

    public CustomConfig createConfig(String name) {
        File fc = new File(getDataFolder(), name);
        if(!fc.exists()){
            fc.getParentFile().mkdir();
            saveResource(name, false);
        }
        CustomConfig config = new CustomConfig();
        try {
            config.load(fc);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        return config;
    }

    public void reloadCustomConfig() {
        config = createConfig("config.yml");
    }
}
