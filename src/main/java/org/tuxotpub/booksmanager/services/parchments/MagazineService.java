package org.tuxotpub.booksmanager.services.parchments;

import org.tuxotpub.booksmanager.api.v1.dtos.MagazineDTO;
import org.tuxotpub.booksmanager.services.ParchmentService;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by tuxsamo.
 */
public interface MagazineService extends ParchmentService<MagazineDTO>{

    List<MagazineDTO> getByReleaseDate(LocalDate releaseDate);
}


