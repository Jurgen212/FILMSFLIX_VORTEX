import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Injectable({
  providedIn: 'root'
})
export class ToastrservService {

  constructor(private toastr: ToastrService) { }

  showSuccess(message: string, title: string = 'Éxito') {
    this.toastr.success(message, title);
  }

  showError(message: string, title: string = 'Error') {
    this.toastr.error(message, title);
  }

  showWarning(message: string, title: string = 'Advertencia') {
    this.toastr.warning(message, title);
  }

  showInfo(message: string, title: string = 'Información') {
    this.toastr.info(message, title);
  }

  showCustomToast(message: string, title: string, type: 'success' | 'error' | 'warning' | 'info') {
    switch (type) {
      case 'success':
        this.showSuccess(message, title);
        break;
      case 'error':
        this.showError(message, title);
        break;
      case 'warning':
        this.showWarning(message, title);
        break;
      case 'info':
        this.showInfo(message, title);
        break;
    }
  }

  // Toasts con configuración personalizada
  showSuccessWithConfig(message: string, title: string = 'Éxito') {
    this.toastr.success(message, title, {
      timeOut: 5000,
      extendedTimeOut: 2000,
      progressBar: true,
      closeButton: true
    });
  }

  // Toast que no se cierra automáticamente
  showPersistentError(message: string, title: string = 'Error Crítico') {
    this.toastr.error(message, title, {
      timeOut: 0,
      extendedTimeOut: 0,
      closeButton: true,
      tapToDismiss: false
    });
  }
}
