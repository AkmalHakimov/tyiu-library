package library.libraryback.payload.requests;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReqBook {
    private String name;
    private String author;
    private Integer categoryId;
    private String description;
    private String publisher;
    private Timestamp bookDate;
}
