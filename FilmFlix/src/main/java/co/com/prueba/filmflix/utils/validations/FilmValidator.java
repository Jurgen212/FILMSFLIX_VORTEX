package co.com.prueba.filmflix.utils.validations;

public class FilmValidator {
    public static final String ID_NOT_NULL = "El film id no puede ser nulo";
    public static final String ID_VALID = "El film id debe ser un entero positivo";

    public static final String TITLE_NOT_BLANK = "El título del film no puede ser vacio";
    public static final String TITLE_SIZE = "El título del film solo puede tener entre 1 y 100 caracteres";

    public static final String DESCRIPTION_NOT_BLANK = "La descripción del film no puede venir vacía";
    public static final String DESCRIPTION_SIZE = "La descripción del film debe tener entre 1 y 300 caracteres";

    public static final String DURATION_NOT_BLANK = "La duración del film no puede estar vacía";

    public static final String FILM_GENRE_NOT_BLANK = "El género del film no puede estar vacío";

    public static final String TRAILER_URL_NOT_BLANK = "La URL del tráiler no puede ser vacía";

    public static final String FILM_NOT_FOUND = "El film con ID: %d no fue encontrado";
    public static final String FILM_NOT_FOUND_BY_TITLE = "El film con título: %s no fue encontrado";

    public static final String FILMSTAFF_IDS_REQUIRED = "La lista de staff no puede estar vacía";

    public static final String POSTER_NOT_NULL = "La imagen del poster no puede ser nula";

    public static final String ENABLED_NOT_NULL = "El campo enabled no puede ser nulo";
}
