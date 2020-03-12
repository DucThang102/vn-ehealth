package vn.ehealth.emr.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vn.ehealth.emr.model.EmrPerson;
import vn.ehealth.emr.service.UserService;
import vn.ehealth.emr.validate.Validator;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping("/create_user")
    public ResponseEntity<?> createUser(@RequestParam("emr_co_so_kham_benh_id") String emrCoSoKhamBenhId, @RequestParam("emrPerson") EmrPerson emrPerson, @RequestParam("role_ids") List<String> roleIds) {
        try {
            if (!emrCoSoKhamBenhId.isEmpty() && !Validator.isCheckTyeObjectId(emrCoSoKhamBenhId)) {
                var result = Map.of(
                        "success", false,
                        "error", "emr_co_so_kham_benh_id định dạng không đúng objectID"
                );
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            if (!Validator.isValidRequier(emrPerson.email)) {
                var result = Map.of(
                        "success", false,
                        "error", "emrPerson.email là bắt buộc"
                );
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            if (!Validator.isValidRequier(emrPerson.tendaydu)) {
                var result = Map.of(
                        "success", false,
                        "error", "emrPerson.tendaydu là bắt buộc"
                );
                return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }

            var user = userService.createUser(new ObjectId(emrCoSoKhamBenhId), emrPerson, roleIds);

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

}
