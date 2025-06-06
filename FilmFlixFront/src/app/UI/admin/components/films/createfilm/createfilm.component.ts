import { Component, Inject, inject } from '@angular/core';
import { FilmService } from '../../../../../infraestructure/film/film.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CreateFilmRequest } from '../../../../../domain/models/film/CreateFilmRequest';
import { FilmStaffResponse } from '../../../../../domain/models/filmStaff/FilmStaffResponse';
import { FilmStaffService } from '../../../../../infraestructure/filmStaff/film-staff.service';

@Component({
  selector: 'app-createfilm',
  standalone: false,
  templateUrl: './createfilm.component.html',
  styleUrl: './createfilm.component.css'
})
export class CreatefilmComponent {
private filmServe = inject(FilmService);

  selectedFile: File | null = null;
  previewUrl: string | null = null;


  filmGenres: string[] = ['ACTION','ADVENTURE','ANIMATION','COMEDY','CRIME','DOCUMENTARY','DRAMA','FANTASY','HISTORICAL','HORROR','MUSICAL','MYSTERY','ROMANCE','SCIENCE_FICTION','THRILLER','WAR','WESTERN']
  durationsList: string[] = ['1h 30m', '1h 45m', '2h 0m', '2h 15m', '2h 30m', '2h 45m', '3h 0m'];
  contentForm: FormGroup;

  private staffServ = inject(FilmStaffService)
  private fb = inject(FormBuilder);
  private toastServ = inject(ToastrService);
  public staffListFromDb: FilmStaffResponse[] = [];
  public selectedStaff: number[] = []

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
  ) {

    this.contentForm = this.fb.group({
      title       : ['', [Validators.required, Validators.minLength(3), Validators.maxLength(80)]],
      description : ['', [Validators.required, Validators.minLength(3), Validators.maxLength(290)]],
      duration    : ['', [Validators.required]],
      filmGenre   : ['', [Validators.required]],
      trailerUrl  : ['', [Validators.required, Validators.minLength(3)]],
      filmStaff   : ['', [Validators.required, Validators.minLength(3), Validators.maxLength(80)]]
    })

    if (this.data && this.data.title) {
      const idContent = this.data.id;
    }
  }

  ngOnInit(): void {
    //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    //Add 'implements OnInit' to the class.
    this.preLoadStaff();
  }


  preLoadStaff(){
    this.staffServ.getAllEnabled()?.subscribe({
      next: (response: any) => {
        this.staffListFromDb = response;
      },
      error: (error: any) => {
        this.toastServ.error(error["details"], 'Error loading staff');
      }
    })
  }

  onMultiSelectChange(event: Event): void {
    const selectElement = event.target as HTMLSelectElement;
    const selected: number[] = [];

    for (let i = 0; i < selectElement.options.length; i++) {
      const option = selectElement.options[i];
      if (option.selected) {
        selected.push(+option.value);
      }
    }

    this.selectedStaff = selected;
    console.log(this.selectedStaff)
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];

      const reader = new FileReader();
      reader.onload = () => {
        this.previewUrl = reader.result as string;
      };
      reader.readAsDataURL(this.selectedFile);
    }
  }

  convertStaffToString(): string{
    if (this.selectedStaff.length === 0) return '';
    return this.selectedStaff.map(String).join(' ');
  }

  onSubmit(){
    if (!this.selectedFile) return;

    const filmToCreate: CreateFilmRequest = this.contentForm.value;
    filmToCreate.filmStaff = this.convertStaffToString();

    if(filmToCreate.filmStaff === ''){
      this.toastServ.error('Please select at least one staff member', 'Error');
      return;
    }
    console.log(filmToCreate);
    console.log(this.selectedFile);
    this.filmServe.createFilm(filmToCreate, this.selectedFile)?.subscribe({
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
