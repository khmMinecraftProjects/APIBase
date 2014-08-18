package me.khmdev.APIBase.Almacenes;


public class ConstantesAlmacen {


	public enum typeVar {
		inteteger,varchar,text,date,tinyint,smallint,mediumint,bigint
		,decimal,Float,Double,real,bit,Boolean,serial,datetime,timestamp,
		time,year,Char,mediumtext,longtext,binary,carbinary,tinyblob,
		mediumblob,blob,longblob,Enum,set,geomety,point,linestring,polygon,
		multipoint,multilinestring,multipolygon,geometricol
	}
	public static final String tabla = "usuarios", id = "usuario";

	public static final String[] sql={
			"CREATE TABLE IF NOT EXISTS "+tabla+" (" +
			"usuario varchar(16) NOT NULL," +
			"PRIMARY KEY ("+id+")" +
			")\n" };
}
