import { FilmResponse } from '../film/FilmResponse';
import { RoomResponse } from '../room/RoomResponse';

export interface FunctionResponse {
    id: number;
    room: RoomResponse;
    film: FilmResponse;
    time: string;
    enabled: boolean;
    functionPrice: number;
}
