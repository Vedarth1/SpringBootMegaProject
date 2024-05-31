package org.megaproject.chatboot.Models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Files")
public class Files {
    @Id
    private String id;
    private String name;
    private String url;
}
