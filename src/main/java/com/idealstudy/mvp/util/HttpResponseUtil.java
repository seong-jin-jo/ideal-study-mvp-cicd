package com.idealstudy.mvp.util;

import com.idealstudy.mvp.enums.HttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class HttpResponseUtil {

    public static ResponseEntity<String> responseString(HttpResponse res) {
        return new ResponseEntity<String>(res.getMsg(), res.getHttpStatus());
    }
}
