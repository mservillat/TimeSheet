package br.com.mowa.timesheet.model.interfaceModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.com.mowa.timesheet.model.Model;

/**
 * Created by walky on 9/14/15.
 */
public interface InterfaceModel {
    public List<Model> modelBuild(JSONObject response) throws JSONException;
}
