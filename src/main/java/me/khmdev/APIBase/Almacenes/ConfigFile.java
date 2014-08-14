package me.khmdev.APIBase.Almacenes;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigFile {
	FileConfiguration config;
	String name;
	File path;

	public ConfigFile(File dataFolder, String nam) {
		name = nam;
		path = dataFolder;
		if (!name.endsWith(".yml")) {
			name += ".yml";
		}
		config=createConfig();
	}
	public void reload(){
		config=createConfig();
	}
	
	private FileConfiguration createConfig() {

		File file = new File(path, name);

		if (!file.exists()) {
			path.mkdir();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return YamlConfiguration.loadConfiguration(file);
	}

	public void saveConfig() {

		File file = new File(path, name);
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public FileConfiguration getConfig() {
		return config;
	}
}
