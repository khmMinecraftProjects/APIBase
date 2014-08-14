package me.khmdev.APIBase.Almacenes;

import java.sql.Connection;
import java.sql.DriverManager;

import me.khmdev.APIBase.API;
import me.khmdev.APIBase.Auxiliar.Auxiliar;

import org.bukkit.configuration.ConfigurationSection;

public class AuxiliarSQL {
	public static Connection sqlConection(API api) {

		ConfigFile conf = new ConfigFile(api.getDataFolder(), "SQL");
		ConfigurationSection section = conf.getConfig();
		boolean enable = section.getBoolean("enable", false);

		if(!enable){
			section.set("enable", false);
		}
		
		String user = section.getString("user", null);
		if (user == null) {
			section.set("user", "user");
		}
		String pass = section.getString("pass", null);
		if (pass == null) {
			section.set("pass", "pass");
		}
		int port = Auxiliar.getNatural(section.getString("port", ""), -1);
		if (port < 0) {
			section.set("port", 0);
		}
		String host = section.getString("host", null);
		if (host == null) {
			section.set("host", "host");
		}
		String database = section.getString("database", null);
		if (database == null) {
			section.set("database", "database");
		}
		conf.saveConfig();
		if (enable&&user != null && pass != null && port >= 0 && host != null
				&&database!=null) {
			return getConnection(user, pass, host, database, port);
		} else {
			return null;
		}
	}

	private static Connection getConnection(String user, String pass,
			String host, String database, int port) {
		Connection con;
		String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
		try {

			con = DriverManager.getConnection(url, user, pass);

			return con;
			

		} catch (Exception e) {

			return null;
		}
	}
}
