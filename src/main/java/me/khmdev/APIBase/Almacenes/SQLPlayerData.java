package me.khmdev.APIBase.Almacenes;

import java.sql.ResultSet;

import me.khmdev.APIBase.Almacenes.ConstantesAlmacen.typeVar;

public class SQLPlayerData {
	private static AlmacenSQL sql;

	public static void init(AlmacenSQL sql2) {
		sql = sql2;
		if(!sql.existTable("users")){
			sql.createTab("users", new varSQL("id", typeVar.varchar,20));
			sql.setUnique("users","id");
		}
	}

	public static boolean addVars(varSQL...vars) {
		return sql.anadirColumnas("users", vars);
	}

	public static ResultSet getPlayer(String pl) {
		
		return sql.getValue("users", "id", pl);

	}
	public static ResultSet getPlayerValue(String pl,String value) {
		
		return sql.getValue(value,"users", "id", pl);

	}
	public static boolean existVar(String field) {
		return sql.existVar("users", field);
	}
	public static boolean setVar(String pl,String field,Object edit) {
		return sql.changeData("users", "id", "'"+pl+"'", field, edit+"");
	}
	
	public static boolean existUser(String pl) {
		return sql.existId("users", "id", pl);
	}
	public static void crearUser(String pl) {
		sql.createField("users", new FieldSQL("id", pl));
	}
}
