package me.khmdev.APIBase;


import java.util.Iterator;
import java.util.List;

import me.khmdev.APIBase.Almacenes.Central;
import me.khmdev.APIBase.Almacenes.ConstantesAlmacen;
import me.khmdev.APIBase.Almacenes.sql.AlmacenSQL;
import me.khmdev.APIBase.Almacenes.sql.player.SQLPlayerData;
import me.khmdev.APIBase.Auxiliar.Spamer;
import me.khmdev.APIBase.Auxiliar.Updater;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.java.JavaPlugin;

public class API extends JavaPlugin {

	private Central central;
	private static API instance = null;
	private AlmacenSQL sql;
	private Spamer spam;


	
	public void onEnable() {

		sql = new AlmacenSQL(this);
		if(sql.isEnable()){
			for (String s : ConstantesAlmacen.sql) {
				sql.sendUpdate(s);
			}
			
		}
		SQLPlayerData.init(sql);

		instance = this;
		central = new Central(this);


		central.cargar();
		getLogger().info("API Cargado");
		//Updater.update(this);
	}


	public Central getCentral() {
		return central;
	}

	public void onDisable() {
		central.guardar();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (cmd.getName().equalsIgnoreCase("APIUpdate")) {
			if(args.length!=0&&args[0].equalsIgnoreCase("forzar")){
				Updater.forceUpdate(sender);

				return true;
			}
			Updater.updateAll(sender);

			return true;
		}
		
		return false;
	}

	public static API getInstance() {
		return instance;
	}

	public static MetadataValue getMetadata(Metadatable b, String s) {
		if (b == null) {
			return null;
		}
		List<MetadataValue> list = b.getMetadata(s);
		if (list == null) {
			return null;
		}
		Iterator<MetadataValue> it = list.iterator();
		while (it.hasNext()) {
			MetadataValue next = it.next();
			if (next.getOwningPlugin().equals(instance)) {
				return next;
			}
		}
		return null;
	}

	public static void setMetadata(Metadatable b, String string, Object o) {
		b.setMetadata(string, new FixedMetadataValue(instance, o));
	}

	public static void removeMetadata(Metadatable b, String string) {
		b.removeMetadata(string, instance);
	}

	public AlmacenSQL getSql() {
		return sql;
	}


	public Spamer getSpam() {
		return spam;
	}

}
