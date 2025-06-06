import { FunctionTime } from "../../enums/FunctionTIme";

export interface CreateFunctionRequest {
    time: FunctionTime;
    functionPrice: string;
    roomId: number;
    filmId: number;
}
