package br.com.mowa.timesheet.parse;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.mowa.timesheet.model.UserModel;

/**
 * Created by walky on 11/16/15.
 */
public class ParseSessions {

    private OnParseFinishListener listener;

    public interface OnParseFinishListener{
        void onParseFinishListener(UserModel user);
    }


    public void  parseJsonSessionsToUserModel(JSONObject jsonObject, OnParseFinishListener listener) {
        this.listener = listener;
        new ParseResponseSessionsAsyncTask().execute(jsonObject);

    }

    public class ParseResponseSessionsAsyncTask extends AsyncTask<JSONObject, Void, UserModel> {

        @Override
        protected UserModel doInBackground(JSONObject... params) {
            JSONObject jsonObject = params[0];
            UserModel user = new UserModel();
            try {
                JSONObject object = jsonObject.getJSONObject("user");
                user.setId(object.optString("id"));
                user.setName(object.optString("name"));
                user.setUserName(object.optString("username"));
                user.setActivite(object.optBoolean("activite"));
                user.setUpdatedAt(object.optString("updated_at"));
                user.setProfilePicture(object.optString("profile_picture"));
                user.setToken(jsonObject.optString("token"));

                return user;

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(UserModel userModel) {
            listener.onParseFinishListener(userModel);
        }
    }
}
