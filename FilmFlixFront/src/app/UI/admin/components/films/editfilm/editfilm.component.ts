import { Component, forwardRef, inject, Inject } from '@angular/core';
import { FormBuilder, FormGroup, NG_VALUE_ACCESSOR, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ToastrService } from 'ngx-toastr';
import { FilmGenre } from '../../../../../domain/enums/FilmGenre';
import { FilmService } from '../../../../../infraestructure/film/film.service';
import { UpdateFilmRequest } from '../../../../../domain/models/film/UpdateFilmRequest';

@Component({
  selector: 'app-editfilm',
  standalone: false,
  templateUrl: './editfilm.component.html',
  styleUrl: './editfilm.component.css',
  providers: [{
    provide: NG_VALUE_ACCESSOR,
    useExisting: forwardRef(() => FilmGenre),
    multi: true,
  }]
})
export class EditfilmComponent {
  private filmServe = inject(FilmService);

  filmGenres: string[] = ['ACTION','ADVENTURE','ANIMATION','COMEDY','CRIME','DOCUMENTARY','DRAMA','FANTASY','HISTORICAL','HORROR','MUSICAL','MYSTERY','ROMANCE','SCIENCE_FICTION','THRILLER','WAR','WESTERN']
  durationsList: string[] = ['1h 30m', '1h 45m', '2h 0m', '2h 15m', '2h 30m', '2h 45m', '3h 0m'];
  contentForm: FormGroup;


  private fb = inject(FormBuilder);
  private toastServ = inject(ToastrService);

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) {

    this.contentForm = this.fb.group({
      id          : '',
      title       : ['', [Validators.required, Validators.nullValidator, Validators.minLength(3), Validators.maxLength(80)]],
      description : ['', [Validators.required, Validators.nullValidator, Validators.minLength(3), Validators.maxLength(200)]],
      duration    : ['', [Validators.required, Validators.nullValidator]],
      filmGenre   : ['', [Validators.required, Validators.nullValidator]],
      trailerUrl  : ['', [Validators.required, Validators.nullValidator, Validators.minLength(3)]],
    })

    if (this.data && this.data.title) {
      const idContent = this.data.id;
      this.contentForm.get('id')?.setValue(idContent)
      this.contentForm.get('title')?.setValue(this.data.title);
      this.contentForm.get('description')?.setValue(this.data.description);
      this.contentForm.get('trailerUrl')?.setValue(this.data.trailerUrl);
      this.contentForm.get('duration')?.setValue(this.data.duration);
      this.contentForm.get('filmGenre')?.setValue(this.data.filmGenre);
      this.contentForm.patchValue(this.data)
    }
  }

  onSubmit(){

    const filmToUpd: UpdateFilmRequest = this.contentForm.value;
    this.filmServe.updateFilm(filmToUpd)?.subscribe({
      next: (response) => {
        this.toastServ.success('Film updated successfully', 'Success');
        location.reload();
      },
      error: (error) => {
        this.toastServ.error(error["details"], 'Error');
      }
    })
  }
}
