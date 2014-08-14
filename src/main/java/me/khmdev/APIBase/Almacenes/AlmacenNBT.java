package me.khmdev.APIBase.Almacenes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

import me.khmdev.APIBase.Auxiliar.Auxiliar;
import me.khmdev.APIBase.nbt.CompressedStreamTools;
import me.khmdev.APIBase.nbt.NBTBase;
import me.khmdev.APIBase.nbt.NBTTagCompound;

public class AlmacenNBT implements Almacen {

	NBTTagCompound nbt;
	String name;

	public AlmacenNBT(String nam) {
		nbt = null;
		name = nam;
		iniciar();
	}

	public AlmacenNBT(String folder, String nam) {
		nbt = null;
		name = folder + nam;
		createFolders(folder);
		iniciar();
	}

	public List<String> getKeys() {
		List<String> s = new LinkedList<String>();
		Iterator<NBTBase> it = nbt.getTags().iterator();
		while (it.hasNext()) {
			s.add(it.next().getName());
		}
		return s;
	}

	public static void createFolders(String dir) {
		int i = 0;
		String s = "";

		while (true) {
			String x = Auxiliar.getSeparate(dir, i, File.separator.charAt(0));

			if (x.length() == 0) {
				return;
			}
			s += x + File.separator;
			File f = new File(s);
			if (!(f.isDirectory() && f.exists())) {
				f.mkdir();
			}
			i++;
		}
	}

	public int getType(String s) {
		return nbt.getTag(s).getId();
	}

	public AlmacenNBT(NBTTagCompound compoundTag) {
		nbt = compoundTag;
		name = compoundTag.getName();
	}

	@Override
	public void iniciar() {
		File f = new File(name);
		if (f.isFile()) {

			FileInputStream in;
			try {
				in = new FileInputStream(name);
				nbt = CompressedStreamTools.readCompressed(in);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			nbt = new NBTTagCompound();
		}
	}

	@SuppressWarnings("deprecation")
	private void backup() {
		File f = new File(name);
		String ruta = f.getAbsolutePath().substring(0,
				f.getAbsolutePath().length() - f.getName().length());

		File f2 = new File(ruta + File.separator + "backup");

		if (!f2.exists()) {
			f2.mkdir();
		} else {
			if (f2.list().length > 10) {
				if (!new File(ruta + File.separator + "backup_2").exists()) {
					new File(ruta + File.separator + "backup_2").mkdir();
				} else {
					new File(ruta + File.separator + "backup_2").delete();
					// FileUtils.deleteDirectory(new
					// File(ruta+File.separator+"backup_2"));

					new File(ruta + File.separator + "backup_2").mkdir();
				}
				for (String p : f2.list()) {
					new File(ruta + File.separator + "backup" + File.separator
							+ p).renameTo(new File(ruta + File.separator
							+ "backup_2" + File.separator + p));

				}
			}
		}
		Date time = Calendar.getInstance().getTime();

		f2 = new File(ruta + File.separator + "backup" + File.separator
				+ time.getDay() + "_" + time.getHours() + "_"
				+ time.getMinutes());
		if (f2.exists()) {
			f2 = new File(f2.getAbsoluteFile() + "_" + time.getSeconds());
		}
		if (f.exists()) {
			f.renameTo(f2);
		}

	}

	@Override
	public String toString() {
		return nbt.toString();
	}

	@Override
	public void finalizar() {
		backup();

		FileOutputStream out;
		try {
			File file = new File(name);
			File despk =file.getParentFile();

			if(!despk.exists()){
				despk.mkdirs();
			}
			if(!file.exists()) {
				file.createNewFile();
			} 
			out = new FileOutputStream(name);
			CompressedStreamTools.writeCompressed(nbt, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void cargar() {

	}

	@Override
	public void guardar() {

	}

	private NBTTagCompound getNBT() {
		return nbt;
	}

	@Override
	public Almacen getAlmacen(String key) {
		if (nbt.hasKey(key)) {
			return new AlmacenNBT(nbt.getCompoundTag(key));
		} else if (key == "") {
			return null;
		}
		return new AlmacenNBT(key);
	}

	@Override
	public void setByte(String par1Str, byte par2) {
		nbt.setByte(par1Str, par2);
	}

	@Override
	public void setShort(String par1Str, short par2) {
		nbt.setShort(par1Str, par2);
	}

	@Override
	public void setInteger(String par1Str, int par2) {
		nbt.setInteger(par1Str, par2);
	}

	@Override
	public void setLong(String par1Str, long par2) {
		nbt.setLong(par1Str, par2);
	}

	@Override
	public void setFloat(String par1Str, float par2) {
		nbt.setFloat(par1Str, par2);
	}

	@Override
	public void setDouble(String par1Str, double par2) {
		nbt.setDouble(par1Str, par2);
	}

	@Override
	public void setString(String par1Str, String par2Str) {
		nbt.setString(par1Str, par2Str);
	}

	@Override
	public void setByteArray(String par1Str, byte[] par2ArrayOfByte) {
		nbt.setByteArray(par1Str, par2ArrayOfByte);
	}

	@Override
	public void setIntArray(String par1Str, int[] par2ArrayOfInteger) {
		nbt.setIntArray(par1Str, par2ArrayOfInteger);
	}

	@Override
	public void setAlmacen(String par1Str, Almacen almacen) {
		nbt.setCompoundTag(par1Str, ((AlmacenNBT) almacen).getNBT());
	}

	@Override
	public void setBoolean(String par1Str, boolean par2) {
		nbt.setBoolean(par1Str, par2);
	}

	@Override
	public boolean hasKey(String par1Str) {
		return nbt.hasKey(par1Str);
	}

	@Override
	public byte getByte(String par1Str) {
		return nbt.getByte(par1Str);
	}

	@Override
	public short getShort(String par1Str) {
		return nbt.getShort(par1Str);
	}

	@Override
	public int getInteger(String par1Str) {
		return nbt.getInteger(par1Str);
	}

	@Override
	public long getLong(String par1Str) {
		return nbt.getLong(par1Str);
	}

	@Override
	public float getFloat(String par1Str) {
		return nbt.getFloat(par1Str);
	}

	@Override
	public double getDouble(String par1Str) {
		return nbt.getDouble(par1Str);
	}

	@Override
	public String getString(String par1Str) {
		return nbt.getString(par1Str);
	}

	@Override
	public byte[] getByteArray(String par1Str) {
		return nbt.getByteArray(par1Str);
	}

	@Override
	public int[] getIntArray(String par1Str) {
		return nbt.getIntArray(par1Str);
	}

	@Override
	public boolean getBoolean(String par1Str) {
		return nbt.getBoolean(par1Str);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<Almacen> getAlmacenes() {

		Iterator<NBTBase> it = nbt.getTags().iterator();
		List<Almacen> al = new LinkedList<Almacen>();

		while (it.hasNext()) {
			try {
				NBTTagCompound n = (NBTTagCompound) it.next();

				al.add(new AlmacenNBT(n));
			} catch (Exception e) {

			}
		}

		return al;
	}

	@Override
	public void leer(Datos dat, String string) {
		Almacen al = this.getAlmacen(string);
		dat.cargar(al);
	}

	@Override
	public void escribir(Datos dat, String string) {

		Almacen al = this.getAlmacen(string);
		dat.guardar(al);
		setAlmacen(string, al);
	}

	@Override
	public void clear() {
		nbt.clear();

	}

}
