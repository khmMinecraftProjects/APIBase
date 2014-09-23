package me.khmdev.APIBase.Almacenes.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import me.khmdev.APIBase.API;

public class AlmacenSQL {
	private Connection conexion;
	private boolean enable;

	public AlmacenSQL(API api) {
		conexion = AuxiliarSQL.sqlConection(api);

		if (conexion == null) {
			enable = (false);
			api.getLogger().info(
					"No se ha podido establecer conexion con la bd SQL");
		} else {
			enable = (true);
			api.getLogger().info("Se ha establecido conexion con la bd SQL");
		}
	}

	@Deprecated
	public ResultSet sendQuerryDEP(String query) {
		if (conexion == null) {
			return null;
		}
		try {
			PreparedStatement statement = conexion.prepareStatement(query);
			ResultSet r = statement.executeQuery();
			// statement.close();
			return r;
		} catch (Exception e) {
			return null;
		}
	}

	public Consulta sendQuerry(String query) {
		if (conexion == null) {
			return null;
		}
		PreparedStatement statement= null;
		try {
			statement = conexion.prepareStatement(query);
			ResultSet r = statement.executeQuery();
			if(r==null){
				statement.close();
				return null;
			}
			Consulta c = new Consulta(r, statement);
			return c;
		} catch (Exception e) {
			if (statement!=null){try {
				statement.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}}
			return null;
		}
	}

	public int sendUpdate(String query) {
		if (conexion == null) {
			return -1;
		}
		PreparedStatement statement=null;
		try {
			statement = conexion.prepareStatement(query);
			int r = statement.executeUpdate();
			return r;
		} catch (Exception e) {
			return -1;
		} finally {
			if(statement!=null){try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}}
		}
	}

	public boolean existVar(String tabla, String var) {
		if (conexion == null) {
			return false;
		}
		Consulta c=null;
		try {
			c = sendQuerry("SELECT * FROM INFORMATION_SCHEMA.COLUMNS"
					+ " WHERE TABLE_NAME = '" + tabla + "' "
					+ "AND column_name = '" + var + "'");
			boolean b = c.getR().next();
			c.close();
			return b;
		} catch (Exception e) {
			return false;
		}finally{
			if(c!=null){c.close();}
		}

	}

	public boolean existTable(String Tabla) {
		if (conexion == null) {
			return false;
		}

		Consulta c=sendQuerry("desc " + Tabla + ";");
		boolean b=c!=null&&c.getR()!=null;
		c.close();
		return b;
		
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

	public boolean changeData(String table, FieldSQLChange field) {
		if (conexion == null) {
			return false;
		}
		String f = field.getWhere(), v = field.getSet();
		if (f == "") {
			f = "1";
		}
		return sendUpdate("UPDATE `" + table + "` SET " + v + " WHERE " + f) >= 0;
	}

	public boolean existId(String table, FieldSQL... fields) {
		if (conexion == null) {
			return false;
		}
		String f = "";
		for (int i = 0; i < fields.length; i++) {
			f += "`" + fields[i].getName() + "` =  '" + fields[i].getData()
					+ "'";
			if (i + 1 < fields.length) {
				f += ",";
			}
		}
		if (f == "") {
			f = "1";
		}
		Consulta c=null;
		try {

			c= sendQuerry("SELECT * FROM  `" + table + "` WHERE  " + f);
			Boolean b=c.getR().next();
			c.close();
			return b;
		} catch (Exception e) {
			return false;
		}finally{
			c.close();
		}
	}

	public int createField(String tab, FieldSQL... fields) {
		if (conexion == null) {
			return -1;
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

		return sendUpdate("INSERT INTO  `" + tab + "` (" + f + ")" + "VALUES ("
				+ v + ");");
	}

	public int deleteField(String tabla, FieldSQL... fields) {
		if (conexion == null) {
			return -1;
		}
		String f = "";
		for (int i = 0; i < fields.length; i++) {
			f += "`" + fields[i].getName() + "` =  '" + fields[i].getData()
					+ "'";
			if (i + 1 < fields.length) {
				f += ",";
			}
		}
		return sendUpdate("DELETE FROM `" + tabla + "` WHERE " + f + "\n");
	}

	public Consulta getValue(String table, FieldSQL... fields) {
		if (conexion == null) {
			return null;
		}
		String f = "";
		for (int i = 0; i < fields.length; i++) {
			f += "`" + fields[i].getName() + "` =  '" + fields[i].getData()
					+ "'";
			if (i + 1 < fields.length) {
				f += ",";
			}
		}
		if (f == "") {
			f = "1";
		}
		return sendQuerry("SELECT * FROM  `" + table + "` WHERE  " + f);
	}

	public Consulta getValue(String value, String table, FieldSQL... fields) {
		if (conexion == null) {
			return null;
		}

		String f = "";
		for (int i = 0; i < fields.length; i++) {
			f += "`" + fields[i].getName() + "` =  '" + fields[i].getData()
					+ "'";
			if (i + 1 < fields.length) {
				f += ",";
			}
		}
		if (f == "") {
			f = "1";
		}

		return sendQuerry("SELECT `" + value + "` FROM  `" + table
				+ "` WHERE  " + f);
	}

	public boolean isEnable() {
		return enable;
	}

}
