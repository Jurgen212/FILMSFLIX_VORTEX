import { FilmGenre } from "../../enums/FilmGenre";

export interface UpdateFilmRequest {
    id: number;
    title: string;
    description: string;
    duration: string;
    filmGenre: FilmGenre;
    trailerUrl: string;
    enabled: boolean;
}
