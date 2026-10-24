package dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Contact {
    private String id;
    private String full_name;
    private String phone_number;
}
