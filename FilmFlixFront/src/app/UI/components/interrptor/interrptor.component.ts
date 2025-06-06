import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-interruptor',
  standalone: true,
  imports: [],
  templateUrl: './interrptor.component.html',
  styleUrl: './interrptor.component.css'
})
export class InterrptorComponent {
  @Input() mensajeError: string = '';
}
