package com.nan.bungshin.global.util;

import java.util.HashMap;
import java.util.Map;

public class ApiResponseFactory {
    public static Map<String, Boolean> isAlreadyExistFlag(){
        return new HashMap<>(){{
            put("exist", true);
        }};
    }
    public static Map<String, Boolean> isNotAlreadyExistFlag(){
        return new HashMap<>(){{
            put("exist", false);
        }};
    }
    public static Map<String, Boolean> successFlag() {
        return new HashMap<>() {{
            put("success", true);
        }};
    }
}
