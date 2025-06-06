import { ApplicationConfig, importProvidersFrom, provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';


import { routes } from './app.routes';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideHttpClient } from '@angular/common/http';
import { provideToastr, ToastrModule } from 'ngx-toastr';
import { provideAnimations } from '@angular/platform-browser/animations';

export const appConfig: ApplicationConfig = {
  providers: [provideZoneChangeDetection({ eventCoalescing: true }),
              provideRouter(routes),
              provideHttpClient(),
              provideAnimations(),
              importProvidersFrom(
                  ToastrModule.forRoot({
                    timeOut: 3000,
                    positionClass: 'toast-top-right',
                    preventDuplicates: true,
                    progressBar: true,
                    closeButton: true,
                    enableHtml: true,
                    tapToDismiss: true,
                    extendedTimeOut: 1000,
                    maxOpened: 3,
                    autoDismiss: true,
                    newestOnTop: true,
                    toastClass: 'ngx-toastr custom-toast',
                    titleClass: 'custom-toast-title',
                    messageClass: 'custom-toast-message'
                  })
                ), provideAnimationsAsync()]
};
