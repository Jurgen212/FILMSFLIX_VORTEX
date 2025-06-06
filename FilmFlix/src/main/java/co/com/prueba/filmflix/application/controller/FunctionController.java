package co.com.prueba.filmflix.application.controller;

import co.com.prueba.filmflix.application.service.IFunctionService;
import co.com.prueba.filmflix.domain.dto.function.CreateFunctionRequest;
import co.com.prueba.filmflix.domain.dto.function.FunctionResponse;
import co.com.prueba.filmflix.domain.dto.function.UpdateFunctionRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/functions")
@RestController
@Tag(name = "Handling cinema functions", description = "Endpoints to manage the functions.")
@SecurityRequirement(name = "bearer-key")
public class FunctionController {
    private final IFunctionService functionService;

    @Operation(summary = "Obtain enabled and non-enabled functions", description = "Allows you to view absolutely all the functions on the platform.")
    @GetMapping("/admin")
    public ResponseEntity<List<FunctionResponse>> findAll() {
        return new ResponseEntity<>(functionService.findAll(), HttpStatus.OK);
    }

    @Operation(summary = "Obtain enabled  functions", description = "Allows you to view absolutely all the enabled functions on the platform.")
    @GetMapping()
    public ResponseEntity<List<FunctionResponse>> findAllEnabled() {
        return new ResponseEntity<>(functionService.findAllEnabled(), HttpStatus.OK);
    }

    @Operation(summary = "Get a function with the ID", description = "Get a single function with its identifier.")
    @GetMapping("/enabled/{id}")
    public ResponseEntity<FunctionResponse> findById(@PathVariable Long id) {
        return new ResponseEntity<>(functionService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Get a function with the film ID", description = "Obtain functions related to a movie.")
    @GetMapping("/enabled/film/{id}")
    public ResponseEntity<List<FunctionResponse>> findByFilmId(@PathVariable Long id) {
        return new ResponseEntity<>(functionService.findAllByFilmId(id), HttpStatus.OK);
    }

    @Operation(summary = "Create a function", description = "Create a function for a movie and a room.")
    @PostMapping(path = "/admin")
    public ResponseEntity<FunctionResponse> save(
            @RequestBody @Valid CreateFunctionRequest createFunctionRequest
    ){
        return new ResponseEntity<>(functionService.addFunctionToFilm(createFunctionRequest), HttpStatus.OK);
    }

    @Operation(summary = "Update a function", description = "Allows to update some fields of the functions.")
    @PutMapping(path = "/admin")
    public ResponseEntity<FunctionResponse> update(
            @RequestBody @Valid UpdateFunctionRequest updateFunctionRequest
    ) throws IOException {
        return new ResponseEntity<>(functionService.updateFunction(updateFunctionRequest), HttpStatus.OK);
    }

    @Operation(summary = "Disable function", description = "Disable a function with its ID.")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        functionService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
