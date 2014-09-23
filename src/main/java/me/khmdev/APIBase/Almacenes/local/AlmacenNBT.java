package me.khmdev.APIBase.Almacenes.local;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

import org.bukkit.Bukkit;

import me.khmdev.APIBase.Almacenes.Almacen;
import me.khmdev.APIBase.Almacenes.Datos;
import me.khmdev.APIBase.Almacenes.local.nbt.CompressedStreamTools;
import me.khmdev.APIBase.Almacenes.local.nbt.NBTBase;
import me.khmdev.APIBase.Almacenes.local.nbt.NBTTagCompound;
import me.khmdev.APIBase.Auxiliar.Auxiliar;

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
		String other = name;
		File backup = new File(other.replace(".dat", "_backup.dat"));
		if (backup.exists() && backup.isFile()) {
			if (f.exists() && f.isFile()) {
				f.delete();
			}
			backup.renameTo(f);
			f=new File(name);
		}
		if (f.exists()&&f.isFile()) {

			FileInputStream in;
			try {
				in = new FileInputStream(f);
				nbt = CompressedStreamTools.readCompressed(in);
				return;
			} catch (Exception e) {
				Bukkit.getLogger()
				.severe("Error al cargar datos de \n"+
							name);
			}
			
			
			File f2= new File(name.replace(".dat", "_2.dat"));
			if(f2.exists()){
				f2.renameTo(f);
				try {
					
					in = new FileInputStream(f);
					nbt = CompressedStreamTools.readCompressed(in);
				} catch (IOException e) {
					Bukkit.getLogger()
					.severe("Error al cargar Backup datos de \n"+
								name);
				}
			}else{
				f.delete();
				try {
					f.createNewFile();
					nbt = new NBTTagCompound();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		} else {
			nbt = new NBTTagCompound();
		}
	}

	private void backup() {
		File f = new File(name),
				f2= new File(name.replace(".dat", "_2.dat"));
		if(f2.exists()&&f2.isFile()){
			f2.delete();try {
				f2.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		f.renameTo(f);
		/*
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
					for (File fd : new File(ruta + File.separator + "backup_2")
							.listFiles()) {
						fd.delete();
					}
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
				+ time.getMinutes() + ".dat");
		if (f2.exists()) {
			f2 = new File(ruta + File.separator + "backup" + File.separator
					+ time.getDay() + "_" + time.getHours() + "_"
					+ time.getMinutes() + "_" + time.getSeconds() + ".dat");
		}
		if (f.exists()) {
			f.renameTo(f2);
		}*/

	}

	@Override
	public String toString() {
		return nbt.toString();
	}

	@Override
	public void finalizar() {

		FileOutputStream out = null;
		File file = null;
		try {
			file = new File(name + "_tmp");
			File despk = file.getParentFile();

			if (!despk.exists()) {
				despk.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
		
			out = new FileOutputStream(name + "_tmp");
			CompressedStreamTools.writeCompressed(nbt, out);
			out.flush();
			out.close();
			backup();
			file.renameTo(new File(name));
			
		} catch (FileNotFoundException e) {
			
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
