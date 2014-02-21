package com.vjr.api;
import spark.ResponseTransformerRoute;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

 public abstract class JsonTransformerRoute extends ResponseTransformerRoute {

    private Gson gson = new GsonBuilder().serializeNulls().create();
	
    protected JsonTransformerRoute(String path) {
       super(path, "application/json");
    }
	
    @Override
    public String render(Object model) {
	   return gson.toJson(model);
    }
 }
  