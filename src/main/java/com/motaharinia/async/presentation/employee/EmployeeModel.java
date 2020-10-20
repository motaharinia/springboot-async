package com.motaharinia.async.presentation.employee;

import java.io.Serializable;

/**
 * User: https://github.com/motaharinia<br>
 * Date: 2020-07-22<br>
 * Time: 13:41:19<br>
 * Description:<br>
 * کلاس مدل کارمند
 */
public class EmployeeModel implements Serializable {

	private static final long serialVersionUID = -1773599508061743940L;
	/**
	 * نام
	 */
	public String firstName;
	/**
	 * نام خانوادگی
	 */
	public String lastName;

	@Override
	public String toString() {
		return "EmployeeModel{" +
				"firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				'}';
	}

	//getter-setter:
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
