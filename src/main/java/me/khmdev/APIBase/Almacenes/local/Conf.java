package me.khmdev.APIBase.Almacenes.local;

import java.util.Hashtable;

import org.bukkit.configuration.Configuration;



public class Conf {

	protected static Configuration config;

	public static void initConfig(Configuration confi) {
		config = confi;

	}
	public static Object addConf
							(String s, String p, Object cn) {
		config.addDefault(s+"."+p, cn);
		return config.get(s+"."+p);
	}
	
	public static void addConf(String s, String p, String cn) {
		cn=config.getString(s+"."+p, cn);
		config.set(s+"."+p, cn);
		strings.put(p,cn);
	}

	public static void addConf(String s, String p, int cn) {
		cn=config.getInt(s+"."+p, cn);
		config.set(s+"."+p, cn);
		ints.put(p, cn);
	}

	public static void addConf(String s, String p, boolean cn) {
		cn=config.getBoolean(s+"."+p, cn);
		
		config.set(s+"."+p, cn);
		booleans.put(p, cn);
	}

	public static void addConf(String s, String p, double cn) {
		cn=config.getDouble(s+"."+p, cn);
		config.set(s+"."+p, cn);
		doubles.put(p, cn);
	}
	public static Hashtable<String, Boolean> booleans = new Hashtable<String, Boolean>();
	public static Hashtable<String, Integer> ints = new Hashtable<String, Integer>();
	public static Hashtable<String, Double> doubles = new Hashtable<String, Double>();
	public static Hashtable<String, String> strings = new Hashtable<String, String>();

	public static Integer getI(String string) {
		return (Integer) ints.get(string);
	}

	public static boolean getB(String string) {
		return (Boolean) booleans.get(string);
	}

	public static String getS(String string) {
		return (String) strings.get(string);
	}

	public static double getD(String string) {
		return (Double) doubles.get(string);
	}


}
