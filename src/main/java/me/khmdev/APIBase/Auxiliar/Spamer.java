package me.khmdev.APIBase.Auxiliar;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Spamer extends BukkitRunnable implements Listener{

	
	private int id;
	public Spamer(JavaPlugin plug,String mens,int seconds){
				
		time=seconds;
		mensaje=mens;
		
		//id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plug,
			//	this, 100, 100L);
		
		plug.getServer().getPluginManager()
		.registerEvents(this,plug);
	}
	public void sendMensaje(Player pl){
		pl.sendMessage(mensaje);
	}
	public void kill(){Bukkit.getScheduler().cancelTask(id);}
	private long init;
	private int time=15*60;
	String mensaje="";
	@Override
	public void run() {
		long now=System.currentTimeMillis();
		if((now-init)/1000>time){
			for (Player player : Bukkit.getServer().getOnlinePlayers()) {
				player.sendMessage(mensaje);
			}
			init=System.currentTimeMillis();
		}
	}
	
	@EventHandler
    public void logIn(PlayerJoinEvent event) {
		event.getPlayer().sendMessage(mensaje);
	}
}
