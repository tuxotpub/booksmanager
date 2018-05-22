package org.tuxotpub.booksmanager.services.parchments;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tuxotpub.booksmanager.api.v1.dtos.MagazineDTO;
import org.tuxotpub.booksmanager.api.v1.mapper.MagazineMapper;
import org.tuxotpub.booksmanager.entities.Magazine;
import org.tuxotpub.booksmanager.repositories.parchments.MagazineRepository;
import org.tuxotpub.booksmanager.services.ParchmentServiceImpl;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.tuxotpub.booksmanager.controllers.v1.MagazineController.FINDBYID_PATH;

/**
 * Created by tuxsamo.
 */
@Service
public class MagazineServiceImpl extends ParchmentServiceImpl<MagazineDTO, Magazine> implements MagazineService {

    private final MagazineMapper magazineMapper;
    private final MagazineRepository magazineRepository;

    public MagazineServiceImpl(MagazineMapper magazineMapper, MagazineRepository magazineRepository) {
        super( magazineRepository );
        this.magazineMapper = magazineMapper;
        this.magazineRepository = magazineRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<MagazineDTO> getByReleaseDate(LocalDate releaseDate) {
        return magazineRepository.findMagazinesByReleaseDate(releaseDate).stream()
        .map(magazineMapper::getMagazineDTO).collect(Collectors.toList());
    }

    @Override
    protected Magazine mergeParchment(Magazine source, Magazine destination) {
        if ( source.getReleaseDate() != null ) destination.setReleaseDate( source.getReleaseDate() );
        return super.mergeParchment(source, destination);
    }

    @Override
    public String getPath() {
        return FINDBYID_PATH;
    }

    @Override
    protected MagazineDTO getDTO(Magazine magazine) {
        return magazineMapper.getMagazineDTO(magazine);
    }

    @Override
    protected Magazine getEntity(MagazineDTO magazineDTO) {
        return magazineMapper.getMagazine( magazineDTO );
    }
}
