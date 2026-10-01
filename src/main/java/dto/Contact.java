package dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class Contact {
    private String fullName;
    private String phoneNumber;
}
