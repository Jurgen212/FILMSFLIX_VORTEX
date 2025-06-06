import { PaymentMethod } from "../../enums/PaymentMethod";

export interface CreateTicketRequest {
    paymentMethod: PaymentMethod;
    quantity: number;
    userId: number;
    functionId: number;
    filmId: number;
}
