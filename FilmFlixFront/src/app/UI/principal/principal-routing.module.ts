import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PrincipalpageComponent } from './pages/principalpage/principalpage.component';
import { MovieDetailComponent } from './pages/movie-detail/movie-detail.component';

const routes: Routes = [
  {
    path: 'principal',
    component: PrincipalpageComponent
  },
  {
   path: 'movie/:id',
   component: MovieDetailComponent
  },
  {
    path: '**',
    redirectTo: 'principal'
  }];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class PrincipalRoutingModule { }
