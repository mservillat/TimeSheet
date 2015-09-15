package br.com.mowa.timesheet.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by walky on 9/11/15.
 */
public class CustomJsonObjectRequest extends JsonObjectRequest {

    public CustomJsonObjectRequest(int method, String url, JSONObject requestBody, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, responseListener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> customHeader = new HashMap<String, String>();

        customHeader.put("X-Api-Token", "QdqV9mQud/PT0LiiEW4zpuJeuCWfEsToMqiuto98XyT5U48CymfdXW/m5us+Tcx9dewwJuiYOYi2JFdd8qD0Rw==");
        customHeader.put("Content-Type", "application/json;charset=utf-8");

        return customHeader;
    }
}
