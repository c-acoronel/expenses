package com.expenses.filter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Andres.
 */

public class UserDataHolder {

    public static final String USER_ID_KEY = "user-id";
    public static final ThreadLocal<Map<String,Object>> tokenThreadLocal = new ThreadLocal<Map<String,Object>>();

    private static void setValue(String key, Object value) {
        if (tokenThreadLocal.get()==null){
            tokenThreadLocal.set(new HashMap<String, Object>());
        }
        tokenThreadLocal.get().put(key, value);
    }

    public static void cleanup() {
        tokenThreadLocal.remove();
    }

    private static Object getValue(String key) {
        if (null != tokenThreadLocal.get() && tokenThreadLocal.get().containsKey(key)) {
            return tokenThreadLocal.get().get(key);
        } else {
            //throw new MissingParameterException(key+" not found");
        }
        return null;
    }

    private static Boolean hasValue(String key){
        if (tokenThreadLocal.get()!=null && tokenThreadLocal.get().containsKey(key)) {
            return true;
        }
        return false;
    }

    public static String getUserId(){
        return getValue(USER_ID_KEY).toString();
    }

    public static void setUserId(String userId){
        setValue(USER_ID_KEY, userId);
    }

    public static Boolean hasUserData(){
        return hasValue(USER_ID_KEY);
    }
    public static Boolean hasUserIdData(){
        return hasValue(USER_ID_KEY);
    }
}
