import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { JwtServService } from '../JWT/jwt-serv.service';
import { environment } from '../../../environments/environment.development';
import { catchError, Observable, throwError } from 'rxjs';
import { UserResponse } from '../../domain/models/user/UserResponse';
import { UpdateUserRequest } from '../../domain/models/user/UpdateUserRequest';
import { ParseErrorService } from '../utils/parse-error.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient,
              private jwtService: JwtServService,
              private parseError: ParseErrorService) { }

  private baseUrlUser = signal(environment.API_URL + '/user');

  private urlGetPostDeleteUsers = signal(this.baseUrlUser() + '/admin');
  private urlUpdateRolUser = signal(this.baseUrlUser() + '/admin/updaterol');
  private urlEnabledUsers = signal(this.baseUrlUser() + '/admin/enabled');

  getByIdUser(_id: string): Observable<UserResponse> | null {
    try {

      const headers = this.jwtService.getTokenInHeaders();
      if (!headers) return throwError(() => new Error('Token not found'));

      return this.http.get<UserResponse>(this.urlGetPostDeleteUsers() + `/${ _id }`, { headers: headers }).pipe(
            catchError(error => {
              return throwError(() => this.parseError.parseErrorFromDB(error));
            }));

    } catch (error) {

      console.log( "Error in getById User -> " + error )
      return null;

    }
  }

  deleteUser(_id: string): Observable<any> | null {
    try {

      const headers = this.jwtService.getTokenInHeaders();
      if (!headers) return throwError(() => new Error('Token not found'));
      return this.http.delete<any>(this.urlGetPostDeleteUsers() + `/${ _id }`, { headers: headers }).pipe(
              catchError(error => {
                return throwError(() => this.parseError.parseErrorFromDB(error));
              }));

    } catch(error) {

      console.log( "Error in delete User -> " + error )
      return null;

    }
  }

  updateUser(_id: string, user: UpdateUserRequest): Observable<UserResponse> | null {
    try {

      const headers = this.jwtService.getTokenInHeaders();
      if (!headers) return throwError(() => new Error('Token not found'));

      return this.http.put<UserResponse>( this.urlGetPostDeleteUsers() + `/${ _id }`, user, { headers}).pipe(
              catchError(error => {
                return throwError(() => this.parseError.parseErrorFromDB(error));
              }));

  } catch(error) {

      console.log( "Error in update User -> " + error )
      return null;
  }
  }

  changeRolUser( id: number): Observable<UserResponse> | null {
    try {

      const headers = this.jwtService.getTokenInHeaders();
      if (!headers) return throwError(() => new Error('Token not found'));
      return this.http.post<UserResponse>(this.urlUpdateRolUser() + "/" + id, { headers: headers }).pipe(
              catchError(error => {
                return throwError(() => this.parseError.parseErrorFromDB(error));
              }));

    } catch(error) {

      console.log( "Error in post User -> " + error )
      return null;

    }
  }

  getAll(): Observable<UserResponse[]> | any{
    try {

      const headers = this.jwtService.getTokenInHeaders();
      if (!headers) return throwError(() => new Error('Token not found'));

      return this.http.get<UserResponse[]>(this.urlGetPostDeleteUsers(), { headers: headers }).pipe(
              catchError(error => {
                return throwError(() => this.parseError.parseErrorFromDB(error));
              }));

    } catch (error) {

      console.log( "Error in getAll User -> " + error )
      return null
    }
  }

  getAllEnabled(): Observable<UserResponse[]> | any{
    try {

      const headers = this.jwtService.getTokenInHeaders();
      if (!headers) return throwError(() => new Error('Token not found'));

      return this.http.get<UserResponse[]>(this.urlEnabledUsers(), { headers: headers }).pipe(
              catchError(error => {
                return throwError(() => this.parseError.parseErrorFromDB(error));
              }));

    } catch (error) {

      console.log( "Error in getAll User -> " + error )
      return null
    }
  }
}
