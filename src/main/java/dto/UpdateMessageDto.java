package dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class UpdateMessageDto {
    private String status;
    private Integer id;
    private String full_name;
    private String phone_number;
}
