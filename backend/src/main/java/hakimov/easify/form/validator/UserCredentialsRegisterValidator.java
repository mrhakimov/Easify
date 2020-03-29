package hakimov.easify.form.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import hakimov.easify.form.RegisterUserCredentials;
import hakimov.easify.service.UserService;

@Component
public class UserCredentialsRegisterValidator implements Validator {
    private final UserService userService;

    public UserCredentialsRegisterValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean supports(Class<?> clazz) {
        return RegisterUserCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            RegisterUserCredentials enterForm = (RegisterUserCredentials) target;
            if (userService.findByLogin(enterForm.getLogin()) != null) {
                errors.reject("password.invalid-login-or-password", "This login is already taken");
            }
        }
    }
}
