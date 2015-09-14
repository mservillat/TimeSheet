package br.com.mowa.timesheet.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by walky on 9/11/15.
 */
public class CustomJsonObjectRequest extends Request<JSONObject> {
    private Listener<JSONObject> response;
    private Map<String, String> params;

    public CustomJsonObjectRequest(int method, String url, Map<String, String> params, Listener<JSONObject> response, Response.ErrorListener listener) {
        super(method, url, listener);
        this.response = response;
        this.params = params;

    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("X-Api-Token", "QdqV9mQud/PT0LiiEW4zpuJeuCWfEsToMqiuto98XyT5U48CymfdXW/m5us+Tcx9dewwJuiYOYi2JFdd8qD0Rw==");
        header.put("Content-Type:", "application/json;charset=utf-8");

        return(header);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String js = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return (Response.success(new JSONObject(js), HttpHeaderParser.parseCacheHeaders(response)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        this.response.onResponse(response);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
