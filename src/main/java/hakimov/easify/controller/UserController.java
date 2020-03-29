package hakimov.easify.controller;

import hakimov.easify.exception.ValidationException;
import hakimov.easify.form.validator.UserCredentialsRegisterValidator;
import hakimov.easify.service.UserService;
import hakimov.easify.util.BindingResultUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import hakimov.easify.domain.User;
import hakimov.easify.form.RegisterUserCredentials;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1")
public class UserController extends ApiController {
    private final UserService userService;
    private final UserCredentialsRegisterValidator userCredentialsRegisterValidator;

    public UserController(UserService userService, UserCredentialsRegisterValidator userCredentialsRegisterValidator) {
        this.userService = userService;
        this.userCredentialsRegisterValidator = userCredentialsRegisterValidator;
    }

    @GetMapping("users/authorized")
    public User findAuthorized(User user) {
        return user;
    }

    @GetMapping("users")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @InitBinder("registerUserCredentials")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCredentialsRegisterValidator);
    }


    @PostMapping("users")
    public void register(@RequestBody @Valid RegisterUserCredentials registerUserCredentials, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }

        userService.register(registerUserCredentials);
    }
}
