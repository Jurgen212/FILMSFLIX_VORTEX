import { Component } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { CreateUserRequest } from '../../../domain/models/user/CreateUserRequest';
import { AuthService } from '../../../infraestructure/authentication/auth.service';
import { ToastrservService } from '../../../infraestructure/toastr/toastrserv.service';

@Component({
  selector: 'app-register',
  standalone: false,
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  registerForm: FormGroup = new FormGroup({})
  public loaded: boolean  = true;


  constructor( private _fb              : FormBuilder,
               private messageService   : MessageService,
               private authService      : AuthService,
               private router           : Router,
               private _toastServ       : ToastrservService

  ) {
    this.registerForm = this._fb.group({
      name            : ['', [Validators.required, Validators.pattern(/^[A-Za-záéíóúÁÉÍÓÚ\s]+$/), Validators.maxLength(20)]],
      last_name       : ['', [Validators.required, Validators.pattern(/^[A-Za-záéíóúÁÉÍÓÚ\s]+$/), Validators.maxLength(40)]],
      phoneNumber     :  ['',[Validators.required, Validators.pattern(/^\d{10}$/)]],
      email           : ['', [Validators.required, Validators.email]],
      password        : ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword : ['', [Validators.required, this.validateConfirm.bind(this)]],
    });
  }

  ngOnInit() {}

  submitForm(){
    if ( this.validarObligatorios() ) return

    try{
      const user = {
        "name": this.registerForm.value['name'],
        "lastName": this.registerForm.value['last_name'],
        "email": this.registerForm.value['email'],
        "password": this.registerForm.value['password'],
        "phoneNumber": this.registerForm.value['phoneNumber']
      }

      this.loaded = false;

      const userToRegister: CreateUserRequest = user;
      return new Promise( (resolve, reject ) =>{
        this.authService.register( userToRegister )?.subscribe({
          next: ( data: any ) =>{
            //this.messageService.add({severity:'success', summary: 'Success', detail: 'Registration done successfully'});
            console.log(data)
            this._toastServ.showSuccess( "Welcome " + data.user.name, "Success" );
            this.loaded = true;
            resolve( data )
            this.router.navigate(['principal']);

          }, error: ( err: any ) =>{
            this.loaded = true;
            //this.messageService.add({ severity:'error', summary: err["message"], detail: err.details });
            console.log(err)
            this._toastServ.showError( err["details"]);
            reject( err )
          }
        })
      })
    } catch( err:any ){
      //this.messageService.add({ severity:'error', summary: 'Error', detail: 'Please contact us' });
      this._toastServ.showError( err["details"]);

      this.registerForm.reset();
      return new Promise(()=>{})
    }
  }

  validarObligatorios(): boolean{
    const formulario = this.registerForm.controls;
    const validacion = formulario['email'].invalid || formulario['password'].invalid || formulario['name'].invalid || formulario['last_name'].invalid || formulario['confirmPassword'].invalid || formulario['phoneNumber'].invalid

    if( validacion ){
      this.messageService.add({ severity:'warn', summary: 'Warning', detail: 'It is essential to properly complete all fields' });

      this.registerForm.markAllAsTouched();
      return true
    }
    return false
  }

  validateConfirm(control: AbstractControl): { [key: string]: any } | null {
    const contrasenia = this.registerForm.get('password')?.value;
    const confirmContra = control.value;

    if (contrasenia === confirmContra) {
      return null;
    }

    return { contraseniaNoCoincide: true };
  }
}
