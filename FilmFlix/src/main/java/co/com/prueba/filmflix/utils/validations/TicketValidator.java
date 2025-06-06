package co.com.prueba.filmflix.utils.validations;

public class TicketValidator {
    public static final String ID_NOT_NULL = "El ticket id no puede ser nulo";
    public static final String ID_VALID = "El ticket id debe ser un entero positivo";

    public static final String QUANTITY_NOT_BLANK = "La cantidad de tickets no puede estar vacía";
    public static final String QUANTITY_VALID = "La cantidad de tickets debe ser mayor que cero";
    public static final String NOT_FOUND_BY_ID = "No se encontró ticket con id: %d";

    public static final String PAYMENT_METHOD_NOT_BLANK = "El método de pago no puede estar vacio";

    public static final String USER_ID_NOT_NULL_TICKET = "El id del usuario no puede ser nulo en el ticket";
    public static final String USER_ID_VALID_TICKET = "El id del usuario debe ser un entero positivo en el ticket";
    public static final String TICKET_USER_NOT_FOUND_FUNCTION = "El usuario con ID: %d no fue encontrado";

    public static final String FUNCTION_ID_NOT_NULL_TICKET = "El id de la función no puede ser nulo";
    public static final String FUNCTION_ID_VALID_TICKET = "El id de la función debe ser un entero positivo";
    public static final String FUNCTION_NOT_FOUND_TICKET = "La función con ID: %d no fue encontrado";

    public static final String FILM_ID_NOT_NULL_TICKET = "El id del film no puede ser nulo";
    public static final String FILM_ID_VALID_TICKET = "El id del film debe ser un entero positivo";
    public static final String FILM_NOT_FOUND_TICKET = "El film con ID: %d no fue encontrado";

    public static final String ENABLED_NOT_NULL = "El campo enabled no puede ser nulo";
}
