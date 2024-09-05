package com.indi.wisexy.pathguide.controller;
import com.google.common.base.Joiner;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class WebController {

    @RequestMapping("/guide")
    public String guide(@RequestParam Map<String, String> requestMapping) {
        log.info("requestMapping: {}", viewParam(requestMapping));
        // temp
        return "";
    }

    private static String viewParam(Map<String, String> map) {
        List<String> params = new ArrayList<>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            params.add(entry.getKey() +  " -> " + entry.getValue());
        }
        return Joiner.on("\n").join(params);
    }

}
