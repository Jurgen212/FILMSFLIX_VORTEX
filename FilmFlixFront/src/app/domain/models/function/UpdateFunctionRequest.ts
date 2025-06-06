import { FunctionTime } from "../../enums/FunctionTIme";

export interface UpdateFunctionRequest {
    id: number;
    time: FunctionTime;
    functionPrice: string;
    roomId: number;
    filmId: number;
    enabled: boolean;
}
