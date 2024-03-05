package library.libraryback.payload.requests;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReqLogin {
    @NotBlank(message = "qani password")
    private String userName;
    @NotBlank(message = "qani phone")
    private String password;
}
