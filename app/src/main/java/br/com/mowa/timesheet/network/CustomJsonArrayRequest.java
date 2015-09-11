package br.com.mowa.timesheet.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by walky on 9/11/15.
 */
public class CustomJsonArrayRequest extends Request<JSONArray> {
    private Listener<JSONArray> response;
    private Map<String, String> params;

    public CustomJsonArrayRequest(int method, String url, Map<String, String> params, Response.Listener<JSONArray> response, Response.ErrorListener listener) {
        super(method, url, listener);
        this.response = response;
        this.params = params;

    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("X-Api-Token", "QdqV9mQud/PT0LiiEW4zpuJeuCWfEsToMqiuto98XyT5U48CymfdXW/m5us+Tcx9dewwJuiYOYi2JFdd8qD0Rw==");

        return(header);
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String js = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return (Response.success(new JSONArray(js), HttpHeaderParser.parseCacheHeaders(response)));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void deliverResponse(JSONArray response) {
        this.response.onResponse(response);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
