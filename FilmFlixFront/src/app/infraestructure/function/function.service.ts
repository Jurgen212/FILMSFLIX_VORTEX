import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { JwtServService } from '../JWT/jwt-serv.service';
import { environment } from '../../../environments/environment.development';
import { catchError, Observable, throwError } from 'rxjs';
import { FunctionResponse } from '../../domain/models/function/FunctionResponse';
import { UpdateFunctionRequest } from '../../domain/models/function/UpdateFunctionRequest';
import { CreateFunctionRequest } from '../../domain/models/function/CreateFunctionRequest';
import { ParseErrorService } from '../utils/parse-error.service';

@Injectable({
  providedIn: 'root'
})
export class FunctionService {

    constructor(private http: HttpClient,
                private jwtService: JwtServService,
                private parseError: ParseErrorService) { }

    private baseUrlFunction = signal(environment.API_URL + '/functions');
    private urlGetPostDeletePutUsersFunction = signal(this.baseUrlFunction() + '/admin');
    private urlGetFunctionByIdAndEnabledFunction = signal(this.baseUrlFunction() + "/enabled");
    private urlGetWithFilmIdFunction = signal(this.baseUrlFunction() + '/enabled/film');

    getByIdFunction(_id: number): Observable<FunctionResponse> | null {
        try {

          const headers = this.jwtService.getTokenInHeaders();
          if (!headers) return throwError(() => new Error('Token not found'));

          return this.http.get<FunctionResponse>(this.urlGetFunctionByIdAndEnabledFunction() + `/${ _id }`, { headers: headers }).pipe(
                catchError(error => {
                  return throwError(() => this.parseError.parseErrorFromDB(error));
                }));
        } catch (error) {

          console.log( "Error in getById Function -> " + error )
          return null;
        }
      }

      getByIdFilm(_id: number): Observable<FunctionResponse[]> | null {
        try {

          const headers = this.jwtService.getTokenInHeaders();
          if (!headers) return throwError(() => new Error('Token not found'));

          return this.http.get<FunctionResponse[]>(this.urlGetWithFilmIdFunction() + `/${ _id }`, { headers: headers }).pipe(
                  catchError(error => {
                    return throwError(() => this.parseError.parseErrorFromDB(error));
                  }));
        } catch (error) {

          console.log( "Error in getByIdFilm Function -> " + error )
          return null;
        }
      }

      deleteFunction(_id: number): Observable<any> | null {
        try {

          const headers = this.jwtService.getTokenInHeaders();
          if (!headers) return throwError(() => new Error('Token not found'));

          return this.http.delete<any>(this.urlGetPostDeletePutUsersFunction() + `/${ _id }`, { headers: headers }).pipe(
                  catchError(error => {
                    return throwError(() => this.parseError.parseErrorFromDB(error));
                  }));
        } catch(error) {

          console.log( "Error in delete function -> " + error )
          return null;

        }
      }

      updateFunction( functionObj: UpdateFunctionRequest): Observable<FunctionResponse> | null {
        try {

          const headers = this.jwtService.getTokenInHeaders();
          if (!headers) return throwError(() => new Error('Token not found'));

          return this.http.put<FunctionResponse>( this.urlGetPostDeletePutUsersFunction(), functionObj, { headers}).pipe(
                  catchError(error => {
                    return throwError(() => this.parseError.parseErrorFromDB(error));
                  }));
      } catch(error) {

          console.log( "Error in update Function -> " + error )
          return null;
        }
      }

      createFunction( functionObj: CreateFunctionRequest): Observable<FunctionResponse> | null {
        try {

          const headers = this.jwtService.getTokenInHeaders();

          if (!headers) return throwError(() => new Error('Token not found'));

          return this.http.post<FunctionResponse>(this.urlGetPostDeletePutUsersFunction(), functionObj,{ headers }).pipe(
                  catchError(error => {
                    return throwError(() => this.parseError.parseErrorFromDB(error));
                  }));
        } catch(error) {

          console.log( "Error in post function -> " + error )
          return null;
        }
      }

      getAll(): Observable<FunctionResponse[]> | any{
        try {

          const headers = this.jwtService.getTokenInHeaders();
          if (!headers) return throwError(() => new Error('Token not found'));

          return this.http.get<FunctionResponse[]>(this.urlGetPostDeletePutUsersFunction(), { headers: headers }).pipe(
                  catchError(error => {
                    return throwError(() => this.parseError.parseErrorFromDB(error));
                  }));
        } catch (error) {

          console.log( "Error in getAll functions -> " + error )
          return null
        }
      }

      getAllEnabled(): Observable<FunctionResponse[]> | any{
        try {

          const headers = this.jwtService.getTokenInHeaders();
          if (!headers) return throwError(() => new Error('Token not found'));

          return this.http.get<FunctionResponse[]>(this.baseUrlFunction(), { headers: headers }).pipe(
                  catchError(error => {
                    return throwError(() => this.parseError.parseErrorFromDB(error));
                  }));
        } catch (error) {

          console.log( "Error in getAll enabled functions -> " + error )
          return null
        }
      }
}
