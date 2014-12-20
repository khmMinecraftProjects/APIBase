package me.khmdev.APIBase.Auxiliar;

public class Auxiliar {
	public static int getNatural(String s, int defaul) {
		if (isNumeric(s)) {
			return Integer.valueOf(s);
		}
		return defaul;
	}

	public static boolean isNumeric(String s) {
		try {
			Integer.valueOf(s);
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public static double getDouble(String s, int defaul) {
		try {

			return Double.valueOf(s);
		} catch (Exception e) {
			return defaul;
		}
	}
	public static String getSeparate(String txt, int i, char sp) {
		String s = "";
		int o = 0, word = 0;
		if(txt==null){return "";}
		while (o < txt.length() && word <= i) {
			s = "";

			while (o < txt.length() && !(txt.charAt(o) == sp)) {

				s += txt.substring(o, o + 1);
				o++;

			}

			o++;
			word++;
		}
		if (word <= i) {
			return "";
		}
		return s;
	}

	public static String getOriginalName(String name) {
		String res = "";
		name = name.replace("_", " ").toLowerCase();
		int i = 0;
		while (name.length() != 0) {
			char c = name.charAt(0);
			name=name.substring(1);
			if (c == ' ') {
				i = 0;
				res += c;
			} else {
				if (i == 0) {
					res += String.valueOf(c).toUpperCase();
				}else{
					res += c;
				}
				i++;

			}
		}
		return res;
	}
}
