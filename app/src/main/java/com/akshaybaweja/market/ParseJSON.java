package com.akshaybaweja.market;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ParseJSON {
    public static String[] names;
    public static String[] addresses;
    public static String[] contacts;
    public static String[] closedOn;
    public static String[] rates;
    public static String[] remarks;
    public static int nshops;

    public static final String JSON_ARRAY = "result";
    public static final String KEY_NAME = "name";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_CONTACT = "contact";
    public static final String KEY_CLOSED = "closed_on";
    public static final String KEY_RATES = "rates";
    public static final String KEY_REMARKS = "remarks";


    private JSONArray shops = null;

    private String json;

    public ParseJSON(String json){
        this.json = json;
    }

    protected void parseJSON(){
        JSONObject jsonObject=null;
        try {
            jsonObject = new JSONObject(json);
            shops = jsonObject.getJSONArray(JSON_ARRAY);
            nshops = shops.length();

            names = new String[nshops];
            addresses = new String[nshops];
            contacts = new String[nshops];
            closedOn = new String[nshops];
            rates = new String[nshops];
            remarks = new String[nshops];

            for(int i=0;i<nshops;i++){
                JSONObject jo = shops.getJSONObject(i);
                names[i] = jo.getString(KEY_NAME);
                addresses[i] = jo.getString(KEY_ADDRESS);
                contacts[i] = jo.getString(KEY_CONTACT);
                closedOn[i] = jo.getString(KEY_CLOSED);
                rates[i] = jo.getString(KEY_RATES);
                remarks[i] = jo.getString(KEY_REMARKS);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}