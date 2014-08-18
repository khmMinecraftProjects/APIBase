package me.khmdev.APIBase.Almacenes.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class FieldSQLChange {
	protected FieldSQL[] where=new FieldSQL[0];	
	protected List<FieldSQL> set=new ArrayList<>();

	public FieldSQLChange(String nam,Object dat,
			FieldSQL...fields){
		where=(fields);
		set.add(new FieldSQL(nam, dat));
	}
	public FieldSQLChange(List<FieldSQL> s,
			FieldSQL...fields){
		where=(fields);
		set=s;
	}
	public FieldSQL[] getFields() {
		return where;
	}
	public String getSet() {
		String v = "";
		Iterator<FieldSQL> ent=set.iterator();
		while (ent.hasNext()){
			FieldSQL next=ent.next();

			v += "`" + next.getName()
					+ "` =  '" + next.getData() + "'";
			if (ent.hasNext()) {
				v += ",";
			}
		}
		return v;
	}

	public String getWhere() {
		String f = "";
		for (int i = 0; i < where.length; i++) {
			f += "`" + where[i].getName()
					+ "` LIKE  '" + where[i].getData() + "'";
			if (i + 1 < where.length) {
				f += ",";
			}
		}
		return f;
	}
}
