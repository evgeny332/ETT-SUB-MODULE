package test;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map map = new HashMap();  
        map.put( "MessageTerminatingId", "60174987390" );  
        map.put( "MessageStatus", "1" ); 
        JSONObject obj= JSONObject.fromObject(map);      
        String s = obj.toString();
       System.out.println(s);
       
	}

}
