package me.dovide.bookcopy.config;

import me.dovide.bookcopy.utils.ColorUtil;
import org.bukkit.configuration.file.YamlConfiguration;

public class CustomConfig extends YamlConfiguration {

    @Override
    public String getString(String text) {
        return ColorUtil.colorize(super.getString(text));
    }
}
