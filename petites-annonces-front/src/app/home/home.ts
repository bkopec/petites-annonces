import { Component, OnInit, inject, signal, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AnnonceService } from '../services/annonce.service';
import { Departement } from '../models/departement.model';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.html',
  styleUrl: './home.css',
  changeDetection: ChangeDetectionStrategy.OnPush // Use OnPush for zoneless
})
export class HomeComponent implements OnInit {
  private annonceService = inject(AnnonceService);
  departements = signal<Departement[]>([]); // Use signal for reactive state
  loading = signal(true);
  error = signal<string | null>(null);

  ngOnInit(): void {
    this.annonceService.getAllDepartements().subscribe({
      next: (data) => {
        this.departements.set(data);
        this.loading.set(false);
      },
      error: (err) => {
        console.error('Error fetching departments:', err);
        this.error.set('Failed to load departments. Please try again later.');
        this.loading.set(false);
      }
    });
  }
}