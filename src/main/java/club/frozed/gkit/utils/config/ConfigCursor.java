package club.frozed.gkit.utils.config;

import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class ConfigCursor {

    private final FileConfig fileConfig;

    private String path;

    public ConfigCursor(FileConfig fileConfig, String path) {
        this.fileConfig = fileConfig;
        this.path = path;
    }

    public FileConfig getFileConfig() {
        return this.fileConfig;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean exists() {
        return exists(null);
    }

    public boolean exists(String path) {
        return this.fileConfig.getConfig().contains(this.path + ((path == null) ? "" : ("." + path)));
    }

    public Set<String> getKeys() {
        return getKeys(null);
    }

    public Set<String> getKeys(String path) {
        return this.fileConfig.getConfig().getConfigurationSection(this.path + ((path == null) ? "" : ("." + path)))
                .getKeys(false);
    }

    public String getString(String path) {
        return this.fileConfig.getConfig().getString(((this.path == null) ? "" : (this.path + ".")) + path);
    }

    public boolean getBoolean(String path) {
        return this.fileConfig.getConfig().getBoolean(((this.path == null) ? "" : (this.path + ".")) + "." + path);
    }

    public int getInt(String path) {
        return this.fileConfig.getConfig().getInt(((this.path == null) ? "" : (this.path + ".")) + "." + path);
    }

    public long getLong(String path) {
        return this.fileConfig.getConfig().getLong(((this.path == null) ? "" : (this.path + ".")) + "." + path);
    }

    public List<String> getStringList(String path) {
        return this.fileConfig.getConfig().getStringList(((this.path == null) ? "" : (this.path + ".")) + "." + path);
    }

    public UUID getUuid(String path) {
        return UUID.fromString(this.fileConfig.getConfig().getString(this.path + "." + path));
    }

    public World getWorld(String path) {
        return Bukkit.getWorld(this.fileConfig.getConfig().getString(this.path + "." + path));
    }

    public void set(Object value) {
        set(null, value);
    }

    public void set(String path, Object value) {
        this.fileConfig.getConfig().set(this.path + ((path == null) ? "" : ("." + path)), value);
    }

    public void save() {
        this.fileConfig.save();
    }
}
