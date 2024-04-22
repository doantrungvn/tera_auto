package org.terasoluna.qp.app.accountprofile;


import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PassEqualsValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return (PasswordForm.class).isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
    	PasswordForm account = (PasswordForm) target;
        String pass = account.getNewPassword();
        String passConfirm = account.getConfirmNewPassword();
        if (pass == null || passConfirm == null) {
            // must be checked by @NotEmpty
            return;
        }
        if (!pass.equals(passConfirm)) {
        	errors.reject("err.accountprofile.0009");
        }
    }
}
