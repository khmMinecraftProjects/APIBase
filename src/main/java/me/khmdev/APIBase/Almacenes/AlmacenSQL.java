package me.khmdev.APIBase.Almacenes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import me.khmdev.APIBase.API;

public class AlmacenSQL {
	private Connection conexion;
	private boolean enable;
	public AlmacenSQL(API api) {
		conexion = AuxiliarSQL.sqlConection(api);
		
		if(conexion==null){
			enable=(false);
			api.getLogger().info("No se ha podido establecer conexion con la bd SQL");
		}else{
			enable=(true);
			api.getLogger().info("Se ha establecido conexion con la bd SQL");
		}
	}

	private ResultSet sendQuerry(String query) {
		if (conexion == null) {
			return null;
		}
		try {
			PreparedStatement statement = conexion.prepareStatement(query);
			return statement.executeQuery();
		} catch (Exception e) {
			return null;
		}
	}

	private int sendUpdate(String query) {
		if (conexion == null) {
			return -1;
		}

		try {
			PreparedStatement statement = conexion.prepareStatement(query);
			return statement.executeUpdate();
		} catch (Exception e) {
			return -1;
		}
	}

	public boolean existVar(String tabla, String var) {
		if (conexion == null) {
			return false;
		}
		
		try {
			return sendQuerry("SELECT * FROM INFORMATION_SCHEMA.COLUMNS"
					+ " WHERE TABLE_NAME = '" + tabla + "' "
					+ "AND column_name = '" + var + "'").next();
		} catch (Exception e) {
			return false;
		}

	}

	public boolean existTable(String Tabla) {
		if (conexion == null) {
			return false;
		}

		return sendQuerry("desc " + Tabla + ";") != null;
	}

	public boolean createTab(String s, varSQL... objects) {
		if (conexion == null) {
			return false;
		}

		String vars = "";
		for (int i = 0; i < objects.length; i++) {
			vars += objects[i].toString();
			if (i + 1 < objects.length) {
				vars += ",";
			}
		}
		return sendUpdate("CREATE TABLE " + s + " (" + vars + ");") >= 0;
	}

	public boolean anadirColumnas(String s, varSQL... objects) {
		if (conexion == null) {
			return false;
		}

		String vars = "";
		for (int i = 0; i < objects.length; i++) {
			vars += objects[i].toString();
			if (i + 1 < objects.length) {
				vars += ",";
			}
		}
		return sendUpdate("ALTER TABLE `" + s + "` ADD (" + vars + ");") >= 0;
	}

	public boolean setUnique(String tab, String var) {
		if (conexion == null) {
			return false;
		}

		return sendUpdate("ALTER TABLE  `" + tab + "` ADD UNIQUE (`" + var
				+ "`);") >= 0;
	}

	public boolean changeData(String table, String idname, String id,
			String field, String edit) {
		if (conexion == null) {
			return false;
		}

		return sendUpdate("UPDATE `" + table + "` SET " + field + " = " + edit
				+ " WHERE " + idname + " = " + id + ";") >= 0;
	}

	public boolean existId(String table, String idname, String id) {
		if (conexion == null) {
			return false;
		}

		try {

			return sendQuerry(
					"SELECT * FROM  `" + table + "` WHERE  `" + idname
							+ "` LIKE  '" + id + "'").next();
		} catch (Exception e) {
			return false;
		}
	}

	public boolean createField(String tab, FieldSQL... fields) {
		if (conexion == null) {
			return false;
		}

		String f = "", v = "";
		for (int i = 0; i < fields.length; i++) {
			f += "`" + fields[i].getName() + "`";
			v += "'" + fields[i].getData() + "'";
			if (i + 1 < fields.length) {
				f += ",";
				v += ",";
			}
		}
		
		sendUpdate("INSERT INTO  `" + tab + "` (" + f + ")" + "VALUES (" + v
				+ ");");
		return true;
	}

	public ResultSet getValue(String table, String idname, String id) {
		if (conexion == null) {
			return null;
		}

		return sendQuerry("SELECT * FROM  `" + table + "` WHERE  `" + idname
				+ "` LIKE  '" + id + "'");
	}
	public ResultSet getValue(String value,String table, String idname, String id) {
		if (conexion == null) {
			return null;
		}

		return sendQuerry("SELECT `"+value+"` FROM  `" + table + "` WHERE  `" + idname
				+ "` LIKE  '" + id + "'");
	}
	public boolean isEnable() {
		return enable;
	}

}
