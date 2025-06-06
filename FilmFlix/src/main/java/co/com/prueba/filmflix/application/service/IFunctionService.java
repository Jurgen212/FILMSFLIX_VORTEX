package co.com.prueba.filmflix.application.service;

import co.com.prueba.filmflix.domain.dto.function.CreateFunctionRequest;
import co.com.prueba.filmflix.domain.dto.function.FunctionResponse;
import co.com.prueba.filmflix.domain.dto.function.UpdateFunctionRequest;
import co.com.prueba.filmflix.utils.exceptions.ConflictException;
import co.com.prueba.filmflix.utils.exceptions.NotFoundException;

import java.util.List;

public interface IFunctionService {
    List<FunctionResponse> findAll();
    List<FunctionResponse> findAllEnabled();
    FunctionResponse findById(Long id) throws NotFoundException;
    List<FunctionResponse> findAllByFilmId(Long id) throws NotFoundException;
    FunctionResponse addFunctionToFilm(CreateFunctionRequest createFunctionRequest) throws NotFoundException, ConflictException;
    FunctionResponse updateFunction(UpdateFunctionRequest updateFunctionRequest);
    void delete(Long id);
}
