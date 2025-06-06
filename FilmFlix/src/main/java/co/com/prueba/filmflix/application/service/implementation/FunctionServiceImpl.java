package co.com.prueba.filmflix.application.service.implementation;

import co.com.prueba.filmflix.application.repository.FilmRepository;
import co.com.prueba.filmflix.application.repository.FunctionRepository;
import co.com.prueba.filmflix.application.repository.RoomRepository;
import co.com.prueba.filmflix.application.service.IFunctionService;
import co.com.prueba.filmflix.domain.dto.function.CreateFunctionRequest;
import co.com.prueba.filmflix.domain.dto.function.FunctionResponse;
import co.com.prueba.filmflix.domain.dto.function.UpdateFunctionRequest;
import co.com.prueba.filmflix.domain.entities.Film;
import co.com.prueba.filmflix.domain.entities.FilmStaff;
import co.com.prueba.filmflix.domain.entities.Function;
import co.com.prueba.filmflix.domain.entities.Room;
import co.com.prueba.filmflix.domain.mappers.FunctionMapper;
import co.com.prueba.filmflix.utils.exceptions.ConflictException;
import co.com.prueba.filmflix.utils.exceptions.NotFoundException;
import co.com.prueba.filmflix.utils.validations.FilmStaffValidator;
import co.com.prueba.filmflix.utils.validations.FilmValidator;
import co.com.prueba.filmflix.utils.validations.FunctionValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FunctionServiceImpl implements IFunctionService {
    private final FunctionRepository functionRepository;
    private final FilmRepository filmRepository;
    private final RoomRepository roomRepository;


    @Override
    @Transactional(readOnly = true)
    public List<FunctionResponse> findAll() {
        return FunctionMapper.toFunctionResponseList(functionRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FunctionResponse> findAllEnabled() {
        return FunctionMapper.toFunctionResponseList(functionRepository.findAllByEnabledTrue());
    }

    @Override
    @Transactional(readOnly = true)
    public FunctionResponse findById(Long id) throws NotFoundException {
        if(!functionRepository.existsById(id)) throw new NotFoundException(String.format(FunctionValidator.FUNCTION_NOT_FOUND, id));

        return FunctionMapper.toFunctionResponse(functionRepository.getReferenceById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<FunctionResponse> findAllByFilmId(Long id) throws NotFoundException {
        if(!filmRepository.existsById(id)) throw new NotFoundException(String.format(FilmValidator.FILM_NOT_FOUND, id));

        return FunctionMapper.toFunctionResponseList(functionRepository.findAllByEnabledTrueAndFilmId(id));
    }

    @Override
    @Transactional()
    public FunctionResponse addFunctionToFilm(CreateFunctionRequest createFunctionRequest) throws NotFoundException, ConflictException {
        if(!filmRepository.existsById(createFunctionRequest.getFilmId())) throw new NotFoundException(String.format(FunctionValidator.FILM_NOT_FOUND_FUNCTION, createFunctionRequest.getFilmId()));
        if(!roomRepository.existsById(createFunctionRequest.getRoomId())) throw new NotFoundException(String.format(FunctionValidator.ROOM_NOT_FOUND_FUNCTION, createFunctionRequest.getRoomId()));
        if(functionRepository.existsByRoom_IdAndTimeAndEnabled(createFunctionRequest.getRoomId(), createFunctionRequest.getTime(), true)) throw new ConflictException(FunctionValidator.ROOM_IN_USE);

        Film film = filmRepository.getReferenceById(createFunctionRequest.getFilmId());
        Room room = roomRepository.getReferenceById(createFunctionRequest.getRoomId());

        return FunctionMapper.toFunctionResponse(functionRepository.save(FunctionMapper.toFunction(createFunctionRequest, room, film)));
    }

    @Override
    @Transactional()
    public FunctionResponse updateFunction(UpdateFunctionRequest updateFunctionRequest) {
        if(!filmRepository.existsById(updateFunctionRequest.getFilmId())) throw new NotFoundException(String.format(FunctionValidator.FILM_NOT_FOUND_FUNCTION, updateFunctionRequest.getFilmId()));
        if(!roomRepository.existsById(updateFunctionRequest.getRoomId())) throw new NotFoundException(String.format(FunctionValidator.ROOM_NOT_FOUND_FUNCTION, updateFunctionRequest.getRoomId()));
        if(functionRepository.existsByRoom_IdAndTimeAndEnabled(updateFunctionRequest.getRoomId(), updateFunctionRequest.getTime(), true)) throw new ConflictException(FunctionValidator.ROOM_IN_USE);

        Film film = filmRepository.getReferenceById(updateFunctionRequest.getFilmId());
        Room room = roomRepository.getReferenceById(updateFunctionRequest.getRoomId());

        return FunctionMapper.toFunctionResponse(functionRepository.save(FunctionMapper.toFunction(updateFunctionRequest, room, film)));
    }

    @Override
    @Transactional()
    public void delete(Long id) {
        if(!functionRepository.existsById(id)) throw new NotFoundException(String.format(FunctionValidator.FUNCTION_NOT_FOUND, id));

        Function oldFunction = functionRepository.getReferenceById(id);
        oldFunction.setEnabled(false);
        functionRepository.save(oldFunction);
    }
}
