package me.khmdev.APIBase.Auxiliar;

import java.util.LinkedList;
import java.util.List;

import org.bukkit.entity.Player;

public class UsuariosOcupados {
	private static List<Player>players=new LinkedList<>();
	public static void addPlayer(Player pl){
		players.add(pl);
	}
	public static void removePlayer(Player pl){
		players.remove(pl);
	}
	public static boolean contain(Player pl){
		return players.contains(pl);
	}
}
