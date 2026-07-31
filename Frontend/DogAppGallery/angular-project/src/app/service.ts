import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { DogResponse } from './dogResponse';

@Injectable({
  providedIn: 'root',
})
export class DogService {
  private http = inject(HttpClient);
  private apiUrl = 'https://dog.ceo/api/breeds/image/random';

  getRandomDogImage(): Observable<DogResponse> {
    return this.http.get<DogResponse>(this.apiUrl);
  }
}
