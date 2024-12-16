package com.nhnacademy.twojopingfront.common.favicon;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FaviconController {
    @RequestMapping(value = "favicon.ico", method = RequestMethod.GET)
    @ResponseBody
    void returnNoFavicon() {
        // 빈 응답 또는 커스텀 로직
    }
}