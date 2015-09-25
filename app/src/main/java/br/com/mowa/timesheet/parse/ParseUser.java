package br.com.mowa.timesheet.parse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.mowa.timesheet.entity.UserEntity;

/**
 * Created by walky on 9/25/15.
 */
public class ParseUser {

    public UserEntity jsonObjectToUserEntity(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        JSONObject object = jsonArray.getJSONObject(0);
        UserEntity user = new UserEntity();
        user.setId(object.optString("id"));
        user.setName(object.optString("name"));
        user.setUserName(object.optString("username"));
        user.setActivite(object.optBoolean("activite"));

        return user;
    }
}
