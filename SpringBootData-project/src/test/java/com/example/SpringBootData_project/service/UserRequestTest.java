// package com.example.SpringBootData_project.service;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// import java.util.Set;

// import jakarta.validation.Validation;
// import jakarta.validation.Validator;
// import jakarta.validation.ValidatorFactory;

// import org.codehaus.stax2.validation.Validatable;
// import org.junit.jupiter.api.BeforeAll;
// import jakarta.validation.ConstraintViolation;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;
// import org.mockito.junit.jupiter.MockitoExtension;

// import com.ctc.wstx.shaded.msv.org_isorelax.jaxp.ValidatingSAXParserFactory;
// import com.example.SpringBootData_project.dto.request.UserRequest;
// import com.example.SpringBootData_project.model.User;
// import com.example.SpringBootData_project.repository.UserRepository;

// public class UserRequestTest {

//     private static Validator validator;

//     @BeforeAll
//     public static void setUp() {
//         ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//         validator = factory.getValidator();
//     }

//     @Test
//     public void testUserRequestValidation() {
//         UserRequest userRequest = new UserRequest();
//         userRequest.setFirstName("John");
//         userRequest.setLastName("Doe");
//         userRequest.setEmail("not-an-email"); 

//         Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
//         assertEquals(1, violations.size());

//         boolean emailViolationFound = violations.stream()
//                 .anyMatch(v -> v.getPropertyPath().toString().equals("email"));
//         assertEquals(true, emailViolationFound);

// }
// }