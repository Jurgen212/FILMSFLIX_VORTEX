import { Component, Input, inject } from '@angular/core';
import { FilmResponse } from '../../../../domain/models/film/FilmResponse';
import { FilmService } from '../../../../infraestructure/film/film.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-principalpage',
  standalone: false,
  templateUrl: './principalpage.component.html',
  styleUrl: './principalpage.component.css'
})
export class PrincipalpageComponent {
  @Input() imageUrl: string = '/fondoLogin.jpg';
  @Input() showOverlay: boolean = true;

  private filmServ = inject(FilmService)
  private toastServ = inject(ToastrService)

  films: FilmResponse[] = []

  ngOnInit(): void {
    //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    //Add 'implements OnInit' to the class.
    this.filmServ.getAll()?.subscribe({
      next: (response: FilmResponse[]) => {
        this.films = response;
      },
      error: (error: any) => {
        console.error('Error fetching films:', error);
        this.toastServ.error(error["details"]);
      }
    });
  }
}
