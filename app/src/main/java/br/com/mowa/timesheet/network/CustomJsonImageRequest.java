package br.com.mowa.timesheet.network;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by walky on 11/17/15.
 */
public class CustomJsonImageRequest extends JsonObjectRequest {


    public CustomJsonImageRequest(int method, String url, String requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    @Override
    public String getBodyContentType() {
        return "plain/text";
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> customHeader = new HashMap<String, String>();

        customHeader.put("X-Api-Token", "QdqV9mQud/PT0LiiEW4zpuJeuCWfEsToMqiuto98XyT5U48CymfdXW/m5us+Tcx9dewwJuiYOYi2JFdd8qD0Rw==");
        customHeader.put("Content-Type", "image/jpeg");
        customHeader.put("X-File-Encoding", "base64");

        return customHeader;
    }
}
