import { Component, inject } from '@angular/core';
import { AuthService } from '../../../infraestructure/authentication/auth.service';
import { JwtServService } from '../../../infraestructure/JWT/jwt-serv.service';
import { NavigationEnd, Router } from '@angular/router';
import { StorageService } from '../../../infraestructure/storage/storage.service';

@Component({
  selector: 'app-principal',
  standalone: false,
  templateUrl: './principal.component.html',
  styleUrl: './principal.component.css'
})
export class PrincipalComponent {

  private jwt             = inject( JwtServService );
  private router          = inject( Router );
  private authService     = inject( AuthService );
  private storage        = inject( StorageService );

  constructor() { }

  ngOnInit() {

  }

  logOut(){
    this.storage.deleteAllSessionStorage();
    this.jwt.deleteTokenFromLocal();

    const navigationSubscription = this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        window.location.reload();
        navigationSubscription.unsubscribe();
      }
    });

    this.router.navigate(['auth']);

  }
}
