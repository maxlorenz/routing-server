package server;

import com.google.gson.Gson;
import spark.ResponseTransformer;

/**
 * Created by max on 13.05.17.
 */
public class JsonTransformer implements ResponseTransformer {
    private Gson gson = new Gson();

    @Override
    public String render(Object model) throws Exception {
        return gson.toJson(model);
    }
}
