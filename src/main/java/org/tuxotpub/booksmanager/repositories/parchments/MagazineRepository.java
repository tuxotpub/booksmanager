package org.tuxotpub.booksmanager.repositories.parchments;

import org.tuxotpub.booksmanager.entities.Magazine;
import org.tuxotpub.booksmanager.repositories.EntityRepository;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by tuxsamo.
 */

public interface MagazineRepository extends ParchmentRepository<Magazine>, EntityRepository {

    List<Magazine> findMagazinesByReleaseDateBetweenOrderByReleaseDate(LocalDate from, LocalDate to);

    List<Magazine> findMagazinesByReleaseDate(LocalDate date);
}
