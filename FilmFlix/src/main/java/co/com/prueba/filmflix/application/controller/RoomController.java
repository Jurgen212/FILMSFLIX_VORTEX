package co.com.prueba.filmflix.application.controller;

import co.com.prueba.filmflix.application.service.IRoomService;
import co.com.prueba.filmflix.domain.dto.film.FilmResponse;
import co.com.prueba.filmflix.domain.dto.room.CreateRoomRequest;
import co.com.prueba.filmflix.domain.dto.room.RoomResponse;
import co.com.prueba.filmflix.domain.dto.room.UpdateRoomRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/room")
@RestController
@Tag(name = "Operate movie rooms", description = "Endpoints to manage the movies rooms.")
@SecurityRequirement(name = "bearer-key")
public class RoomController {
    private final IRoomService roomService;

    @Operation(summary = "Obtain enabled and non-enabled rooms", description = "Allows you to view absolutely all the rooms on the platform.")
    @GetMapping("/admin")
    public ResponseEntity<List<RoomResponse>> findAll() {
        return new ResponseEntity<>(roomService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtain enabled rooms", description = "Allows you to view absolutely all the enabled rooms on the platform.")
    @GetMapping()
    public ResponseEntity<List<RoomResponse>> findAllEnabled() {
        return new ResponseEntity<>(roomService.findAllEnabled(), HttpStatus.OK);
    }

    @Operation(summary = "Get a room with the ID", description = "Get a single room with its identifier.")
    @GetMapping("/enabled/{id}")
    public ResponseEntity<RoomResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(roomService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Create a room", description = "Create a room with the image.")
    @PostMapping(path = "/admin", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RoomResponse> create(
            @RequestPart("name") String name,
            @RequestPart("image") MultipartFile image
    ) throws IOException {
        return new ResponseEntity<>(roomService.save(name, image), HttpStatus.OK);
    }

    @Operation(summary = "Update a room", description = "Allows to update some fields of the rooms.")
    @PutMapping(path = "/admin")
    public ResponseEntity<RoomResponse> update(
            @RequestBody @Valid UpdateRoomRequest updateRoomRequest
    ) throws IOException {
        return new ResponseEntity<>(roomService.update(updateRoomRequest), HttpStatus.OK);
    }

    @Operation(summary = "Disable a room", description = "Disable a room with its ID.")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}