package co.com.prueba.filmflix.utils.validations;

public class RoomValidator {

    public static final String ID_NOT_NULL = "El id de la sala no puede ser nulo";
    public static final String ID_VALID = "El id de la sala debe ser un entero positivo";

    public static final String NAME_NOT_BLANK = "El nombre de la sala no puede estar vacío";
    public static final String NAME_VALID = "El nombre de la sala debe tener entre 1 y 20 caracteres";
    public static final String NAME_ALREADY_IN_USE = "El nombre de la sala ya esta siendo utilizado";

    public static final String IMAGE_URL_NOT_BLANK = "La URL de la imagen para la sala no puede estar vacía";

    public static final String ROOM_NOT_FOUND = "La sala con id: %d no fue encontrada";

    public static final String ENABLED_NOT_NULL = "El campo enabled no puede ser nulo";
}
