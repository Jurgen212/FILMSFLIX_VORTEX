import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { JwtServService } from '../JWT/jwt-serv.service';
import { environment } from '../../../environments/environment.development';
import { CreateUserRequest } from '../../domain/models/user/CreateUserRequest';
import { AuthenticationResponse } from '../../domain/models/authentication/AuthenticationResponse';
import { catchError, Observable, throwError } from 'rxjs';
import { AuthenticationRequest } from '../../domain/models/authentication/AuthenticationRequest';
import { ParseErrorService } from '../utils/parse-error.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

    constructor(private http: HttpClient, private parseError: ParseErrorService) { }

    private baseUrlAuth = signal(environment.API_URL + '/auth');
    private urlLogin = signal(this.baseUrlAuth() + '/login');
    private urlRegister = signal(this.baseUrlAuth() + '/register');

    login(user: AuthenticationRequest): Observable<AuthenticationResponse> | any {
      return this.http.post<AuthenticationResponse>(this.urlLogin(), user).pipe(
        catchError(error => {
          return throwError(() => this.parseError.parseErrorFromDB(error));
        }))
    }

    register(user: CreateUserRequest): Observable<AuthenticationResponse> | any {
      return this.http.post<AuthenticationResponse>(this.urlRegister(), user).pipe(
        catchError(error => {
          console.log(error)
          return throwError(() => this.parseError.parseErrorFromDB(error));
        }))
    }
}
