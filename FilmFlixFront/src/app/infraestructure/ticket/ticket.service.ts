import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { JwtServService } from '../JWT/jwt-serv.service';
import { environment } from '../../../environments/environment.development';
import { TicketResponse } from '../../domain/models/ticket/TicketResponse';
import { catchError, Observable, throwError } from 'rxjs';
import { CreateTicketRequest } from '../../domain/models/ticket/CreateTicketRequest';
import { ParseErrorService } from '../utils/parse-error.service';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

    constructor(private http: HttpClient,
                private jwtService: JwtServService,
                private parseError: ParseErrorService) { }

    private baseUrlTicket       = signal(environment.API_URL + '/ticket');
    private urlGetPostAndById   = signal(this.baseUrlTicket() + '/admin');
    private urlGetTicketUserId  = signal(this.baseUrlTicket() + '/admin/user');
    private urlGetTicketFilmId  = signal(this.baseUrlTicket() + '/admin/film');
    private buyTicket           = signal(this.baseUrlTicket() + '/buy');

    getByIdTicketId(_id: number): Observable<TicketResponse> | null {
      try {

        const headers = this.jwtService.getTokenInHeaders();
        if (!headers) return throwError(() => new Error('Token not found'));

        return this.http.get<TicketResponse>(this.urlGetPostAndById() + `/${ _id }`, { headers: headers }).pipe(
              catchError(error => {
                return throwError(() => this.parseError.parseErrorFromDB(error));
              }));
      } catch (error) {

        console.log( "Error in getById Ticket -> " + error )
        return null;
      }
    }

    getByIdTicketIdFilm(_id: number): Observable<TicketResponse> | null {
      try {

        const headers = this.jwtService.getTokenInHeaders();
        if (!headers) return throwError(() => new Error('Token not found'));

        return this.http.get<TicketResponse>(this.urlGetTicketFilmId() + `/${ _id }`, { headers: headers }).pipe(
                catchError(error => {
                  return throwError(() => this.parseError.parseErrorFromDB(error));
                }));
      } catch (error) {

        console.log( "Error in getByIdFilm - Ticket -> " + error )
        return null;
      }
    }

    getByIdTicketIdUser(_id: number): Observable<TicketResponse> | null {
      try {

        const headers = this.jwtService.getTokenInHeaders();
        if (!headers) return throwError(() => new Error('Token not found'));

        return this.http.get<TicketResponse>(this.urlGetTicketUserId() + `/${ _id }`, { headers: headers }).pipe(
                catchError(error => {
                  return throwError(() => this.parseError.parseErrorFromDB(error));
                }));
      } catch (error) {

        console.log( "Error in getByIdUser - Ticket -> " + error )
        return null;
      }
    }

    createTicket( ticketRequest: CreateTicketRequest): Observable<TicketResponse> | null {
      try {

        const headers = this.jwtService.getTokenInHeaders();
        if (!headers) return throwError(() => new Error('Token not found'));

        return this.http.post<TicketResponse>(this.buyTicket(), ticketRequest,{ headers: headers }).pipe(
            catchError(error => {
              return throwError(() => this.parseError.parseErrorFromDB(error));
            }));
      } catch(error) {

        console.log( "Error in post Ticket -> " + error )
        return null;
      }
    }

    getAllTickets(): Observable<TicketResponse[]> | any{
      try {

        const headers = this.jwtService.getTokenInHeaders();
        if (!headers) return throwError(() => new Error('Token not found'));

        return this.http.get<TicketResponse[]>(this.urlGetPostAndById(), { headers: headers }).pipe(
                catchError(error => {
                  return throwError(() => this.parseError.parseErrorFromDB(error));
                }));
      } catch (error) {
        console.log( "Error in getAll Tickets -> " + error )
        return null
      }
    }
}
