package co.com.prueba.filmflix.utils.validations;

public class UserValidator {
    public static final String ID_NOT_NULL = "El id del usuario no puede ser nulo";
    public static final String ID_VALID = "El id del usuario debe ser un entero positivo";

    public static final String NAME_NOT_BLANK = "El nombre del usuario no puede estar vacío";
    public static final String NAME_SIZE = "El nombre del usuario solo puede tener entre 1 y 20 caracteres";

    public static final String LAST_NAME_NOT_BLANK = "El apellido del usuario no puede ser vacío";
    public static final String LAST_NAME_SIZE = "El apellido del usuario solo puede tener entre 1 y 40 caracteres";

    public static final String PHONE_NUMBER_NOT_BLANK = "El número de teléfono del usuario no puede ser vacío";
    public static final String PHONE_NUMBER_SIZE = "El número de teléfono del usuario solo puede tener entre 1 y 20 caracteres";
    public static final String PHONE_NUMBER_PATTERN = "El número de teléfono debe contener mínimo 10 letras";
    public static final String PHONE_NUMBER_REGEX = "^[0-9]{10}$";
    public static final String PHONE_NUMBER_ALREADY_EXISTS = "El usuario con el número de teléfono: %s ya existe";

    public static final String EMAIL_NOT_BLANK = "El email no puede ser vacío";
    public static final String EMAIL_SIZE = "El email debe tener entre 1 y 100 caracteres";
    public static final String EMAIL_PATTERN = "Ingrese un email válido";
    public static final String EMAIL_ALREADY_EXISTS = "El email: %s ya está registrado";


    public static final String PASSWORD_NOT_BLANK = "La contraseña no puede estar vacía";
    public static final String PASSWORD_SIZE = "La contraseña debe tener entre 1 y 50 caracteres";
    public static final String PASSWORD_PATTERN = "La contraseña debe tener mínimo una mayúscula, una minúscula, un carácter especial y un número";
    public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,}$";

    public static final String USER_NOT_FOUND = "El usuario con ID: %s no fue encontrado";
    public static final String USER_NOT_FOUND_BY_EMAIL = "El usuario con email: %s no fue encontrado";

    public static final String ENABLED_NOT_NULL = "El campo enabled no puede ser nulo";

}
