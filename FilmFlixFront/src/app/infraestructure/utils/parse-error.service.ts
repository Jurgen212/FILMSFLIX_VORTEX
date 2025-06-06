import { Injectable } from '@angular/core';
import { ErrorResponse } from '../../domain/models/error/ErrorResponse';

@Injectable({
  providedIn: 'root'
})
export class ParseErrorService {

  constructor() { }

  parseErrorFromDB(error: any): ErrorResponse{

    if(error.details) return {
        message: error.error.message,
        code: error.error.code,
        status: error.error.status,
        details: error.error.details[Object.keys(error.error.details)[0]],
        timestamp: new Date()
    }
    else if(error.message)return {
        message: error.error.message,
        code: error.error.code,
        status: error.error.status,
        details: error.error.message,
        timestamp: new Date()
      }
      else return {
        details: error,
        message: '',
        code: '',
        status: '',
        timestamp: new Date()
    }
  }
}

