import { inject, Injectable, signal } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { UserResponse } from '../../domain/models/user/UserResponse';
import { JwtServService } from '../JWT/jwt-serv.service';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  private _jwtUseCase       = inject(  JwtServService )

  private usersFromDb   = signal<any | UserResponse   >  ( null )

  private loadedData = new BehaviorSubject<boolean>( false )

  getLoadedData() {
    return this.loadedData.asObservable();
  }

  updateLoadedData( value: boolean ) {
    this.loadedData.next( value )
  }

  deleteAllSessionStorage(){

    const sessionLists = [ "genreList", "contentList", "user"]
    sessionLists.forEach( title => {

      if( sessionStorage.getItem( title ) ) sessionStorage.removeItem( title )
    })

    sessionStorage.getItem("token") && sessionStorage.removeItem("token")
  }

  addToSessionStorage( user: UserResponse ){

    if( user ){
      sessionStorage.setItem("user", JSON.stringify( user ))
      this.updateLoadedData( true )
    } else{
      console.log("Calling again storage.service")
    }
  }
}
