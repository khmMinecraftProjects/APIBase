package me.khmdev.APIBase.Auxiliar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.Map.Entry;

public class UUIDConverter {
	private static HashMap<String, UUID> data = new HashMap<>();
	private static final String PROFILE_URL = "https://api.mojang.com/profiles/minecraft";
	private final static int PROFILES_PER_REQUEST = 100;
	private final static JSONParser jsonParser = new JSONParser();

	public static Map<String, UUID> getConversions(List<String> names)
			throws Exception {
		Map<String, UUID> uuidMap = new HashMap<String, UUID>();
		int requests = (int) Math.ceil(names.size() / PROFILES_PER_REQUEST);
		for (int i = 0; i < requests; i++) {
			HttpURLConnection connection = createConnection();
			String body = JSONArray.toJSONString(names.subList(i * 100,
					Math.min((i + 1) * 100, names.size())));
			writeBody(connection, body);
			JSONArray array = (JSONArray) jsonParser
					.parse(new InputStreamReader(connection.getInputStream()));
			for (Object profile : array) {
				JSONObject jsonProfile = (JSONObject) profile;
				String id = (String) jsonProfile.get("id");
				String name = (String) jsonProfile.get("name");
				UUID uuid = UUIDConverter.getUUID(id);
				uuidMap.put(name, uuid);
				data.put(name, uuid);
			}
		}
		return uuidMap;
	}

	public static UUID getOneConversion(String n) throws Exception {
		HttpURLConnection connection = createConnection();
		String body = JSONArray.toJSONString(Arrays.asList(n));
		writeBody(connection, body);
		JSONArray array = (JSONArray) jsonParser.parse(new InputStreamReader(
				connection.getInputStream()));
		UUID uuid = null;
		for (Object profile : array) {
			JSONObject jsonProfile = (JSONObject) profile;
			String id = (String) jsonProfile.get("id");
			String name = (String) jsonProfile.get("name");
			uuid = UUIDConverter.getUUID(id);

			data.put(name, uuid);
		}
		return uuid;

	}

	private static void writeBody(HttpURLConnection connection, String body)
			throws Exception {
		OutputStream stream = connection.getOutputStream();
		stream.write(body.getBytes());
		stream.flush();
		stream.close();
	}

	private static HttpURLConnection createConnection() throws Exception {
		URL url = new URL(PROFILE_URL);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setUseCaches(false);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		return connection;
	}

	private static UUID getUUID(String id) {
		return UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12)
				+ "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-"
				+ id.substring(20, 32));
	}

	public static byte[] toBytes(UUID uuid) {
		ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
		byteBuffer.putLong(uuid.getMostSignificantBits());
		byteBuffer.putLong(uuid.getLeastSignificantBits());
		return byteBuffer.array();
	}

	public static UUID fromBytes(byte[] array) {
		if (array.length != 16) {
			throw new IllegalArgumentException("Illegal byte array length: "
					+ array.length);
		}
		ByteBuffer byteBuffer = ByteBuffer.wrap(array);
		long mostSignificant = byteBuffer.getLong();
		long leastSignificant = byteBuffer.getLong();
		return new UUID(mostSignificant, leastSignificant);
	}

	public static UUID getUUIDOf(String name) {
		try {
			UUID id = data.get(name);
			return id != null ? id : getOneConversion(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static HashMap<String, UUID> getUUIDOf(List<String> name)
			throws Exception {
		HashMap<String, UUID> map = new HashMap<>();
		List<String> nop = new LinkedList<>();
		for (String st : name) {
			UUID s = null;
			if ((s = data.get(s)) != null) {
				map.put(st, s);
			} else {
				nop.add(st);
			}
		}
		for (Entry<String, UUID> ent : getConversions(nop).entrySet()) {
			map.put(ent.getKey(), ent.getValue());
		}
		return map;
	}
}
