package com.boltech.service_insurance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignUpUserRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    private String email;

    @NotBlank(message = "First Name is required")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    private String lastName;

    /**
     * (?=.*[A-Z])        # At least one uppercase letter (A-Z)
     * (?=.*[a-z])        # At least one lowercase letter (a-z)
     * (?=.*\\d)          # At least one digit (0-9)
     * (?=.*[@$!&])       # At least one of these special characters: @, $, !, &
     * [A-Za-z\\d@$!&]    # Allow only letters, digits, and the specified special characters
     * {8,64}             # Length between 8 to 64 characters
     */
    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 64, message = "Password must be between 8 and 64 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!&])[A-Za-z\\d@$!&]{8,64}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one of these special characters: @, $, !, &"
    )
    private String password;
}
