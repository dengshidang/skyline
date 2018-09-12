package com.skyline.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

/**
 * 类名称：Json工具类 <br/>
 * 类描述：Json工具类 <br/>
 * 创建人： Z.CJ <br/>
 * 创建时间： 2018年3月9日 下午1:23:37
 *  
 * @updateRemark 修改备注：
 */
public class JSONUtil {

        private static Gson gson = null;

        static {
                gson = new Gson();
        }

        public static synchronized Gson newInstance() {
                if (gson == null) {
                        gson = new Gson();
                }
                return gson;
        }

        public static String toJson(Object obj) {
                return gson.toJson(obj);
        }

        public static <T> T toBean(String json, Class<T> clz) {

                return gson.fromJson(json, clz);
        }
        
        public static <T> T toBean(Map<String, Object> map, Class<T> clz) {
                String json = gson.toJson(map);
                return gson.fromJson(json, clz);
        }

        public static <T> Map<String, T> toMap(String json, Class<T> clz) {
                Map<String, JsonObject> map = gson.fromJson(json, new TypeToken<Map<String, JsonObject>>() {
                }.getType());
                Map<String, T> result = new HashMap<String, T>();
                for (String key : map.keySet()) {
                        result.put(key, gson.fromJson(map.get(key), clz));
                }
                return result;
        }

        public static Map<String, Object> toMap(String json) {
                Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String, Object>>() {
                }.getType());
                return map;
        }

        public static <T> List<T> toList(String json, Class<T> clz) {
                JsonArray array = new JsonParser().parse(json).getAsJsonArray();
                List<T> list = new ArrayList<T>();
                for (final JsonElement elem : array) {
                        list.add(gson.fromJson(elem, clz));
                }
                return list;
        }
        
        
}
