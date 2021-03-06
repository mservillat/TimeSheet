package br.com.mowa.timesheet.parse;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.mowa.timesheet.model.UserModel;

/**
 * Created by walky on 9/25/15.
 */
public class ParseUser {

    private OnParseFinishListener listener;

    public interface OnParseFinishListener {
        void onParseFinishListener(UserModel user);
    }

    public void parseJsonToUserModel(JSONObject jsonObject, OnParseFinishListener listener) {
        this.listener = listener;
        new ParseResponseUserAsyncTask().execute(jsonObject);

    }


    public class ParseResponseUserAsyncTask extends AsyncTask<JSONObject, Void, UserModel> {

        @Override
        protected UserModel doInBackground(JSONObject... params) {
            UserModel user;
            JSONObject jsonObject = params[0];
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                JSONObject object = jsonArray.getJSONObject(0);
                user = new UserModel();
                user.setId(object.optString("id"));
                user.setName(object.optString("name"));
                user.setUserName(object.optString("username"));
                user.setActivite(object.optBoolean("activite"));
                user.setProfilePicture(object.optString("profile_picture"));
                user.setUpdatedAt(object.optString("updated_at"));
                return user;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(UserModel user) {
            listener.onParseFinishListener(user);
        }
    }
}
