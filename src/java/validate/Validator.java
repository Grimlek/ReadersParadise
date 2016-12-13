/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validate;

import java.time.Year;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

public class Validator {

    public static Map<String, Boolean> validateForm(String name,
            String email,
            String phone,
            String address,
            String year,
            String month,
            String ccNumber) {

        Map<String, Boolean> errorMap = new HashMap<>();
        boolean errorFlag = false;

        if (name == null
                || name.equals("")
                || name.length() < 5) {
            errorFlag = true;
            errorMap.put("nameError", true);
        }
        if (!email.equals("")
                && !email.contains("@")) {
            errorFlag = true;
            errorMap.put("emailError", true);
        }
        if (phone == null
                || phone.equals("")
                || phone.length() < 9) {
            errorFlag = true;
            errorMap.put("phoneError", true);
        }
        if (address == null
                || address.equals("")
                || address.length() < 5) {
            errorFlag = true;
            errorMap.put("addressError", true);
        }

        if (year != null
                && month != null
                && year.matches("^\\d+$")
                && month.matches("^\\d+$")) {

            YearMonth expirationDate = YearMonth.of(
                    Integer.valueOf(year),
                    Integer.valueOf(month));

            if (expirationDate.getMonthValue() < YearMonth.now().getMonthValue()
                    && expirationDate.getYear() == Year.now().getValue()) {
                errorFlag = true;
                errorMap.put("expirationeDateError", true);
            }
        } else {
            errorFlag = true;
            errorMap.put("expirationeDateError", true);
        }

        if (ccNumber == null
                || ccNumber.equals("")
                || ccNumber.length() < 16) {
            errorFlag = true;
            errorMap.put("ccNumError", true);
        }
        errorMap.put("errorFlag", errorFlag);
        return errorMap;
    }
}
