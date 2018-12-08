package UserRelated;
import java.util.HashMap;
import java.util.Map;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
public class UserInfo {
	public String username = "";
	public String password = "";
	public Integer userid = -1;
	public String email = "";
	public Integer exerciseAmount = 0; 
	public Integer averageScore  = 0; 
	public Integer friendAmount  = 0; 
	public Integer trainDegree = 0; 
	public Integer wordAmount = 0; 
	public Integer wordPronounce = 0; 
	public String achievements = "000000000";
	private List scores;
	private Map<String,Integer> medals = new HashMap<String,Integer>();
	public void add_one_medal(String medal){
		Integer num = medals.remove(medal);
		medals.put(medal,num+1);
	}
	public int get_medal(String medal){
		return medals.get(medal);
	}
	public String getPassword() {
	  return password;
	}
	public void setPassword(String password) {
	  this.password = password;
	}
	public String getUsername() {
	  return username;
	}
	public void setUsername(String username) {
	  this.username = username;
	}
	public String getEmail() {
		  return email;
	}
	public void setEmail(String email) {
		  this.email = email;
	}
}
