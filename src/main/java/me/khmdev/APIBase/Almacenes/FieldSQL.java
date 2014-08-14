package me.khmdev.APIBase.Almacenes;

public class FieldSQL {
	private String name;
	private Object data;
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
