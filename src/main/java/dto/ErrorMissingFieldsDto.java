package dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ErrorMissingFieldsDto {
    private String error;
    private String[] missing_fields;
}
