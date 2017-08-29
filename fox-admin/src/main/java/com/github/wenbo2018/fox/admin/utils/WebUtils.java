package com.github.wenbo2018.fox.admin.utils;

import com.github.wenbo2018.jconf.web.constants.TokenState;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wenbo.shen on 2017/5/27.
 */
public class WebUtils {

    private static Logger logger = LoggerFactory.getLogger(WebUtils.class);
    private static final byte[] SECRET = "3d990d2276917dfac04467df11fff26d".getBytes();
    private static final JWSHeader header = new JWSHeader(JWSAlgorithm.HS256, JOSEObjectType.JWT, null, null, null, null, null, null, null, null, null, null, null);

    public static String getStringFromCookie(String str, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies!=null) {
            for (Cookie cookie : cookies) {
                if (str.equals(cookie.getName()))
                    return cookie.getValue();
            }
        }
        return null;
    }

    public static void addStringToCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        if (maxAge > 0) {
            cookie.setMaxAge(maxAge);
        }
        response.addCookie(cookie);
    }

    public static String produceToken(Map<String, Object> payload) {
        String token = null;
        JWSObject jwsObject = new JWSObject(header, new Payload(new JSONObject(payload)));
        try {
            jwsObject.sign(new MACSigner(SECRET));
            token = jwsObject.serialize();
        } catch (JOSEException e) {
            logger.error("生产token失败:{}", e);
        }
        return token;
    }

    public static Map<String, Object> validToken(String token) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            JWSObject jwsObject = JWSObject.parse(token);
            Payload payload = jwsObject.getPayload();
            JWSVerifier verifier = new MACVerifier(SECRET);
            if (jwsObject.verify(verifier)) {
                JSONObject jsonOBj = payload.toJSONObject();
                // token校验成功（此时没有校验是否过期）
                resultMap.put("state", TokenState.VALID.toString());
                // 若payload包含ext字段，则校验是否过期
                if (jsonOBj.containsKey("ext")) {
                    long extTime = Long.valueOf(jsonOBj.get("ext").toString());
                    long curTime = new Date().getTime();
                    // 过期了
                    if (curTime > extTime) {
                        resultMap.clear();
                        resultMap.put("state", TokenState.EXPIRED.toString());
                    }
                }
                resultMap.put("data", jsonOBj);
            } else {
                // 校验失败
                resultMap.put("state", TokenState.INVALID.toString());
            }
        } catch (Exception e) {
            // token格式不合法导致的异常
            resultMap.clear();
            resultMap.put("state", TokenState.INVALID.toString());
            logger.error("token 格式不合法:{}",e);
        }
        return resultMap;
    }
}
