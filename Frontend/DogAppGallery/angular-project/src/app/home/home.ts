import { Component, inject, Pipe, PipeTransform, signal } from '@angular/core';
import { forkJoin } from 'rxjs';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { AuthService } from '../auth/authService';
import { DogService } from '../service';
import { Auth } from '../auth/auth';
import { RouterLink } from '@angular/router';
import { RepeatPipe } from './pipe';

@Component({
  selector: 'app-home',
  imports: [MatButtonModule, MatToolbarModule, MatIconModule, RouterLink, RepeatPipe],
  templateUrl: 'home.html',
  styleUrl: './home.css',
})
export class Home {
  private dogService = inject(DogService);
  private authService = inject(AuthService);
  dogImages = signal<string[]>([]);

  loadDogs(): void {
    const requests = [
      this.dogService.getRandomDogImage(),
      this.dogService.getRandomDogImage(),
      this.dogService.getRandomDogImage(),
    ];

    forkJoin(requests).subscribe({
      next: (results) => {
        const images = results.map((dog) => dog.message);
        this.dogImages.set(images);
      },
      error: (err) => console.error('Failed to fetch dogs', err),
    });
  }

  login(email: string, password: string): void {
    this.authService.login(email, password).subscribe({
      next: (response) => {
        console.log('Logged in successfully', response);
      },
      error: (err) => console.error('Login failed', err),
    });
  }

  logout(): void {
    this.authService.logout();
  }
}
