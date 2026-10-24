package dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class SuccessMessageDto {
    private String status;
    private Integer id;
}
