import { forwardRef, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { Footer, MessageService } from 'primeng/api';
import { AuthService } from '../../infraestructure/authentication/auth.service';
import { ToastrservService } from '../../infraestructure/toastr/toastrserv.service';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { PrincipalComponent } from './principal/principal.component';
import { FooterComponent } from '../components/footer/footer.component';
import { InterrptorComponent } from '../components/interrptor/interrptor.component';
import { NetLoaderComponent } from '../components/net-loader/net-loader.component';
import { FormsModule, NG_VALUE_ACCESSOR, ReactiveFormsModule } from '@angular/forms';
import { MatDialogModule } from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import { MatOptionModule } from '@angular/material/core';
import {MatPaginatorModule} from '@angular/material/paginator';
import {MatTableModule} from '@angular/material/table';
import {MatSortModule} from '@angular/material/sort';
import { FilmsComponent } from './components/films/films.component';
import { MatIconModule } from '@angular/material/icon';
import {MatGridListModule} from '@angular/material/grid-list';
import { EditfilmComponent } from './components/films/editfilm/editfilm.component';
import { CreatefilmComponent } from './components/films/createfilm/createfilm.component';
import {MatSelectModule} from '@angular/material/select';
import { FunctionsComponent } from "./components/functions/functions.component";
import { UsersComponent } from './components/users/users.component';

@NgModule({
  declarations: [PrincipalComponent, EditfilmComponent, CreatefilmComponent, FilmsComponent, FunctionsComponent, UsersComponent],
  imports: [
    CommonModule,
    AdminRoutingModule,
    FooterComponent,
    ReactiveFormsModule,
    NetLoaderComponent,
    InterrptorComponent,
    MatDialogModule,
    MatFormFieldModule,
    MatOptionModule,
    MatPaginatorModule,
    MatTableModule,
    MatSortModule,
    MatIconModule,
    MatGridListModule,
    FormsModule,
    ReactiveFormsModule,
    MatSelectModule,

],
  providers: [
    MessageService,
    AuthService,
    ToastrservService,
    BrowserAnimationsModule,
  ]
})
export class AdminModule { }
