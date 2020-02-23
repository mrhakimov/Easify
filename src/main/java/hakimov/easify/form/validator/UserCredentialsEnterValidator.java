package hakimov.easify.form.validator;

import hakimov.easify.form.UserCredentials;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import hakimov.easify.service.UserService;

@Component
public class UserCredentialsEnterValidator implements Validator {
    private final UserService userService;

    public UserCredentialsEnterValidator(UserService userService) {
        this.userService = userService;
    }

    public boolean supports(Class<?> clazz) {
        return UserCredentials.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        if (!errors.hasErrors()) {
            UserCredentials enterForm = (UserCredentials) target;
            if (userService.findByLoginAndPassword(enterForm.getLogin(), enterForm.getPassword()) == null) {
                errors.reject("password.invalid-login-or-password", "Invalid login or password");
            }
        }
    }
}
