import { Routes } from '@angular/router';

export const routes: Routes = [
    {
      path: 'auth',
      loadChildren: () => import('./UI/auth/auth.module').then(m => m.AuthModule)
    },
    {
      path: 'principal',
      loadChildren: () => import('./UI/principal/principal.module').then(m => m.PrincipalModule)
    },
    {
      path: 'admin',
      loadChildren: () => import('./UI/admin/admin.module').then(m => m.AdminModule)
    },
    {
      path: '**',
      redirectTo: 'auth',
    }
];
