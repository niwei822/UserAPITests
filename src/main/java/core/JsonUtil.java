package core;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import userAPITest.User;

public class JsonUtil {
	public static String getJsonString(User user) {
		Gson gson = new Gson();
		String jsonStr = gson.toJson(user);
		System.out.println(jsonStr);
		return jsonStr;
	}
	
	public static User getJavaObjectFromJsonString(String str) {
		Gson gson = new Gson();
		User user1 = gson.fromJson(str, User.class);
		return user1;
	}
	
	public static User createUser(String name, String email, String gender, String status) {
        // Assuming User has a constructor that takes these parameters
        User newUser = new User(0, name, email, gender, status);
        return newUser;
    }

    public static String deleteUserJson(int userId) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", userId);
        String jsonStr = jsonObject.toString();
        System.out.println(jsonStr);
        return jsonStr;
    }

}
