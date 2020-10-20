package com.motaharinia.async.presentation.employee;

/**
 * User: https://github.com/motaharinia<br>
 * Date: 2020-09-02<br>
 * Time: 11:52:12<br>
 * Description:<br>
 *     کلاس مدل اطلاعات تماس کارمند
 */
public class EmployeeContactModel {
    /**
     * نشانی
     */
    private String address;
    /**
     * شماره تلفن ثابت
     */
    private String phone;

    //getter-setter
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}