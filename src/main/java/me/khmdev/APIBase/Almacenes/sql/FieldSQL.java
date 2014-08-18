package me.khmdev.APIBase.Almacenes.sql;

public class FieldSQL {
	protected String name;
	protected Object data;
	public FieldSQL(String nam,Object dat){
		data=(dat);
		name=(nam);
	}
	public String getName() {
		return name;
	}
	public Object getData() {
		return data;
	}
}
