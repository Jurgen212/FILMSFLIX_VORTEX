import { PaymentMethod } from '../../enums/PaymentMethod';
import { FilmResponse } from '../film/FilmResponse';
import { FunctionResponse } from '../function/FunctionResponse';
import { UserResponse } from '../user/UserResponse';

export interface TicketResponse {
    id: number;
    user: UserResponse;
    film: FilmResponse;
    function: FunctionResponse;
    paymentMethod: PaymentMethod;
    quantity: number;
    total: number;
    date: Date;
}
