import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MessageModule } from 'primeng/message';
import { ReactiveFormsModule } from '@angular/forms';
import { InterrptorComponent } from '../components/interrptor/interrptor.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { FooterComponent } from '../components/footer/footer.component';
import { NetLoaderComponent } from '../components/net-loader/net-loader.component';
import { MessageService } from 'primeng/api';
import { AuthService } from '../../infraestructure/authentication/auth.service';
import { HttpClient, HttpHandler } from '@angular/common/http';
import { ToastrservService } from '../../infraestructure/toastr/toastrserv.service';
import { MatIconModule } from '@angular/material/icon'


@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
  ],
  imports: [
    CommonModule,
    AuthRoutingModule,
    ReactiveFormsModule,
    NetLoaderComponent,
    InterrptorComponent,
    FooterComponent,
    MatIconModule
  ],
  providers: [
    MessageService,
    AuthService,
    ToastrservService,
    BrowserAnimationsModule,
    FooterComponent
  ]
})
export class AuthModule { }
