package co.com.prueba.filmflix.application.service.implementation;

import co.com.prueba.filmflix.application.repository.FilmRepository;
import co.com.prueba.filmflix.application.repository.FilmStaffRepository;
import co.com.prueba.filmflix.application.service.IFilmService;
import co.com.prueba.filmflix.application.service.IFirebaseService;
import co.com.prueba.filmflix.domain.dto.film.CreateFilmRequest;
import co.com.prueba.filmflix.domain.dto.film.FilmResponse;
import co.com.prueba.filmflix.domain.dto.film.UpdateFilmRequest;
import co.com.prueba.filmflix.domain.entities.Film;
import co.com.prueba.filmflix.domain.entities.FilmCast;
import co.com.prueba.filmflix.domain.entities.FilmStaff;
import co.com.prueba.filmflix.domain.mappers.FilmMapper;
import co.com.prueba.filmflix.domain.mappers.FilmStaffMapper;
import co.com.prueba.filmflix.utils.enums.FilmGenre;
import co.com.prueba.filmflix.utils.exceptions.NotFoundException;
import co.com.prueba.filmflix.utils.validations.FilmStaffValidator;
import co.com.prueba.filmflix.utils.validations.FilmValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
@RequiredArgsConstructor
@Service
public class FilmServiceImpl implements IFilmService {
    private final FilmRepository filmRepository;
    private final FilmStaffRepository filmStaffRepository;
    private final IFirebaseService firebaseService;

    @Override
    @Transactional(readOnly = true)
    public List<FilmResponse> findAll() {
        return FilmMapper.toFilmResponseList(filmRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilmResponse> findAllEnabled() {
        return FilmMapper.toFilmResponseList(filmRepository.findAllByEnabledTrue());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FilmResponse> findByTitle(String title) {
        List<Film> filmsByTitle = filmRepository.findByTitleContainingIgnoreCase(title);

        if(filmsByTitle.isEmpty()) throw new NotFoundException(String.format(FilmValidator.FILM_NOT_FOUND_BY_TITLE, title));
        return FilmMapper.toFilmResponseList(filmsByTitle);
    }

    @Override
    @Transactional(readOnly = true)
    public FilmResponse findById(Long id) {
        if(!filmRepository.existsById(id)) throw new NotFoundException(String.format(FilmValidator.FILM_NOT_FOUND, id));

        return FilmMapper.toFilmResponse(filmRepository.getReferenceById(id));
    }

    @Override
    @Transactional
    public FilmResponse save(String title, String description, String duration, FilmGenre filmGenre, String trailerUrl, String filmStaff, MultipartFile poster) throws IOException {

        URL posterURL = firebaseService.uploadFile(poster, "posters");

        CreateFilmRequest request = CreateFilmRequest.builder()
                .title(title)
                .description(description)
                .duration(duration)
                .filmGenre(filmGenre)
                .trailerUrl(trailerUrl)
                .build();

        Film film = FilmMapper.toFilm(request, posterURL.toString());

        List<FilmCast> filmCastList = stringToStaffList(filmStaff, film);

        film.setFilmCastList(filmCastList);

        FilmResponse filmResponse = FilmMapper.toFilmResponse(filmRepository.save(film));
        return filmResponse;
    }

    @Override
    @Transactional
    public FilmResponse update(UpdateFilmRequest updateFilmRequest) {
        if(!filmRepository.existsById(updateFilmRequest.getId())) throw new NotFoundException(String.format(FilmValidator.FILM_NOT_FOUND, updateFilmRequest.getId()));

        Film oldFilm = filmRepository.getReferenceById(updateFilmRequest.getId());

        Film updFilm = FilmMapper.toFilm(updateFilmRequest, oldFilm.getPosterUrl(), updateFilmRequest.isEnabled());
        updFilm.setFilmCastList(oldFilm.getFilmCastList());

        return FilmMapper.toFilmResponse(filmRepository.save(updFilm));
    }

    @Override
    public void delete(Long id) {
        if(!filmRepository.existsById(id)) throw new NotFoundException(String.format(FilmValidator.FILM_NOT_FOUND, id));
        Film oldFilm = filmRepository.getReferenceById(id);

        oldFilm.setEnabled(!oldFilm.isEnabled());

        filmRepository.save(oldFilm);
    }

    private List<FilmCast> stringToStaffList(String filmStaff, Film film){
        List<Long> castIdList = stringToIntegerIdList(filmStaff);
        log.info(castIdList.toString());

        Map<Long, FilmStaff> staffMap = filmStaffRepository.findAllById(castIdList).stream()
                .collect(Collectors.toMap(FilmStaff::getId, staff -> staff));

        return castIdList.stream()
                .map(staffId -> {
                    FilmStaff staff = staffMap.get(staffId);
                    if (staff == null) {
                        throw new NotFoundException(String.format(FilmStaffValidator.NOT_FOUND_BY_ID, staffId));
                    }

                    return FilmCast.builder()
                            .film(film)
                            .filmStaff(staff)
                            .build();
                })
                .toList();
    }

    private List<Long> stringToIntegerIdList(String numberList){
        return Arrays.stream(numberList.split(" ")).map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
    }
}
