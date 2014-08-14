package me.khmdev.APIBase.Auxiliar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Updater implements Listener {

	private static Updater update = null;
	protected List<JavaPlugin> ups,all;

	public Updater() {
		ups = new LinkedList<>();
		all = new LinkedList<>();
	}

	public static void update(JavaPlugin plug) {
		String actual = plug.getDescription().getVersion();
		String ultima = "0";
		ultima = readUrlLine(url + "?Jar=" + plug.getDescription().getName());

		if (ultima == " ") {
			plug.getLogger().info(
					"No se a podido conectar con el servidor para actualizar");
			return;
		}
		if (update == null) {
			update = new Updater();
			Bukkit.getServer().getPluginManager().registerEvents(update, plug);
		}
		update.all.add(plug);
		if (!VersionActualizada(ultima, actual)) {
			plug.getLogger().info(
					"Se debe actualizar de la version " + actual + " ha la "
							+ ultima);
			update.ups.add(plug);
		} else {
			plug.getLogger().info(
					"Esta actualizada a la ultima version " + actual);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void PlayerJoin(PlayerJoinEvent event) {
		if (event.getPlayer().hasPermission("update.command")) {
			if (ups.size() == 0) {
				event.getPlayer().sendMessage("No hay plugins a actualizar");
			} else {
				event.getPlayer()
						.sendMessage(
								"Hay "
										+ ups.size()
										+ " plugins a espera de actualizar. Usa /APIUpdate");
			}
		}
	}

	private final static String url = "http://khmdev.esy.es/Plugin/",
			pass = "fgkjahsdkhflashddasdaflasjdf";

	private static String readUrlLine(String ur) {
		URL url;
		String contenido = "";

		try {
			url = new URL(ur);

			URLConnection uc = url.openConnection();
			uc.connect();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					uc.getInputStream()));
			String inputLine;
			if ((inputLine = in.readLine()) != null) {
				contenido += inputLine;
			} else {
				contenido = null;
			}
			in.close();
		} catch (IOException e) {
			
		}
		return contenido;
	}

	public static void main(String[] args) {
		String jar = "API";
		
		String actual = "0.9";
		String ultima = "";
		ultima = readUrlLine(url + "?Jar=" + jar);

		if (!VersionActualizada(ultima, actual)) {

			return;
		}
	}

	private static boolean VersionActualizada(String ult, String act) {
		String u = "", a = "";
		int o = 0, i = 0;
		while (o < ult.length()) {
			u = "";
			a = "";

			while (o < ult.length() && !(ult.charAt(o) == '.')) {

				u += ult.substring(o, o + 1);
				o++;

			}
			o++;

			while (i < act.length() && !(act.charAt(i) == '.')) {

				a += act.substring(i, i + 1);
				i++;

			}
			i++;
			double ac = Auxiliar.getDouble("0." + a, -1), 
					ul = Auxiliar.getDouble("0." + u, -1);

			if (ac == -1 && ul != -1) {
				return false;
			} else if (ul > ac) {
				return false;
			}
		}

		return true;
	}



	private static void downloadPlug(JavaPlugin plug) {
		try {		
			downloadFile(url + "?Jar=" + plug.getDescription().getName()
					+ "&descargar=" + pass, plug.getName());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void downloadFile(String ur, String out) throws IOException {
		File tmp = Bukkit.getServer().getUpdateFolderFile();
		if (!tmp.exists()) {
			tmp.mkdirs();
		}
		URL url = new URL(ur);
		URLConnection urlCon = url.openConnection();
		InputStream is = urlCon.getInputStream();
		FileOutputStream fos = new FileOutputStream(Bukkit.getServer()
				.getUpdateFolderFile() + File.separator + out + ".jar");
		byte[] array = new byte[1000];
		int leido = is.read(array);
		while (leido > 0) {
			fos.write(array, 0, leido);
			leido = is.read(array);
		}

		is.close();
		fos.close();
	}

	public void downloadAll(CommandSender sender,List<JavaPlugin> list) {
		sender.sendMessage("Se van a actualizar " + list.size() + " plugins");
		Iterator<JavaPlugin> it = list.iterator();
		while (it.hasNext()) {
			downloadPlug(it.next());
		}
		sender.sendMessage("Plugins actualizados, usa /reload para cargarlos");
	}


	
	public static void updateAll(CommandSender sender) {
		if (update == null) {
			sender.sendMessage("No hay plugins para actualizar");
			return;
		}
		update.downloadAll(sender,update.ups);

	}

	public static void forceUpdate(CommandSender sender) {
		if (update == null) {
			sender.sendMessage("No hay plugins para actualizar");
			return;
		}
		update.downloadAll(sender,update.all);
	}
}
