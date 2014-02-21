package com.vjr.api;

import static spark.Spark.get;
import static spark.Spark.setPort;

import java.util.Map;

import spark.Request;
import spark.Response;

import com.vjr.meta.MetaParser;

public class Main {

    public static void main(String[] args) {
        setPort(8081);
        get(new JsonTransformerRoute("/") {
            @Override
            public Object handle(Request request, Response response) {
                String url = request.queryParams("url");
                response.type("application/json; charset=UTF-8"); 
                Map<String, Object> parse = MetaParser.parse(url);
                return parse;
            }
        });
    }
}