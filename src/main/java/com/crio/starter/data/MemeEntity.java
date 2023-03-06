package com.crio.starter.data;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="memes")
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MemeEntity {

    @Id
    private String id;
    @NotNull
    @Indexed(unique = true)
    private String name;
    @NotNull
    // @Indexed(unique = true)
    private String url;
    @NotNull
    @Indexed(unique = true)
    private String caption;
    
}
