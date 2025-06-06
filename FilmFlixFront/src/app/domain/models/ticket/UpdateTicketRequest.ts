
export interface UpdateTicketRequest {
    id: number;
    paymentMethod: string;
    quantity: number;
    userId: number;
    functionId: number;
    filmId: number;
    enabled: boolean;
}
