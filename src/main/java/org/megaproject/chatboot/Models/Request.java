package org.megaproject.chatboot.Models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Request {
    private String email;
    private String password;

}
