import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { JwtServService } from '../JWT/jwt-serv.service';
import { environment } from '../../../environments/environment.development';
import { catchError, Observable, throwError } from 'rxjs';
import { FilmResponse } from '../../domain/models/film/FilmResponse';
import { CreateFilmRequest } from '../../domain/models/film/CreateFilmRequest';
import { UpdateFilmRequest } from '../../domain/models/film/UpdateFilmRequest';
import { ParseErrorService } from '../utils/parse-error.service';

@Injectable({
  providedIn: 'root'
})
export class FilmService {

    constructor(private http: HttpClient,
                private jwtService: JwtServService,
                private parseError: ParseErrorService) { }

    private baseUrlFilm = signal(environment.API_URL + '/film');
    private urlPutPostDeleteFilm = signal(this.baseUrlFilm() + '/admin');
    private urlSearchByTitleFilm = signal(this.baseUrlFilm() + '/title');
    private urlGetFilmWithId = signal(this.baseUrlFilm() + '/enabled');

     getByTitle(title: string): Observable<FilmResponse> | null {
        try {
          const headers = this.jwtService.getTokenInHeaders();
          if (!headers) return throwError(() => new Error('Token not found'));

          return this.http.get<FilmResponse>(this.urlSearchByTitleFilm() + `/${ title }`, { headers: headers }).pipe(
                catchError(error => {
                  return throwError(() => this.parseError.parseErrorFromDB(error));
                }));
        } catch (error) {

          console.log( "Error in getByTitle film -> " + error )
          return null;
        }
      }

     getByIdFilm(film: number): Observable<FilmResponse> | null {
        try {
          const headers = this.jwtService.getTokenInHeaders();
          if (!headers) return throwError(() => new Error('Token not found'));

          return this.http.get<FilmResponse>(this.urlGetFilmWithId() + `/${ film }`, { headers: headers }).pipe(
                  catchError(error => {
                    return throwError(() => this.parseError.parseErrorFromDB(error));
                  }));
        } catch (error) {

          console.log( "Error in getById Film -> " + error )
          return null;
        }
      }

      deleteFilm(_id: number): Observable<any> | null {
        try {
          const headers = this.jwtService.getTokenInHeaders();
          if (!headers) return throwError(() => new Error('Token not found'));
          return this.http.delete<any>(this.urlPutPostDeleteFilm() + `/${ _id }`, { headers: headers }).pipe(
                  catchError(error => {
                    return throwError(() => this.parseError.parseErrorFromDB(error));
                  }));

        } catch(error) {

          console.log( "Error in delete Film -> " + error )
          return null;

        }
      }

      updateFilm(film: UpdateFilmRequest): Observable<FilmResponse> | null {
        try {
          const headers = this.jwtService.getTokenInHeaders();
          if (!headers) return throwError(() => new Error('Token not found'));

          return this.http.put<FilmResponse>( this.urlPutPostDeleteFilm(), film, { headers}).pipe(
                  catchError(error => {
                    return throwError(() => this.parseError.parseErrorFromDB(error));
                  }));

      } catch(error) {

          console.log( "Error in update Film -> " + error )
          return null;
      }
      }

      createFilm(film: CreateFilmRequest, file: File ): Observable<FilmResponse> | null {
        try {

          const headers = this.jwtService.getTokenInHeaders();
          if (!headers) {
            return throwError(() => new Error('Token not found'));
          }

          if(!file) {
            return throwError(() => new Error('Image file is required'));
          }

          const formData = new FormData();
          formData.append('title', film.title);
          formData.append('description', film.description);
          formData.append('duration', film.duration);
          formData.append('filmGenre', film.filmGenre + "");
          formData.append('trailerUrl', film.trailerUrl);
          formData.append('filmStaff', film.filmStaff);
          formData.append('poster', file, file.name);

        return this.http.post<FilmResponse>(this.urlPutPostDeleteFilm(), formData, { headers }).pipe(
            catchError(error => {
              return throwError(() => this.parseError.parseErrorFromDB(error));
            }));
      } catch (error) {
        console.error('Error in post Film ->', error);
        return null;
      }
    }


      getAll(): Observable<FilmResponse[]> | any{
        try {

          const headers = this.jwtService.getTokenInHeaders();
          if (!headers) return throwError(() => new Error('Token not found'));

          return this.http.get<FilmResponse[]>(this.baseUrlFilm(), { headers: headers }).pipe(
                  catchError(error => {
                    return throwError(() => this.parseError.parseErrorFromDB(error));
                  }));

        } catch (error) {

          console.log( "Error in getAll User -> " + error )
          return null
        }
      }

      getAllEnabled(): Observable<FilmResponse[]> | any{
        try {

          const headers = this.jwtService.getTokenInHeaders();
          if (!headers) return throwError(() => new Error('Token not found'));

          return this.http.get<FilmResponse[]>(this.urlPutPostDeleteFilm(), { headers: headers }).pipe(
                  catchError(error => {
                    return throwError(() => this.parseError.parseErrorFromDB(error));
                  }));

        } catch (error) {

          console.log( "Error in getAllEnabled Film -> " + error )
          return null
        }
      }
}
