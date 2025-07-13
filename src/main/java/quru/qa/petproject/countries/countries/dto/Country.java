package quru.qa.petproject.countries.countries.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import quru.qa.petproject.countries.countries.dto.group.OnCreate;
import quru.qa.petproject.countries.countries.dto.group.OnUpdate;

import java.util.UUID;

public record Country(
    @NotNull(groups = OnUpdate.class, message = "ID is required for update")
    @Null(groups = OnCreate.class, message = "ID must be null")
    UUID id,
    @NotBlank(message = "Name must not be blank")
    String name,
    @NotBlank(message = "Code must not be blank")
    String code
) {
}
