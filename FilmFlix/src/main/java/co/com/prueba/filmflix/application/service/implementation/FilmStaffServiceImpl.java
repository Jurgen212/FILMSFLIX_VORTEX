package co.com.prueba.filmflix.application.service.implementation;

import co.com.prueba.filmflix.application.repository.FilmStaffRepository;
import co.com.prueba.filmflix.application.service.IFilmStaffService;
import co.com.prueba.filmflix.domain.dto.filmStaff.CreateFilmStaffRequest;
import co.com.prueba.filmflix.domain.dto.filmStaff.FilmStaffResponse;
import co.com.prueba.filmflix.domain.dto.room.CreateRoomRequest;
import co.com.prueba.filmflix.domain.dto.room.RoomResponse;
import co.com.prueba.filmflix.domain.dto.room.UpdateRoomRequest;
import co.com.prueba.filmflix.domain.entities.FilmStaff;
import co.com.prueba.filmflix.domain.entities.Room;
import co.com.prueba.filmflix.domain.mappers.FilmStaffMapper;
import co.com.prueba.filmflix.domain.mappers.RoomMapper;
import co.com.prueba.filmflix.utils.exceptions.ConflictException;
import co.com.prueba.filmflix.utils.exceptions.NotFoundException;
import co.com.prueba.filmflix.utils.validations.FilmStaffValidator;
import co.com.prueba.filmflix.utils.validations.RoomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FilmStaffServiceImpl implements IFilmStaffService {
    private final FilmStaffRepository filmStaffRepository;

    @Override
    @Transactional(readOnly = true)
    public List<FilmStaffResponse> findAll() {
        return FilmStaffMapper.toFilmStaffResponseList(filmStaffRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilmStaffResponse> findAllEnabled() {
        return FilmStaffMapper.toFilmStaffResponseList(filmStaffRepository.findAllByEnabledTrue());
    }

    @Override
    @Transactional(readOnly = true)
    public FilmStaffResponse findById(Long id) {
        if(!filmStaffRepository.existsById(id)) throw new NotFoundException(String.format(FilmStaffValidator.NOT_FOUND_BY_ID, id));
        return FilmStaffMapper.toFilmStaffResponse(filmStaffRepository.getReferenceById(id));
    }

    @Override
    @Transactional
    public FilmStaffResponse save(CreateFilmStaffRequest createFilmStaffRequest) {
        return FilmStaffMapper.toFilmStaffResponse(filmStaffRepository.save(FilmStaffMapper.toFilmStaff(createFilmStaffRequest)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!filmStaffRepository.existsById(id)) throw new NotFoundException(String.format(FilmStaffValidator.NOT_FOUND_BY_ID, id));

        FilmStaff oldFilmStaff = filmStaffRepository.getReferenceById(id);
        oldFilmStaff.setEnabled(false);
        filmStaffRepository.save(oldFilmStaff);
    }
}
