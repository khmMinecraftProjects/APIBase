package me.khmdev.APIBase.Almacenes.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.PreparedStatement;

public class Consulta {
	private ResultSet r;
	private PreparedStatement stm;

	public Consulta(ResultSet re, PreparedStatement statement) {
		r = re;
		stm = statement;
	}

	public void close() {
		if (r != null) {
			try {
				r.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stm != null) {
			try {
				stm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public ResultSet getR() {
		return r;
	}

	public PreparedStatement getStm() {
		return stm;
	}

}
