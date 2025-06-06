import { Component, Inject, Input } from '@angular/core';
import { FilmResponse } from '../../../../domain/models/film/FilmResponse';
import { Router } from '@angular/router';

@Component({
  selector: 'app-card-layout',
  standalone: false,
  templateUrl: './card-layout.component.html',
  styleUrl: './card-layout.component.css'
})
export class CardLayoutComponent {
 @Input() movies: FilmResponse[] = [];

 constructor(private router: Router) {}


  navigateToMovie(movie: FilmResponse): void {
    if (movie.enabled) {
      this.router.navigate(['/principal/movie', movie.id]);
    }
  }

  onImageError(event: any): void {
    event.target.src = 'https://via.placeholder.com/280x400/cccccc/666666?text=Imagen+No+Disponible';
  }
}
