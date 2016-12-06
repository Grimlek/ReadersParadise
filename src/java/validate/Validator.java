/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validate;

import java.time.Year;
import java.time.YearMonth;
import javax.servlet.http.HttpServletRequest;

public class Validator {

    public static boolean validateForm(String name,
            String email,
            String phone,
            String address,
            YearMonth expirationDate,
            String ccNumber,
            HttpServletRequest request) {

        boolean errorFlag = false;
        boolean nameError;
        boolean emailError;
        boolean phoneError;
        boolean addressError;
        boolean expirationDateError;
        boolean ccNumberError;

        if (name == null
                || name.equals("")
                || name.length() < 5) {
            errorFlag = true;
            nameError = true;
            request.setAttribute("nameError", nameError);
        }
        if (!email.equals("")
                && !email.contains("@")) {
            errorFlag = true;
            emailError = true;
            request.setAttribute("emailError", emailError);
        }
        if (phone == null
                || phone.equals("")
                || phone.length() < 9) {
            errorFlag = true;
            phoneError = true;
            request.setAttribute("phoneError", phoneError);
        }
        if (address == null
                || address.equals("")
                || address.length() < 5) {
            errorFlag = true;
            addressError = true;
            request.setAttribute("addressError", addressError);
        }
        if (expirationDate == null
                || (expirationDate.getMonthValue() < YearMonth.now().getMonthValue()
                && expirationDate.getYear() == Year.now().getValue())) {
            errorFlag = true;
            expirationDateError = true;
            request.setAttribute("expirationeDateError", expirationDateError);
        }
        if (ccNumber == null
                || ccNumber.equals("")
                || ccNumber.length() < 16) {
            errorFlag = true;
            ccNumberError = true;
            request.setAttribute("ccNumError", ccNumberError);
        }
        request.setAttribute("errorFlag", errorFlag);
        return errorFlag;
    }
}
