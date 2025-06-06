import { Component, inject, Input } from '@angular/core';
import { FilmResponse } from '../../../../domain/models/film/FilmResponse';
import { ActivatedRoute, Router } from '@angular/router';
import { FilmService } from '../../../../infraestructure/film/film.service';
import { Toast, ToastrService } from 'ngx-toastr';
import { ToastrservService } from '../../../../infraestructure/toastr/toastrserv.service';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';
import { FunctionResponse } from '../../../../domain/models/function/FunctionResponse';
import { FunctionService } from '../../../../infraestructure/function/function.service';
import { TicketService } from '../../../../infraestructure/ticket/ticket.service';
import { CreateTicketRequest } from '../../../../domain/models/ticket/CreateTicketRequest';
import { PaymentMethod } from '../../../../domain/enums/PaymentMethod';

@Component({
  selector: 'app-movie-detail',
  standalone: false,
  templateUrl: './movie-detail.component.html',
  styleUrl: './movie-detail.component.css'
})
export class MovieDetailComponent {
  @Input() imageUrl: string = '/fondoLogin.jpg';
  @Input() functions: FunctionResponse[] = [];
  private filmService = inject(FilmService);
  private toastServ = inject(ToastrService);
  private functionServ = inject(FunctionService)
  private ticketServ = inject(TicketService);
  trailerEmbedUrl: SafeResourceUrl | null = null;

  movieData: FilmResponse | null = null;
  movieId: number | null = null;
  ticketQuantity: number = 1;
  selectedFunction: string = '';
  selectedPaymentMethod: string = '';
  totalPrice: number = 0;

  constructor(private router: ActivatedRoute, private sanitizer: DomSanitizer, private redirecRouter: Router) {}

  paymentMethods = [
    { id: 'CREDIT_CARD', name: 'Credit Card' },
    { id: 'DEBIT_CARD', name: 'Debit Card' },
    { id: 'PAYPAL', name: 'PayPal' },
    { id: 'GOOGLE_PAY', name: 'Google Pay' },
    { id: 'APPLE_PAY', name: 'Apple Pay' }
  ];

  ngOnInit() {
    // Acceder directamente desde el state
    this.router.paramMap.subscribe((params: any) => {
      const idParam = params.get('id');
      if (idParam) {
        this.loadResource(+idParam);
      }
    });
  }

  loadResource(id: number){
    this.filmService.getByIdFilm(id)?.subscribe({
      next: (response: FilmResponse) => {
        this.movieData = response;
        console.log('Movie Data:', this.movieData);
        this.processTrailerUrl();
        this.loadFunctions(this.movieData.id);
      },
      error: (error: any) => {
        console.error('Error loading movie data:', error);
        this.movieData = null;
        this.toastServ.error(error["details"])
      }
      })
  }

  loadFunctions(idFilm: number){
    this.functionServ.getByIdFilm(idFilm)?.subscribe(
     {
      next: (response: FunctionResponse[]) => {
        this.functions = response;
        console.log('Functions Data:', this.functions);
      },
      error: (error: any) => {
        console.error('Error loading functions data:', error);
        this.functions = [];
        this.toastServ.error(error["details"]);
      }
     }
    );
  }

  private processTrailerUrl(): void {
    if (this.movieData?.trailerUrl) {
      const youtubeId = this.extractYouTubeId(this.movieData.trailerUrl);
      if (youtubeId) {
        const embedUrl = `https://www.youtube.com/embed/${youtubeId}`;
        this.trailerEmbedUrl = this.sanitizer.bypassSecurityTrustResourceUrl(embedUrl);
      }
    }
  }

  private loadFunctionsByMovieId(){
    if(!this.movieData?.id) {
      this.toastServ.error('Movie ID is not available');
      return;
    }

    this.functionServ.getByIdFilm(this.movieData.id)?.subscribe({
      next: (response: FunctionResponse[]) => {
        this.functions = response;
        console.log('Functions Data:', this.functions);
      },
      error: (error: any) => {
        console.error('Error loading functions data:', error);
        this.functions = [];
        this.toastServ.error(error["details"]);
      }
    })
  }

  private extractYouTubeId(url: string): string | null {
    const regExp = /^.*(youtu.be\/|v\/|u\/\w\/|embed\/|watch\?v=|&v=)([^#&?]*).*/;
    const match = url.match(regExp);
    return (match && match[2].length === 11) ? match[2] : null;
  }

  onFunctionClick(functionData: FunctionResponse): void {
    if (functionData.enabled) {
      console.log('Datos de la función:', functionData);
      console.log('Datos de la película:', this.movieData);
    }
  }

  formatTime(time: string): string {
    switch(time){
      case 'THREE':
        return '3:00PM';
      case 'FIVE':
        return '5:00PM';
      case 'EIGHT':
        return '8:00PM';
      case 'TEN':
        return '10:00PM';
      default:
        return time;
    }
    return time;
  }

  onRoomImageError(event: any): void {
    event.target.src = 'https://via.placeholder.com/200x80/cccccc/666666?text=Sala';
  }

  isUserLoggedIn(): boolean {
    const user = localStorage.getItem('token') || sessionStorage.getItem('user');
    return user !== null || user !== undefined;
  }



  calculateTotal(): void {
    if (this.selectedFunction && this.ticketQuantity > 0) {
      const selectedFunc = this.functions.find(f => (f.id + "") === this.selectedFunction);
      if (selectedFunc) {
        this.totalPrice = selectedFunc.functionPrice * this.ticketQuantity;
      }
    } else {
      this.totalPrice = 0;
    }
  }

  // Método para validar si se puede comprar
  canPurchase(): boolean {
    return this.ticketQuantity > 0 &&
           this.selectedFunction !== '' &&
           this.selectedPaymentMethod !== '' &&
           this.totalPrice > 0;
  }

  // Método para comprar tickets
  purchaseTickets(): void {
    const user = JSON.parse( (localStorage.getItem('token') || sessionStorage.getItem('user'))! );

    if (this.canPurchase() && this.movieData) {
      const purchaseData: CreateTicketRequest = {
        userId: parseInt(user?.id),
        filmId: this.movieData?.id,
        functionId: parseInt(this.selectedFunction),
        quantity: this.ticketQuantity,
        paymentMethod: this.selectedPaymentMethod as unknown as PaymentMethod
      };

      console.log('Datos de compra:', purchaseData);

      this.ticketServ.createTicket(purchaseData)?.subscribe({
        next: (response: any) => {
          console.log('Compra exitosa:', response);
          this.toastServ.success('Congratulations! Tickets purchased successfully.');
          this.redirecRouter.navigate(['/principal']);
        },
        error: (error: any) => {
          console.error('Error al comprar tickets:', error);
          this.toastServ.error(error["details"]);
        }
      })
    }
  }

  redirectToLogin(): void {
    this.redirecRouter.navigate(['/register']);
    console.log('Redirigiendo al login...');
  }
}
