package co.com.prueba.filmflix.utils.validations;

public class FilmStaffValidator {
    public static final String ID_NOT_NULL = "El cast id no puede ser nulo";
    public static final String ID_VALID = "El cast id debe ser un entero positivo";

    public static final String NOT_FOUND_BY_ID = "No se encuentra el staff con id: %d";

    public static final String NAME_NOT_BLANK = "El nombre del staff no puede estar vacío";
    public static final String NAME_SIZE = "El nombre del staff debe tener entre 1 y 100 caracteres";

    public static final String STAFF_ROL_NOT_BLANK = "El rol del staff no puede estar vacío";

    public static final String ENABLED_NOT_NULL = "El campo enabled no puede ser nulo";
}
