package me.khmdev.APIBase.Almacenes;

import java.util.List;



public interface Almacen {

	public void iniciar();

	public void finalizar();

	public void cargar();

	public void guardar();

	public void setByte(String par1Str, byte par2);

	public void setShort(String par1Str, short par2);

	public void setInteger(String par1Str, int par2);

	public void setLong(String par1Str, long par2);

	public void setFloat(String par1Str, float par2);

	public void setDouble(String par1Str, double par2);

	public void setString(String par1Str, String par2Str);

	public void setByteArray(String par1Str, byte[] par2ArrayOfByte);

	public void setIntArray(String par1Str, int[] par2ArrayOfInteger);

	public void setAlmacen(String par1Str, Almacen almacen);

	public void setBoolean(String par1Str, boolean par2);

	public boolean hasKey(String par1Str);

	public byte getByte(String par1Str);

	public short getShort(String par1Str);

	public int getInteger(String par1Str);

	public long getLong(String par1Str);

	public float getFloat(String par1Str);

	public double getDouble(String par1Str);

	public String getString(String par1Str);

	public byte[] getByteArray(String par1Str);

	public int[] getIntArray(String par1Str);

	public Almacen getAlmacen(String par1Str);

	public boolean getBoolean(String par1Str);

	public String getName();

	List<Almacen> getAlmacenes();

	public void leer(Datos dat, String string);
	
	public void escribir(Datos dat, String string);

	public List<String> getKeys();
	
	public int getType(String s);

	public void clear();
}
