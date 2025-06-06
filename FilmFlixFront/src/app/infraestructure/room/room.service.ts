import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { JwtServService } from '../JWT/jwt-serv.service';
import { environment } from '../../../environments/environment.development';
import { RoomResponse } from '../../domain/models/room/RoomResponse';
import { catchError, Observable, throwError } from 'rxjs';
import { CreateRoomRequest } from '../../domain/models/room/CreateRoomRequest';
import { UpdateRoomRequest } from '../../domain/models/room/UpdateRoomRequest';
import { ParseErrorService } from '../utils/parse-error.service';

@Injectable({
  providedIn: 'root'
})
export class RoomService {

    constructor(private http: HttpClient,
                private jwtService: JwtServService,
                private parseError: ParseErrorService) { }

    private baseUrlRoom = signal(environment.API_URL + '/room');
    private urlGetPostDeleteRoom = signal(this.baseUrlRoom() + '/admin');
    private urlEnabledRoom = signal(this.baseUrlRoom() + '/enabled');

    getByIdRoom(_id: string): Observable<RoomResponse> | null {
      try {

        const headers = this.jwtService.getTokenInHeaders();
        if (!headers) return throwError(() => new Error('Token not found'));

        return this.http.get<RoomResponse>(this.urlEnabledRoom() + `/${ _id }`, { headers: headers }).pipe(
              catchError(error => {
                return throwError(() => this.parseError.parseErrorFromDB(error));
              }));
      } catch (error) {

        console.log( "Error in getByIdEnabled room -> " + error )
        return null;
      }
    }

    deleteRoom(_id: number): Observable<any> | null {
      try {

        const headers = this.jwtService.getTokenInHeaders();
        if (!headers) return throwError(() => new Error('Token not found'));

        return this.http.delete<any>(this.urlGetPostDeleteRoom() + `/${ _id }`, { headers: headers }).pipe(
                catchError(error => {
                  return throwError(() => this.parseError.parseErrorFromDB(error));
                }));
      } catch(error) {

        console.log( "Error in delete Film -> " + error )
        return null;
      }
    }

    updateRoom(room: UpdateRoomRequest): Observable<RoomResponse> | null {
      try {

        const headers = this.jwtService.getTokenInHeaders();
        if (!headers) return throwError(() => new Error('Token not found'));

        return this.http.put<RoomResponse>( this.urlGetPostDeleteRoom(), room, { headers}).pipe(
                catchError(error => {
                  return throwError(() => this.parseError.parseErrorFromDB(error));
                }));
      } catch(error) {

          console.log( "Error in update Room -> " + error )
          return null;
          }
      }

    createRoom(room: CreateRoomRequest, event: Event ): Observable<RoomResponse> | null {
        try {

          let imageFile = null;
          const input = event.target as HTMLInputElement;

          if (input.files && input.files.length > 0){
            imageFile = input.files[0];
          }

          const headers = this.jwtService.getTokenInHeaders();
          if (!headers) {
            return throwError(() => new Error('Token not found'));
          }

          if(!imageFile) {
            return throwError(() => new Error('Image file is required'));
          }

          const formData = new FormData();
          formData.append('name', room.name);
          formData.append('image', imageFile, imageFile.name);

          return this.http.post<RoomResponse>(this.urlGetPostDeleteRoom(), formData, { headers }).pipe(
            catchError(error => {
              return throwError(() => this.parseError.parseErrorFromDB(error));
            }));
        } catch (error) {

          console.error('Error in post room ->', error);
          return null;
      }
    }

    getAll(): Observable<RoomResponse[]> | any{
      try {
          const headers = this.jwtService.getTokenInHeaders();
          if (!headers) return throwError(() => new Error('Token not found'));

          return this.http.get<RoomResponse[]>(this.urlGetPostDeleteRoom(), { headers: headers }).pipe(
              catchError(error => {
                return throwError(() => this.parseError.parseErrorFromDB(error));
              }));
      } catch (error) {

        console.log( "Error in getAll rooms -> " + error )
        return null
      }
    }

    getAllEnabled(): Observable<RoomResponse[]> | any{
      try {

        const headers = this.jwtService.getTokenInHeaders();
        if (!headers) return throwError(() => new Error('Token not found'));

        return this.http.get<RoomResponse[]>(this.baseUrlRoom(), { headers: headers }).pipe(
                catchError(error => {
                  return throwError(() => this.parseError.parseErrorFromDB(error));
                }));
      } catch (error) {

        console.log( "Error in getAllEnabled room -> " + error )
        return null
      }
    }
}

