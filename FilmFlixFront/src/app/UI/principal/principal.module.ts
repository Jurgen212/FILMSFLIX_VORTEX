import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PrincipalRoutingModule } from './principal-routing.module';
import { CardLayoutComponent } from './components/card-layout/card-layout.component';
import { PrincipalpageComponent } from './pages/principalpage/principalpage.component';
import { MovieDetailComponent } from './pages/movie-detail/movie-detail.component';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [CardLayoutComponent, PrincipalpageComponent, MovieDetailComponent],
  imports: [
    CommonModule,
    PrincipalRoutingModule,
    FormsModule
  ]
})
export class PrincipalModule { }
