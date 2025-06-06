package co.com.prueba.filmflix.domain.mappers;

import co.com.prueba.filmflix.domain.dto.function.CreateFunctionRequest;
import co.com.prueba.filmflix.domain.dto.function.FunctionResponse;
import co.com.prueba.filmflix.domain.dto.function.UpdateFunctionRequest;
import co.com.prueba.filmflix.domain.entities.*;

import java.util.List;

public class FunctionMapper {

    public static FunctionResponse toFunctionResponse(Function function) {
        return FunctionResponse.builder()
                .id(function.getId())
                .time(function.getTime().toString())
                .functionPrice(function.getFunctionPrice())
                .room(RoomMapper.toRoomResponse(function.getRoom()))
                .film(FilmMapper.toFilmResponse(function.getFilm()))
                .enabled(function.isEnabled())
                .build();
    }

    public static Function toFunction(CreateFunctionRequest createFunctionRequest, Room room, Film film) {
        return Function.builder()
                .time(createFunctionRequest.getTime())
                .functionPrice(Double.parseDouble(createFunctionRequest.getFunctionPrice()))
                .room(room)
                .film(film)
                .build();
    }

    public static Function toFunction(UpdateFunctionRequest updateFunctionRequest, Room room, Film film) {
        return Function.builder()
                .id(updateFunctionRequest.getId())
                .time(updateFunctionRequest.getTime())
                .functionPrice(Double.parseDouble(updateFunctionRequest.getFunctionPrice()))
                .room(room)
                .film(film)
                .enabled(updateFunctionRequest.isEnabled())
                .build();
    }

    public static List<FunctionResponse> toFunctionResponseList(List<Function> function) {
        return function.stream().map(FunctionMapper::toFunctionResponse).toList();
    }
}
