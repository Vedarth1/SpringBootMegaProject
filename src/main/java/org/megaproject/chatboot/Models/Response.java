package org.megaproject.chatboot.Models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Response {

    private String token;
    private String message;
}
