package com.motaharinia.async.presentation.employee;

/**
 * User: https://github.com/motaharinia<br>
 * Date: 2020-09-02<br>
 * Time: 12:02:24<br>
 * Description:<br>
 *     کلاس مدل حساب بانکی کارمند
 */
public class EmployeeAccountModel {
    /**
     * نام بانک
     */
    private String bankName;
    /**
     * شماره حساب بانکی
     */
    private String bankAccountNo;

    //getter-setter:
    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }
}
