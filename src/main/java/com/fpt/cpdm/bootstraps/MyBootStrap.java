package com.fpt.cpdm.bootstraps;

import com.fpt.cpdm.exceptions.user.UserNotFoundException;
import com.fpt.cpdm.exceptions.user.UserNotValidException;
import com.fpt.cpdm.models.User;
import com.fpt.cpdm.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Component
public class MyBootStrap implements ApplicationListener<ApplicationReadyEvent> {

    private final UserService userService;
    private final Validator validator;

    @Autowired
    public MyBootStrap(UserService userService, @Qualifier("defaultValidator") Validator validator) {
        this.userService = userService;
        this.validator = validator;
    }

    @Override
    @SuppressWarnings("Duplicates")
    public void onApplicationEvent(ApplicationReadyEvent event) {

        System.out.println("ApplicationReadyEvent");

        User user = new User();
        user.setId(null);
        user.setDisplayName("Hoàng Vinh Quang");
        user.setEmail("quanghvse61073@fpt.edu.vn");
        user.setPassword("12345678");
        user.setRole("STAFF");
        user.setPhone("0707518178");
        user.setAddress("HCMC");
        user.setBirthDay(LocalDate.of(1993, Month.SEPTEMBER, 30));
        System.out.println(user);

        BindingResult result = new BeanPropertyBindingResult(user, "user");
        validator.validate(user, result);

        if (result.hasErrors()) {
            List<FieldError> fieldsErrors = result.getFieldErrors();
            StringBuilder stringBuilder = new StringBuilder();
            for (FieldError fieldError : fieldsErrors) {
                stringBuilder
                        .append(fieldError.getField())
                        .append(": ")
                        .append(fieldError.getDefaultMessage())
                        .append(" \n ");
            }
            throw new UserNotValidException(stringBuilder.toString(), null, true, false);
        }

        User savedUser = userService.save(user);
        System.out.println(savedUser);

        UserDetails currentUser = userService.loadUserByUsername("quanghvse61073@fpt.edu.vn");
        System.out.println(currentUser);
    }
}
