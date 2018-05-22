package org.tuxotpub.booksmanager.api.v1.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.tuxotpub.booksmanager.api.v1.dtos.MagazineDTO;
import org.tuxotpub.booksmanager.entities.Magazine;

/**
 * Created by tuxsamo.
 */

@Mapper(componentModel = "spring")
public interface MagazineMapper{

    MagazineMapper INSTANCE = Mappers.getMapper(MagazineMapper.class);

    MagazineDTO getMagazineDTO(Magazine magazine);

    Magazine getMagazine(MagazineDTO magazineDTO);
}
