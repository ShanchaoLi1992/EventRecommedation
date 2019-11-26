package rpc;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.*;

import entity.Item;

public class RpcHelper {
	//Writes a JSONObject to http response.
	public static void writeJsonObject(HttpServletResponse response, JSONObject obj) throws IOException {
		response.setContentType("application/json");
		response.getWriter().print(obj);
	}
	//Write a JSONArray to http response.
	public static void writeJsonArray(HttpServletResponse response, JSONArray array) throws IOException {
		response.setContentType("application/json");
		response.getWriter().print(array);
	}
	public static JSONObject readJSONObject(HttpServletRequest request) {
	  	StringBuilder sBuilder = new StringBuilder();
	  	try (BufferedReader reader = request.getReader()) {
	  		String line = null;
	  		while((line = reader.readLine()) != null) {
	  			sBuilder.append(line);
	  		}
	  		return new JSONObject(sBuilder.toString());
	  		
	  	} catch (Exception e) {
	  		e.printStackTrace();
	  	}
	  	return new JSONObject();
	}
	public static JSONArray getJSONArray(List<Item> l) {
		JSONArray jsonArray = new JSONArray();
		for (Item i : l) {
			jsonArray.put(i.toJSONObject());
		}
		return jsonArray;
	}


}
