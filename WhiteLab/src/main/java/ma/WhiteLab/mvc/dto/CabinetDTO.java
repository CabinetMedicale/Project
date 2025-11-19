package ma.WhiteLab.mvc.dto;

import lombok.*;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class CabinetDTO {
    private String nom;
    private String email;
    private String telephone;
    private String dateCreationFormatee;
}

