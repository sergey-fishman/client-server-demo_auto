package dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class ContactsMessageDto {
    private String status;
    private Contact[] contacts;
}
