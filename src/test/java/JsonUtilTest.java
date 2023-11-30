import com.google.gson.Gson;

public class JsonUtilTest {
	
	public static void main(String[] args) {
		
	User user = new User(5797791, "Anagh Patel", "patel_anagh@schoen.teste","female", "active");
	Gson gson = new Gson();
	String jsonStr = gson.toJson(user);
	System.out.println(jsonStr);
	
	String str = "{\"id\":5797791, \"name\":\"Anagh Patel\", \"email\":\"patel_anagh@schoen.teste\", \"gender\":\"female\", \"status\":\"active\" }";
	User user1 = gson.fromJson(str, User.class);
	System.out.println(user.getEmail());
	
	

}
}
