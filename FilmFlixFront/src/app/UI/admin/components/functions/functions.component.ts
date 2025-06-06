import { Component, inject } from '@angular/core';
import { FilmService } from '../../../../infraestructure/film/film.service';
import { ToastrService } from 'ngx-toastr';
import { FunctionService } from '../../../../infraestructure/function/function.service';
import { FunctionResponse } from '../../../../domain/models/function/FunctionResponse';
import { RoomResponse } from '../../../../domain/models/room/RoomResponse';
import { FilmResponse } from '../../../../domain/models/film/FilmResponse';
import { RoomService } from '../../../../infraestructure/room/room.service';
import { CreateFunctionRequest } from '../../../../domain/models/function/CreateFunctionRequest';
import { FunctionTime } from '../../../../domain/enums/FunctionTIme';

interface Horario {
  id: string;
  nombre: string;
  valor: 'THREE' | 'FIVE' | 'EIGHT' | 'TEN';
}

@Component({
  selector: 'app-functions',
  standalone: false,
  templateUrl: './functions.component.html',
  styleUrl: './functions.component.css'
})
export class FunctionsComponent {
  private filmServ = inject(FilmService)
  private toastServ = inject(ToastrService)
  private functionServ = inject(FunctionService);
  private roomService = inject(RoomService);

  rooms: RoomResponse[] = [];
  peliculas: FilmResponse[] = [];
  funcionesExistentes: FunctionResponse[] = [];

  horarios: Horario[] = [
    { id: 'THREE', nombre: '3:00 PM', valor: 'THREE' },
    { id: 'FIVE', nombre: '5:00 PM', valor: 'FIVE' },
    { id: 'EIGHT', nombre: '8:00 PM', valor: 'EIGHT' },
    { id: 'TEN', nombre: '10:00 PM', valor: 'TEN' }
  ];

  isGuardando = false;
  roomGuardandoId: number | null = null;
  mensajeExito = '';
  mensajeError = '';

  ngOnInit() {
    this.cargarDatos();
  }

  cargarDatos() {
    // Simular carga desde DB - reemplazar con llamadas reales
    this.cargarRooms();
  }

 cargarPeliculas() {

  return new Promise((resolve, reject) => {
    this.filmServ.getAllEnabled().subscribe({
      next: (peliculas: FilmResponse[]) => {
        this.peliculas = peliculas;
        resolve(peliculas)
      },
      error: (error: any) => {
        this.toastServ.error('Error loading Movies.');
        console.error('Error cargando películas:', error);
        reject(error)
          },
      })
    })
  }

  cargarRooms(){

    return new Promise((resolve, reject) => {
      this.roomService.getAllEnabled().subscribe({
      next: (rooms: RoomResponse[]) => {
        this.rooms = rooms;
        this.cargarPeliculas().then(() => {

            this.rooms = this.rooms.map(r => ({
              ...r,
              peliculasAsignadas: {}
            }));
            this.cargarFuncionesExistentes();
            this.rooms.forEach((room: any) => {
            this.horarios.forEach((horario: any) => {

            room.peliculasAsignadas[horario.valor] = null;
            });
        });
        resolve(rooms)
    });
      },
      error: (error: any) => {
        this.toastServ.error('Error loading Rooms.');
        console.error('Error cargando salas:', error);
        reject(error)
      },
    }) })

  }



  cargarFuncionesExistentes() {
    return new Promise((resolve, reject) => {
      this.functionServ.getAllEnabled().subscribe({
        next: (funciones: FunctionResponse[]) => {
          this.funcionesExistentes = funciones;
        },
        error: (error: any) => {
          this.toastServ.error('Error loading existing functions.');
          console.error('Error cargando funciones existentes:', error);
        },
      })
    })
  }

  getFuncionExistente(roomId: number, horario: string): FunctionResponse | undefined {
    return this.funcionesExistentes.find(f => f.room.id === roomId && f.time === horario);
  }

  onPeliculaSeleccionada(room: RoomResponse, horario: string, event: Event) {
    const selectElement = event.target as HTMLSelectElement;
    const peliculaId = parseInt(selectElement.value);

    if (peliculaId) {
      const pelicula = this.peliculas.find(p => p.id === peliculaId);
      room.peliculasAsignadas[horario] = pelicula || null;
    } else {
      room.peliculasAsignadas[horario] = null;
    }
  }

  choosePrice(time: string): string {
    switch (time) {
      case 'THREE':
        return '10000';
      case 'FIVE':
        return '12000';
      case 'EIGHT':
        return '15000';
      case 'TEN':
        return '18000';
      default:
        return '$0';
    }
  }

  tieneAsignacionesPendientes(room: RoomResponse): boolean {
    return Object.values(room.peliculasAsignadas).some(pelicula => pelicula !== null);
  }

  async guardarAsignaciones(room: RoomResponse) {
    const asignaciones = Object.entries(room.peliculasAsignadas)
      .filter(([_, pelicula]) => pelicula !== null)
      .map(([horario, pelicula]) => ({
        roomId: room.id,
        time: (horario as unknown as FunctionTime),
        filmId: (pelicula as FilmResponse).id,
        functionPrice: this.choosePrice(horario),
      }));

    if (asignaciones.length === 0) {
      this.mostrarError('You don\'t have any pending assignments to save.');
      return;
    }

    this.isGuardando = true;
    this.roomGuardandoId = room.id;
    this.limpiarMensajes();

    this.simularGuardadoEnDB(asignaciones)
  }

  private async simularGuardadoEnDB(asignaciones: CreateFunctionRequest[]): Promise<void> {
    console.log(asignaciones)
    asignaciones.forEach(asign => {
      if(!asign) return;
      this.functionServ.createFunction(asign)?.subscribe({
        next: (response) => {

          this.toastServ.success(`Room ${asign.roomId} at ${asign.time} saved successfully.`);
          this.cargarFuncionesExistentes();
        },
        error: (error) => {
          this.toastServ.error(`Error saving function for room ${asign.roomId} at ${asign.time}: ${error.message}`);
          console.error('Error saving function:', error);
        },
        complete: () => {
          this.isGuardando = false;
          this.roomGuardandoId = null;
          location.reload();
        }
      });
    })
  }

  onImageError(event: Event) {
    const img = event.target as HTMLImageElement;
    img.src = 'https://via.placeholder.com/80x80/999/white?text=IMG';
  }

  trackByRoom(index: number, room: RoomResponse): number {
    return room.id;
  }

  trackByPelicula(index: number, pelicula: FilmResponse): number {
    return pelicula.id;
  }

  private mostrarExito(mensaje: string) {
    this.mensajeExito = mensaje;
    setTimeout(() => this.mensajeExito = '', 5000);
  }

  private mostrarError(mensaje: string) {
    this.mensajeError = mensaje;
    setTimeout(() => this.mensajeError = '', 5000);
  }

  private limpiarMensajes() {
    this.mensajeExito = '';
    this.mensajeError = '';
  }
}
