package service;


import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;




public class JsonObject extends LinkedHashMap implements Map {
    
    public static JsonObject parseJson(String json){
        JsonObject obj = new JsonObject();
        json = json.trim();
        if ((json.charAt(0) != '{') || (json.charAt(json.length() - 1) != '}')) {
            return obj;
        }
        String key = "", value = "";
        int position = 1, nestedObjs = 0;
        boolean getKey = false, getValue = false;
        while (position < (json.length() - 1)){
            if (nestedObjs > 0){
                value += json.substring(position, position + 1);
            }else if (json.charAt(position) != '"'){
                if (getKey){
                    key += json.substring(position, position + 1);
                }else if (getValue){
                    value += json.substring(position, position + 1);
                }
            }else if ((json.charAt(position) == '"') && (nestedObjs <= 0)){
                if (getKey){
                    getKey = false;
                    key += json.substring(position, position + 1);
                }else if (getValue){
                    value += json.substring(position, position + 1);
                    getValue = false;
                    obj.put(key, value);
                    key = "";
                    value = "";
                    nestedObjs = 0;
                }else{
                    if (key.equals("") && ((json.charAt(position - 1) == ',') || (json.charAt(position - 1) == '{'))){
                        getKey = true;
                        key += json.substring(position, position + 1);
                    }else if (value.equals("") && (json.charAt(position - 1) == ':')){
                        getValue = true;
                        nestedObjs = -1;
                        value += json.substring(position, position + 1);
                    }
                }
            }
            if ((json.charAt(position) == '{') && (nestedObjs != -1)){
                nestedObjs++;
                getValue = true;
                if  (nestedObjs == 1) 
                    value += "{";
            }else if ((json.charAt(position) == '}') && (nestedObjs != -1)){
                nestedObjs--;
                if (nestedObjs == 0){
                    getValue = false;
                    obj.put(key, value);
                    key = "";
                    value = "";
                    nestedObjs = 0;
                }
            }   
            position++;
        }
        return obj;
    }
    
    
    public String toJsonString(){
        String json = "{";
        Set keys = this.keySet();
        Object[] keysArray = keys.toArray();
        for (int i = 0; i < keysArray.length; i++){
          Object keyObj = keysArray[i];
          String key = keyObj.toString();
          String value;
          if (this.get(keyObj) == null) {
              value = "null";
          }else{
              value = this.get(keyObj).toString();
          }
          if (!key.startsWith("\"")) key = "\"" + key;
          if (!key.endsWith("\"")) key += "\"";
          if (!value.startsWith("{") && !value.endsWith("}")){
              if (!value.startsWith("\"")) value = "\"" + value;
              if (!value.endsWith("\"") || (value.length() == 1)) value += "\"";
          }
          if (i != 0) 
              json += ",";
          json += key + ":" + value ;
        }
        json += "}";
        return json;
    }
    
}
