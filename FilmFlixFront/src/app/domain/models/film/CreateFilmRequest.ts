import { FilmGenre } from "../../enums/FilmGenre";

export interface CreateFilmRequest {
    title: string;
    description: string;
    duration: string;
    filmGenre: FilmGenre;
    trailerUrl: string;
    filmStaff: string;
}
