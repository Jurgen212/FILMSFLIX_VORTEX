import { FilmStaffResponse } from "../filmStaff/FilmStaffResponse";

export interface FilmResponse {
    id: number;
    title: string;
    description: string;
    duration: string;
    filmGenre: string;
    posterUrl: string;
    trailerUrl: string;
    enabled: boolean;
    filmStaff: FilmStaffResponse[];
}
