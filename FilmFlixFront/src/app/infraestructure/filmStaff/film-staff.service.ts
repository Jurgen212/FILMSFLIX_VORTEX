import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { JwtServService } from '../JWT/jwt-serv.service';
import { environment } from '../../../environments/environment.development';
import { catchError, Observable, throwError } from 'rxjs';
import { CreateFilmStaffRequest } from '../../domain/models/filmStaff/CreateFilmStaffRequest';
import { FilmStaffResponse } from '../../domain/models/filmStaff/FilmStaffResponse';
import { ParseErrorService } from '../utils/parse-error.service';

@Injectable({
  providedIn: 'root'
})
export class FilmStaffService {

    constructor(private http: HttpClient,
                private jwtService: JwtServService,
                private parseError: ParseErrorService) { }

    private baseUrlStaff = signal(environment.API_URL + '/staff');
    private urlGetPostDeleteUsers = signal(this.baseUrlStaff() + '/admin');
    private urlStaffById = signal(this.baseUrlStaff() + '/enabled');

    getByIdStaff(_id: number): Observable<FilmStaffResponse> | null {
      try {
        const headers = this.jwtService.getTokenInHeaders();
        if (!headers) return throwError(() => new Error('Token not found'));

        return this.http.get<FilmStaffResponse>(this.urlStaffById() + `/${ _id }`, { headers: headers }).pipe(
              catchError(error => {
                return throwError(() => this.parseError.parseErrorFromDB(error));
              }));
      } catch (error) {

        console.log( "Error in getById Staff -> " + error )
        return null;
      }
    }

    createStaff( filmStaff: CreateFilmStaffRequest): Observable<FilmStaffResponse> | null {
      try {

        const headers = this.jwtService.getTokenInHeaders();
        if (!headers) return throwError(() => new Error('Token not found'));

        return this.http.post<FilmStaffResponse>(this.urlGetPostDeleteUsers(), filmStaff, { headers: headers }).pipe(
              catchError(error => {
                return throwError(() => this.parseError.parseErrorFromDB(error));
              }));
      } catch(error) {
        console.log( "Error in post staff -> " + error )
        return null;
      }
    }

    deleteStaff(_id: number): Observable<any> | null {
      try {

        const headers = this.jwtService.getTokenInHeaders();
        if (!headers) return throwError(() => new Error('Token not found'));

        return this.http.delete<any>(this.urlGetPostDeleteUsers() + `/${ _id }`, { headers: headers }).pipe(
                catchError(error => {
                  return throwError(() => this.parseError.parseErrorFromDB(error));
                }));
      } catch(error) {

        console.log( "Error in delete staff -> " + error )
        return null;
      }
    }

    getAll(): Observable<FilmStaffResponse[]> | any{
      try {
        const headers = this.jwtService.getTokenInHeaders();
        if (!headers) return throwError(() => new Error('Token not found'));
        return this.http.get<FilmStaffResponse[]>(this.urlGetPostDeleteUsers(), { headers: headers }).pipe(
                catchError(error => {
                  return throwError(() => this.parseError.parseErrorFromDB(error));
                }));
      } catch (error) {

        console.log( "Error in getAll Staff -> " + error )
        return null
        }
      }

    getAllEnabled(): Observable<FilmStaffResponse[]> | any{
      try {
        const headers = this.jwtService.getTokenInHeaders();
        if (!headers) return throwError(() => new Error('Token not found'));

        return this.http.get<FilmStaffResponse[]>(this.baseUrlStaff(), { headers: headers }).pipe(
              catchError(error => {
                return throwError(() => this.parseError.parseErrorFromDB(error));
              }));
      } catch (error) {

        console.log( "Error in getAllEnabled staff -> " + error )
        return null
      }
    }
}
