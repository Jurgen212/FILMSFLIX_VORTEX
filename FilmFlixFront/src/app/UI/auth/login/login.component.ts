import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { AuthService } from '../../../infraestructure/authentication/auth.service';
import { JwtServService } from '../../../infraestructure/JWT/jwt-serv.service';
import { Router } from '@angular/router';
import { AuthenticationRequest } from '../../../domain/models/authentication/AuthenticationRequest';
import { ToastrservService } from '../../../infraestructure/toastr/toastrserv.service';
import { StorageService } from '../../../infraestructure/storage/storage.service';

@Component({
  selector: 'app-login',
  standalone: false,
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
 public loginForm       : FormGroup = new FormGroup({});
  public token           : string    = "";
  public loaded          : boolean   = true;

  constructor( private messageService: MessageService,
               private _fb           : FormBuilder  ,
               private authServ      : AuthService ,
               private jwt           : JwtServService,
               private router        : Router,
               private _toastServ    : ToastrservService,
               private storageServ   : StorageService
    ){
    this.loginForm = this._fb.group({
      email       : ['', [Validators.required, Validators.email]],
      password    : ['', Validators.required]
    });
  }

  ngOnInit() {

  }

  validarUsuarioDB(): Promise<any>{

    try{

      const user: AuthenticationRequest = {
        "email": this.loginForm.value['email'],
        "password": this.loginForm.value['password']
      }

      this.loaded = false;

      return new Promise( (resolve, reject ) =>{
        this.authServ.login( user )?.subscribe({
          next: ( data: any ) =>{
            this.loaded = true;
            resolve( data )
          }, error: ( err: any ) =>{
            this.loaded = true;
            this._toastServ.showError( err["details"]);
            //this.messageService.add({ severity:'error', summary: 'Error', detail: err.error.message });
            reject( err )
          }
        })
      })
    } catch( err ){
      this.messageService.add({ severity:'error', summary: 'Error', detail: 'Please contact us' });

      this.loginForm.reset();

      return new Promise(()=>{})
    }
  }

  submitForm(): void{
    if ( this.validarObligatorios() ) return

    try {
      if ( this.loginForm.valid ) {
        this.validarUsuarioDB().then( data =>{
          this.token = data.accessToken
          this.storageServ.addToSessionStorage( data.user );
          this.jwt.saveTokenFromSesion( this.token );
          //this.messageService.add({ severity:'success', summary: 'Success', detail: 'Session started successfully' });

          this._toastServ.showSuccess( "Welcome " + data.user.name, "Success" );

          if (data.user.role == "ADMIN") this.router.navigate(['/admin'])
          else this.router.navigate(['/principal'])
        }).catch( err =>{
          this.loginForm.reset();
        })
      } else {
        this._toastServ.showWarning('Enter a valid email and/or password, all fields are required', "Warning");
        this.loginForm.reset();
      }
    } catch (error) {
      this._toastServ.showError( 'Unknown error',"Error");
      //this.messageService.add({ severity:'error', summary: 'Error', detail: 'Please contact us' });
    }
  }

  validarObligatorios(): boolean{
    const formulario = this.loginForm.controls;
    const validacion = formulario['email'].invalid || formulario['password'].invalid

    if( validacion ){
      this.messageService.add({ severity:'warn', summary: 'Warning', detail: 'It is essential to properly complete all fields' });
      this.loginForm.markAllAsTouched();
      return true
    }
    return false
  }
}
