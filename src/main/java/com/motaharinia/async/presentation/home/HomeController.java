package com.motaharinia.async.presentation.home;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * User: https://github.com/motaharinia<br>
 * Date: 2020-08-24<br>
 * Time: 14:04:26<br>
 * Description:<br>
 *     کلاس کنترلر صفحه خانه
 */
@RestController
public class HomeController {


    @Value("${spring.application.name}")
    private String springApplicationName;

    @RequestMapping("/")
    public String getUrl() {
        return springApplicationName;
    }

}
