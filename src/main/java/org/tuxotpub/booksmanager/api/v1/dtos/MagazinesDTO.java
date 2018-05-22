package org.tuxotpub.booksmanager.api.v1.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by tuxsamo.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MagazinesDTO {
    private List<MagazineDTO> magazineDTOS;
}
