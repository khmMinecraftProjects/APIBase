package me.khmdev.APIBase.Almacenes.sql.player;

import java.sql.ResultSet;

import me.khmdev.APIBase.Almacenes.ConstantesAlmacen.typeVar;
import me.khmdev.APIBase.Almacenes.sql.AlmacenSQL;
import me.khmdev.APIBase.Almacenes.sql.FieldSQL;
import me.khmdev.APIBase.Almacenes.sql.FieldSQLChange;
import me.khmdev.APIBase.Almacenes.sql.varSQL;

public class SQLPlayerData {
	private static AlmacenSQL sql;
	public static final String tabla = "usuarios", id = "usuario";

	public static void init(AlmacenSQL sql2) {
		sql = sql2;
		if (!sql.existTable(tabla)) {
			sql.createTab(tabla, new varSQL(id, typeVar.varchar, 20));
			sql.setUnique(tabla, id);
		}
	}

	public static boolean addVars(varSQL... vars) {
		return sql.anadirColumnas(tabla, vars);
	}

	public static ResultSet getPlayer(String pl) {

		return sql.getValue(tabla, new FieldSQL(id, pl));

	}

	public static ResultSet getPlayerValue(String pl, String value) {

		return sql.getValue(value, tabla, new FieldSQL(id, pl));

	}

	public static boolean existVar(String field) {
		return sql.existVar(tabla, field);
	}

	public static boolean setVar(String pl, String field, Object edit) {
		return sql.changeData(tabla, 
				new FieldSQLChange(field, edit,
				new FieldSQL(id, pl )));
	}

	public static boolean existUser(String pl) {
		return sql.existId(tabla,new FieldSQL(id, pl));
	}

	public static void crearUser(String pl) {
		sql.createField(tabla, new FieldSQL(id, pl));
	}
	public static void crearUser(String pl,FieldSQL field) {
		sql.createField(tabla, new FieldSQL(id, pl),field);
	}
}
