package co.com.prueba.filmflix.application.service.implementation;

import co.com.prueba.filmflix.application.repository.FilmRepository;
import co.com.prueba.filmflix.application.repository.RoomRepository;
import co.com.prueba.filmflix.application.service.IFirebaseService;
import co.com.prueba.filmflix.application.service.IRoomService;
import co.com.prueba.filmflix.domain.dto.room.CreateRoomRequest;
import co.com.prueba.filmflix.domain.dto.room.RoomResponse;
import co.com.prueba.filmflix.domain.dto.room.UpdateRoomRequest;
import co.com.prueba.filmflix.domain.entities.Room;
import co.com.prueba.filmflix.domain.mappers.RoomMapper;
import co.com.prueba.filmflix.domain.mappers.UserMapper;
import co.com.prueba.filmflix.utils.exceptions.ConflictException;
import co.com.prueba.filmflix.utils.exceptions.NotFoundException;
import co.com.prueba.filmflix.utils.validations.RoomValidator;
import co.com.prueba.filmflix.utils.validations.UserValidator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RoomServiceImpl implements IRoomService {
    private final RoomRepository roomRepository;
    private final IFirebaseService firebaseService;

    @Override
    @Transactional(readOnly = true)
    public List<RoomResponse> findAll() {
        return RoomMapper.toRoomResponseList(roomRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoomResponse> findAllEnabled() {
        return RoomMapper.toRoomResponseList(roomRepository.findAllByEnabledTrue());
    }

    @Override
    @Transactional(readOnly = true)
    public RoomResponse findById(Long id) {
        if(!roomRepository.existsById(id)) throw new NotFoundException(String.format(RoomValidator.ROOM_NOT_FOUND, id));
        return RoomMapper.toRoomResponse(roomRepository.getReferenceById(id));
    }

    @Override
    @Transactional
    public RoomResponse save(String name, MultipartFile image) throws IOException{
        if(roomRepository.existsRoomByName(name)) throw new ConflictException(RoomValidator.NAME_ALREADY_IN_USE);

        URL imageUrl = firebaseService.uploadFile(image, "rooms");

        return RoomMapper.toRoomResponse(roomRepository.save(RoomMapper.toRoom(CreateRoomRequest.builder()
                .name(name)
                .build(), imageUrl.toString())));
    }

    @Override
    @Transactional
    public RoomResponse update(UpdateRoomRequest updateRoomRequest) {
        if(!roomRepository.existsById(updateRoomRequest.getId())) throw new NotFoundException(String.format(RoomValidator.ROOM_NOT_FOUND, updateRoomRequest.getId()));
        if(roomRepository.existsRoomByName(updateRoomRequest.getName())) throw new ConflictException(RoomValidator.NAME_ALREADY_IN_USE);

        return RoomMapper.toRoomResponse(roomRepository.save(RoomMapper.toRoom(updateRoomRequest)));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(!roomRepository.existsById(id)) throw new NotFoundException(String.format(RoomValidator.ROOM_NOT_FOUND, id));

        Room oldRoom = roomRepository.getReferenceById(id);
        oldRoom.setEnabled(false);
        roomRepository.save(oldRoom);
    }
}
