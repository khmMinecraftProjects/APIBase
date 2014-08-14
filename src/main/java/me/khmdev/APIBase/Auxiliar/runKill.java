package me.khmdev.APIBase.Auxiliar;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class runKill extends BukkitRunnable {
	private int id=-1;
	public void setId(int i){
		id=i;
	}
	public void kill(){
		if(id==-1){return;}
		Bukkit.getServer().getScheduler().cancelTask(id);
	}
}
