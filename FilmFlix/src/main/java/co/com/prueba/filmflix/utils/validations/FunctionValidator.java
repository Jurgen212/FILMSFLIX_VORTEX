package co.com.prueba.filmflix.utils.validations;

public class FunctionValidator {
    public static final String ID_NOT_NULL = "El id de la función no puede ser nulo";
    public static final String ID_VALID = "El id de la función debe ser un entero positivo";

    public static final String ROOM_ID_NOT_NULL_FUNCTION = "El id de la sala en function no puede ser nulo";
    public static final String ROOM_NOT_FOUND_FUNCTION = "El id de la sala: %d en function no fue encontrado";


    public static final String FILM_NOT_FOUND_FUNCTION = "El film con ID: %d no fue encontrado";
    public static final String FILM_ID_NOT_NULL_FUNCTION = "El film id en function no puede ser nulo";

    public static final String TIME_NOT_BLANK = "El horario de la función no puede estar vacio";

    public static final String FUNCTION_PRICE_NOT_BLANK = "El precio de la función no puede estar vacio";
    public static final String FUNCTION_PRICE_INVALID = "El formato del precio no es válido";

    public static final String FUNCTION_NOT_FOUND = "La función con id: %d no fue encontrada";
    
    public static final String ENABLED_NOT_NULL = "El campo enabled no puede ser nulo";

    public static final String ROOM_IN_USE = "La sala seleccionada está reservada en esa hora";
}
