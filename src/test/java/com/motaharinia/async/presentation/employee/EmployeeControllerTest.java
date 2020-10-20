package com.motaharinia.async.presentation.employee;


import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ActiveProfiles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * User: https://github.com/motaharinia<br>
 * Date: 2020-07-22<br>
 * Time: 13:41:19<br>
 * Description:<br>
 * کلاس تست کنترلر کارمند
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    @Qualifier("asyncExecutor1")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private CountDownLatch countDownLatch;


    @Test()
    public void testAsyncBlock() throws ParseException {
        try {

            //درخواست وب
            String uri = "http://localhost:" + port + "/v1/employee/async/block";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity entity = new HttpEntity(headers);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");
            //زمان شروع
            Date firstDate = sdf.parse(sdf.format(new Date()));
            ResponseEntity<Boolean> response = this.restTemplate.exchange(uri, HttpMethod.GET, entity, Boolean.class);
            //زمان پایان
            Date secondDate = sdf.parse(sdf.format(new Date()));

            //اختلاف زمان شروع و زمان پایان
            long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
            System.out.println("diffInMillies:::" + diffInMillies);

            //در این تست اختلاف زمان شروع و زمان پایان متدهای آسینک را محاسبه میکند
            //چون در متدهای آسینک برای همه شان 2000میلی ثانیه sleep را ست کرده ایم بنابراین انتظار داریم که زیر 3000میلی ثانیه جواب را دریافت نماییم
            assertThat(diffInMillies).isLessThan(3000L);

            //نمایش نام نخ فعلی
            System.out.println("testAsyncBlock Thread.currentThread().getName():" + Thread.currentThread().getName());

        } catch (Exception ex) {
            fail(ex.toString());
        }
    }


    @Test
    public void testAsyncThenApply() {
        try {

            //درخواست وب
            String uri = "http://localhost:" + port + "/v1/employee/async/thenApply";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            HttpEntity entity = new HttpEntity(headers);
            ResponseEntity<Boolean> response = this.restTemplate.exchange(uri, HttpMethod.GET, entity, Boolean.class);

            //به دلیل اینکه ما با دستور join , get ترد جاری را بلوکه نکرده ایم ، متد کنترلر زودتر از متدهای Async تمام میشود و ترو ریترن میشود
            //برای اینکه تست کنیم که متدهای Async بصورت موازی اجرا میشوند باید در متد تست یک انتظار ایجاد کنیم
            // که این کار را توسط countDownLatch.await انجام میدهیم

            //متد await تا وقتیکه count==0 شود ترد جاری را بلوک میکند
            //countDownLatch.await();
            //متد await به مدت 3000میلی ثانیه ترد جاری را بلوک میکند
            //چون در متدهای آسینک برای همه شان 2000میلی ثانیه sleep را ست کرده ایم بنابراین انتظار داریم که زیر 3000میلی ثانیه اجرای تردها تمام شوند
            countDownLatch.await(3000, TimeUnit.MILLISECONDS);

            // Main thread has started
            //چک میکند که تعداد تردها صفر است یا نه
            assertThat(countDownLatch.getCount()).isEqualTo(0);
            //چک میکند تعداد تردهای فعال صفر است یا نه
            assertThat(threadPoolTaskExecutor.getActiveCount()).isEqualTo(0);

            //نمایش نام نخ فعلی
            System.out.println("testAsyncThenApply Thread.currentThread().getName():" + Thread.currentThread().getName());

        } catch (Exception ex) {
            fail(ex.toString());
        }
    }


}
