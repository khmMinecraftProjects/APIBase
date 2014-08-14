package me.khmdev.APIBase.Almacenes;

import me.khmdev.APIBase.Almacenes.ConstantesAlmacen.typeVar;

public class varSQL {
	private String name,auxlon=null;
	private typeVar type;
	private int lon=0;
	public varSQL(String s,typeVar t){
		name=s;type=t;
	}
	public varSQL(String s,typeVar t,int i){
		name=s;type=t;lon=i;
	}
	public varSQL(String s,typeVar t,String auxlo){
		name=s;type=t;auxlon=auxlo;
	}
	public String toString(){
		return name +" "+getType();
	}
	private String getType(){
		String s="",s2="";
		if(lon!=0){ s="("+lon+")";}
		if(auxlon!=null){ s="("+auxlon+")";}

		if(type.equals(typeVar.inteteger)){
			return "int"+s;
		}else if(type.equals(typeVar.Boolean)){
			return "boolean"+s;
		}else if(type.equals(typeVar.Float)){
			return "float"+s;
		}else if(type.equals(typeVar.Double)){
			return "double"+s;
		}else if(type.equals(typeVar.Char)){
			return "char"+s;
		}else if(type.equals(typeVar.Enum)){
			return "enum"+s2;
		}else if(type.equals(typeVar.set)){
			return "set"+s2;
		}
		
		return type.name()+s;
	}
}
