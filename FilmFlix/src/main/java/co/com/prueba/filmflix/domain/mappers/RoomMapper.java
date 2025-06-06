package co.com.prueba.filmflix.domain.mappers;

import co.com.prueba.filmflix.domain.dto.room.CreateRoomRequest;
import co.com.prueba.filmflix.domain.dto.room.RoomResponse;
import co.com.prueba.filmflix.domain.dto.room.UpdateRoomRequest;
import co.com.prueba.filmflix.domain.entities.Room;

import java.util.List;

public class RoomMapper {
    public static RoomResponse toRoomResponse(Room room) {
        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .imageUrl(room.getImageUrl())
                .build();
    }

    public static Room toRoom(CreateRoomRequest createRoomRequest, String imageUrl) {
        return Room.builder()
                .name(createRoomRequest.getName())
                .imageUrl(imageUrl)
                .build();
    }

    public static Room toRoom(UpdateRoomRequest updateRoomRequest) {
        return Room.builder()
                .id(updateRoomRequest.getId())
                .name(updateRoomRequest.getName())
                .imageUrl(updateRoomRequest.getImageUrl())
                .enabled(updateRoomRequest.isEnabled())
                .build();
    }

    public static List<RoomResponse> toRoomResponseList(List<Room> rooms) {
        return rooms.stream().map(RoomMapper::toRoomResponse).toList();
    }
}
