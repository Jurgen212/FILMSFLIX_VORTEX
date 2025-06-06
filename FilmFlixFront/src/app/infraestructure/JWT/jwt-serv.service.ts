import { Injectable } from '@angular/core';
import { JwtModel } from '../../domain/models/JWT/JwtModel';
import { HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class JwtServService {

  constructor() { }

  getTokenFromLocal(): JwtModel | null{

    if( sessionStorage.getItem("token") ){
      return {token: JSON.parse( sessionStorage.getItem("token")!) }

    } else if( localStorage.getItem("token") ){
      return { token: JSON.parse( localStorage.getItem("token")! ) }
    }
    else return null
  }

  deleteTokenFromLocal(): void {
    localStorage.getItem("token") && localStorage.removeItem("token")
  }

  saveTokenFromLocal( _token: string): JwtModel | null {

    localStorage.setItem( "token", JSON.stringify(_token)! )

    const tokenCreated = JSON.parse(localStorage.getItem("token")!)

    if( !tokenCreated ) return null
    return { token: tokenCreated }
  }

  saveTokenFromSesion( _token: string): JwtModel | null {

    sessionStorage.setItem( "token", JSON.stringify(_token)! )

    const tokenCreated = JSON.parse(sessionStorage.getItem("token")!)

    if( !tokenCreated ) return null
    return { token: tokenCreated }
  }

  getTokenInHeaders() {

    const token = this.getTokenFromLocal()?.token
    if( !token ) return null
    return new HttpHeaders({ 'Authorization': 'Bearer ' + token })
  }
}
