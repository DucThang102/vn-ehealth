package vn.ehealth.emr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.ehealth.emr.model.Role;
import vn.ehealth.emr.model.UserRequestDTO;
import vn.ehealth.emr.service.UserService;
import vn.ehealth.emr.validate.Validator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping("/create_user")
    public ResponseEntity<?> createUser(@RequestBody UserRequestDTO userRequestDTO) {
        try {
            if (!Validator.isValidRequier(userRequestDTO.getEmrPerson().email)) {
                var result = Map.of(
                        "success", false,
                        "error", "emrPerson.email là bắt buộc"
                );
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            if (!Validator.isValidRequier(userRequestDTO.getEmrPerson().tendaydu)) {
                var result = Map.of(
                        "success", false,
                        "error", "emrPerson.tendaydu là bắt buộc"
                );
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }

            var user = userService.createUser(userRequestDTO.getEmrPerson(), userRequestDTO.getRoleIds());

            var result = Map.of(
                    "success", true,
                    "user", user

            );

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            var error = Optional.ofNullable(e.getMessage()).orElse("Unknown error");
            var result = Map.of(
                    "success", false,
                    "error", error
            );
            logger.error("Error save user: ", e);
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getRolesByUsername")
    @ResponseBody
    public List<Role> getRolesByUsername(@RequestParam("username") String username) {
        return userService.getRolesByUsername(username);
    }

}
