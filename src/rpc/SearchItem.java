package rpc;

import java.io.*;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.*;

import db.DBConnection;
import db.DBConnectionFactory;
import entity.Item;
//import external.TicketMasterClient;

/**
 * Servlet implementation class SearchItem
 */
@WebServlet("/search")
public class SearchItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//response.setContentType("application/json");
		//PrintWriter writer = response.getWriter();
		//TicketMasterClient api = new TicketMasterClient();
		HttpSession session = request.getSession(false);
		if (session == null) {
			response.setStatus(403);
			return;
		}

		// optional
		String userId = session.getAttribute("user_id").toString(); 
		
		
		
		
		double lat = Double.parseDouble(request.getParameter("lat"));
		double lon = Double.parseDouble(request.getParameter("lon"));
		String term = request.getParameter("term");
		//JSONArray array = api.search(lat, lon, null);
		//RpcHelper.writeJsonArray(response, array);
		
		DBConnection connection = DBConnectionFactory.getConnection();
		/*
		List<Item> items = connection.searchItems(lat, lon, term);
		JSONArray array = new JSONArray();
		for (Item item : items) {
  		  JSONObject obj = item.toJSONObject(); 
  		  array.put(obj);
  	  	}
  	  	RpcHelper.writeJsonArray(response, array);
  	  	*/
		
		
          try {
        	  List<Item> items = connection.searchItems(lat, lon, term);
        	  Set<String> favoritedItemIds = connection.getFavoriteItemIds(userId);

        	  JSONArray array = new JSONArray();
        	  for (Item item : items) {
        		  JSONObject obj = item.toJSONObject(); 
        		  obj.put("favorite", favoritedItemIds.contains(item.getItemId()));
        		  array.put(obj);
        	  }
        	  RpcHelper.writeJsonArray(response, array);

         } catch (Exception e) {
        	 e.printStackTrace();
         } finally {
        	 connection.close();
         }

		
		
		
		/*
		if (request.getParameter("username") != null) {
			String username = request.getParameter("username");
			
			try {
				obj.put("username1", new JSONObject().put("username3", "Shanchao"));
				obj.put("username2", username);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			writer.print(obj);
		
			//writer.print("<html><body>");
			//writer.print("<h1>Hello " + username + "</h1>");
			//writer.print("</body></html>");
		}
		*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
