package me.khmdev.APIBase.Almacenes;

import java.util.LinkedList;
import java.util.List;

import me.khmdev.APIBase.API;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;

public class LocAlmacen {

	public static Location cargar(Almacen nbt, String s) {
		Location l = null;
		int[] ints = nbt.getIntArray(s);
		String ww = nbt.getString(s + "W");
		if (ww.length() != 0 && ints.length > 2) {
			World w = getWorld(nbt.getString(s + "W"));
			if (w == null) {
				return l;
			}
			l = new Location(w, ints[0], ints[1], ints[2]);
		}
		return l;
	}

	public static Location cargar(Metadatable pl, String s) {
		Location l=null;
		MetadataValue mw = API.getMetadata(pl, s + "_W"), 
				mx = API.getMetadata(pl, s + "_X"), 
				my = API.getMetadata(pl, s + "_Y"), 
				mz = API.getMetadata(pl, s + "_Z");
		if(mw!=null&&mx!=null&&my!=null&&mz!=null){
			World w = getWorld(mw.asString());
			int x=mx.asInt(),y=my.asInt(),z=mz.asInt();
			if (w == null) {
				return l;
			}
			l = new Location(w, x,y,z);

		}
		return l;
	}

	private static World getWorld(String s) {
		return Bukkit.getServer().getWorld(s);
	}

	public static void guardar(Almacen nbt, Location l, String s) {
		if (l == null) {
			return;
		}
		int[] ints = { (int) l.getX(), (int) l.getY(), (int) l.getZ() };
		nbt.setIntArray(s, ints);
		nbt.setString(s + "W", l.getWorld().getName());
	}
	
	public static void guardar(Metadatable pl, Location l, String s) {
		if(l==null){
			API.removeMetadata(pl, s+"_W");
			API.removeMetadata(pl, s+"_X");
			API.removeMetadata(pl, s+"_Y");
			API.removeMetadata(pl, s+"_Z");
			return;
		}
		API.setMetadata(pl, s+"_W", l.getWorld().getName());
		API.setMetadata(pl, s+"_X", l.getX());
		API.setMetadata(pl, s+"_Y", l.getY());
		API.setMetadata(pl, s+"_Z", l.getZ());
	}
	public static void guardarList(Almacen nbt, List<Location> l, String s) {
		if (l == null) {
			return;
		}
		int i=0;
		Almacen alm=nbt.getAlmacen(s);
		for (Location location : l) {
			guardar(alm, location, s+i);
			i++;
		}
		nbt.setAlmacen(s, alm);
	}
	
	public static List<Location> cargarList(Almacen nbt, String s) {
		List<Location> l =new LinkedList<>();
		Almacen alm=nbt.getAlmacen(s);
		boolean b=true;
		int i=0;
		while (b) {
			Location loc=cargar(alm, s+i);
			if(loc!=null){
				l.add(loc);
			}else{
				b=false;
			}
			i++;
		}
		return l;
	}
}
