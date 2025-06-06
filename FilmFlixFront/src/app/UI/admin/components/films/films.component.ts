import { Component, inject, ViewChild } from '@angular/core';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { MessageService } from 'primeng/api';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { FilmStaffResponse } from '../../../../domain/models/filmStaff/FilmStaffResponse';
import { FilmService } from '../../../../infraestructure/film/film.service';
import { FilmResponse } from '../../../../domain/models/film/FilmResponse';
import { UserResponse } from '../../../../domain/models/user/UserResponse';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';
import { CreatefilmComponent } from './createfilm/createfilm.component';
import { EditfilmComponent } from './editfilm/editfilm.component';

@Component({
  selector: 'app-films',
  standalone: false,
  templateUrl: './films.component.html',
  styleUrl: './films.component.css'
})
export class FilmsComponent {
  private filmServ = inject(FilmService)
  private toastServ = inject(ToastrService)
  private router = inject(Router)

  displayedColumns: string[] = ['id','posterUrl', 'title', 'description', 'trailerUrl', 'duration', 'filmStaff', 'enabled', 'actions'];
  dataSource: MatTableDataSource<FilmResponse> = new MatTableDataSource();
  filmList: FilmResponse[] = [];;

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  private dialog = inject(MatDialog)
  private messageService = inject(MessageService)

  ngOnInit(): void {
    this.loadFilms();
  }

  loadFilms(){
    this.filmServ.getAll()?.subscribe({
      next: (data: UserResponse) => {
        this.filmServ.getAll()
          ?.subscribe((res: FilmResponse[]) => {
              this.filmList = res;
              this.dataSource = new MatTableDataSource(this.filmList)
              this.dataSource.paginator = this.paginator;
              this.dataSource.sort = this.sort
              console.log(res)
            })

      }, error: (err: any) => {
        this.toastServ.error(err["details"])
        }
      })
  }

  truncateList(list: FilmStaffResponse[], maxLength: number = 1): string {
    if (!list || list.length === 0) return '';

    const formattedList = list.map(person => {
      const firstName = person.name.split(' ')[0];
      const roleInitial = person.staffRol === 'DIRECTOR' ? 'D' : 'A';
      return `${firstName} (${roleInitial})`;
    });

    if (formattedList.length <= maxLength) {
      return formattedList.join(', ');
    } else {
      const truncated = formattedList.slice(0, maxLength);
      return `${truncated.join(', ')} and ${formattedList.length - maxLength} more`;
      }
    }

  truncate(description: string, maxLength: number = 20): string {
    if (description.length <= maxLength) {
      return description;
    } else {
      return description.substr(0, maxLength) + '...';
    }
  }

  openAddEditForm() {
    const dialogRef = this.dialog.open(CreatefilmComponent, {
      height: '80vh',
      width: '500px'
    });

    dialogRef.afterClosed().subscribe({
      next: (val) => {
        if (val) {
          this.loadFilms()
        }
      }
    })
  }

  openEditForm(data: any) {
    const dialogRef = this.dialog.open(EditfilmComponent, {
      data,
      height: '80vh',
      width: '500px'
    });

    dialogRef.afterClosed().subscribe({
      next: (val: any) => {
        if (val) {
          this.loadFilms()
        }
      }
    })
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }

  changeStatus(row: any) {
    this.filmServ.deleteFilm(row.id)?.subscribe({
      next: (data: any) => {
        this.toastServ.success("Change visivility successfully");
        location.reload();
      },
      error: (err: any) => {
        this.toastServ.error(err["details"]);
      }
    })
  }
}
