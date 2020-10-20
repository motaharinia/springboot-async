package com.motaharinia.async.business;


import com.motaharinia.async.presentation.employee.EmployeeAccountModel;
import com.motaharinia.async.presentation.employee.EmployeeContactModel;
import com.motaharinia.async.presentation.employee.EmployeeModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;


/**
 * User: https://github.com/motaharinia<br>
 * Date: 2020-09-02<br>
 * Time: 11:52:12<br>
 * Description:<br>
 * کلاس سرویس کارمند
 */
@Service
public class EmployeeService {


    /**
     * شمارنده نزولی برای متد تست
     */
    @Autowired
    private CountDownLatch countDownLatch;


    /**
     * این متد مدل اطلاعات کارمند را خروجی میدهد
     *
     * @return خروجی: مدل اطلاعات کارمند
     * @throws InterruptedException
     */
    @Async("asyncExecutor1")
    public CompletableFuture<EmployeeModel> getEmployee() throws InterruptedException {
        //پر کردن اطلاعات کارمند در مدل
        EmployeeModel employeeModel = new EmployeeModel();
        employeeModel.setFirstName("Mostafa");
        employeeModel.setLastName("Motaharinia");

        //نمایش نام نخ فعلی
        System.out.println("getEmployee Thread.currentThread().getName():" + Thread.currentThread().getName());

        //ایجاد وقفه 2 ثانیه ای برای متد تست
        Thread.sleep(2000L);

        //متد countDown تعداد را برای متد تست کاهش می دهد
        countDownLatch.countDown();

        return CompletableFuture.completedFuture(employeeModel);
    }

    /**
     * این متد مدل اطلاعات تماس کارمند را خروجی میدهد
     *
     * @return خروجی: مدل اطلاعات تماس کارمند
     * @throws InterruptedException
     */
    @Async("asyncExecutor1")
    public CompletableFuture<EmployeeContactModel> getEmployeeContact() throws InterruptedException {
        //پر کردن اطلاعات تماس کارمند در مدل
        EmployeeContactModel employeeContactModel = new EmployeeContactModel();
        employeeContactModel.setAddress("address1");
        employeeContactModel.setPhone("+989124376251");

        //نمایش نام نخ فعلی
        System.out.println("getEmployeeContact Thread.currentThread().getName():" + Thread.currentThread().getName());

        //ایجاد وقفه 2 ثانیه ای برای متد تست
        Thread.sleep(2000L);

        //متد countDown تعداد را برای متد تست کاهش می دهد
        countDownLatch.countDown();

        return CompletableFuture.completedFuture(employeeContactModel);
    }

    /**
     * این متد مدل اطلاعات حساب بانکی کارمند را خروجی میدهد
     *
     * @param withException آیا متد تست نیاز به بروز خطا دارد؟
     * @param message       پیام خطای مورد نیاز متد تست
     * @return خروجی: مدل اطلاعات حساب بانکی کارمند
     * @throws InterruptedException
     */
    @Async("asyncExecutor1")
    public CompletableFuture<EmployeeAccountModel> getEmployeeAccount(Boolean withException, String message) throws InterruptedException {
        //پر کردن اطلاعات حساب بانکی کارمند در مدل
        EmployeeAccountModel employeeAccountModel = new EmployeeAccountModel();
        employeeAccountModel.setBankAccountNo("54545-1985458");
        employeeAccountModel.setBankName("BMWBank");

        //نمایش نام نخ فعلی
        System.out.println("getEmployeeAccount Thread.currentThread().getName():" + Thread.currentThread().getName());

        //ایجاد وقفه 2 ثانیه ای برای متد تست
        Thread.sleep(2000L);

        //در صورت دلخواه متد تست یک خطا خروجی میدهیم
        if (withException) {
            throw new InterruptedException("message : " + message);
        }

        //متد countDown تعداد را برای متد تست کاهش می دهد
        countDownLatch.countDown();

        return CompletableFuture.completedFuture(employeeAccountModel);
    }

}
