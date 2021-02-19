package com.muse.gatewaynacosprovider;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NacosController {
    @GetMapping("/sayHello")
    public String sayHello() {
        System.out.println("[gateway-nacos-provider]: sayHello");
        return "[gateway-nacos-provider]: sayHello";
    }
}
