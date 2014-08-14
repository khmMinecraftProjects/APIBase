package me.khmdev.APIBase.Almacenes;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

public class Central {
	private Almacen almacen;
	private List<Datos> lista;
	private String name;
	@SuppressWarnings("unused")
	private JavaPlugin plugin;

	public Central(JavaPlugin plug) {
		CargaConfig(plug);
		plugin = plug;
		name =  plug.getName() + ".dat";
		almacen = new AlmacenNBT(plug.getDataFolder() + File.separator + "Data" + File.separator,name);
		lista = new LinkedList<Datos>();
	}

	public void CargaConfig(JavaPlugin plug) {

		plug.getConfig().options().copyDefaults(true);
		Conf.initConfig(plug.getConfig());
		plug.saveConfig();
	}

	public void insertar(Datos dat) {
		lista.add(dat);
		dat.cargar(almacen);
	}

	public void cargar() {

		//almacen = new AlmacenNBT(name);

		Iterator<Datos> it = lista.iterator();
		while (it.hasNext()) {
			it.next().cargar(almacen);
		}

	}

	public void guardar() {

		Iterator<Datos> it = lista.iterator();
		while (it.hasNext()) {
			it.next().guardar(almacen);
		}
		almacen.finalizar();

	}
}
