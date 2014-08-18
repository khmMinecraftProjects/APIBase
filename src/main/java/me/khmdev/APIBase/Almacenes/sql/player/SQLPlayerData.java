package me.khmdev.APIBase.Almacenes.sql.player;

import java.sql.ResultSet;

import me.khmdev.APIBase.Almacenes.ConstantesAlmacen;
import me.khmdev.APIBase.Almacenes.ConstantesAlmacen.typeVar;
import me.khmdev.APIBase.Almacenes.sql.AlmacenSQL;
import me.khmdev.APIBase.Almacenes.sql.FieldSQL;
import me.khmdev.APIBase.Almacenes.sql.FieldSQLChange;
import me.khmdev.APIBase.Almacenes.sql.varSQL;

public class SQLPlayerData {
	private static AlmacenSQL sql;

	public static void init(AlmacenSQL sql2) {
		sql = sql2;
		if (!sql.existTable(ConstantesAlmacen.tabla)) {
			sql.createTab(ConstantesAlmacen.tabla, new varSQL(ConstantesAlmacen.id, typeVar.varchar, 20));
			sql.setUnique(ConstantesAlmacen.tabla, ConstantesAlmacen.id);
		}
	}

	public static boolean addVars(varSQL... vars) {
		return sql.anadirColumnas(ConstantesAlmacen.tabla, vars);
	}

	public static ResultSet getPlayer(String pl) {

		return sql.getValue(ConstantesAlmacen.tabla, new FieldSQL(ConstantesAlmacen.id, pl));

	}

	public static ResultSet getPlayerValue(String pl, String value) {

		return sql.getValue(value, ConstantesAlmacen.tabla, new FieldSQL(ConstantesAlmacen.id, pl));

	}

	public static boolean existVar(String field) {
		return sql.existVar(ConstantesAlmacen.tabla, field);
	}

	public static boolean setVar(String pl, String field, Object edit) {
		return sql.changeData(ConstantesAlmacen.tabla, 
				new FieldSQLChange(field, edit,
				new FieldSQL(ConstantesAlmacen.id, pl )));
	}

	public static boolean existUser(String pl) {
		return sql.existId(ConstantesAlmacen.tabla,new FieldSQL(ConstantesAlmacen.id, pl));
	}

	public static void crearUser(String pl) {
		sql.createField(ConstantesAlmacen.tabla, new FieldSQL(ConstantesAlmacen.id, pl));
	}
	public static void crearUser(String pl,FieldSQL field) {
		sql.createField(ConstantesAlmacen.tabla, new FieldSQL(ConstantesAlmacen.id, pl),field);
	}
}
