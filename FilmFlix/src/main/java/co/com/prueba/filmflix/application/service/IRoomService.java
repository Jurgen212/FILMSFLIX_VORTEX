package co.com.prueba.filmflix.application.service;

import co.com.prueba.filmflix.domain.dto.room.CreateRoomRequest;
import co.com.prueba.filmflix.domain.dto.room.RoomResponse;
import co.com.prueba.filmflix.domain.dto.room.UpdateRoomRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IRoomService {
    List<RoomResponse> findAll();
    List<RoomResponse> findAllEnabled();
    RoomResponse findById(Long id);
    RoomResponse save(String name, MultipartFile image) throws IOException;
    RoomResponse update(UpdateRoomRequest updateRoomRequest);
    void delete(Long id);
}
