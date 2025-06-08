import { Component, OnInit, inject, signal, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { AnnonceService } from '../services/annonce.service';
import { Annonce } from '../models/annonce.model';

@Component({
  selector: 'app-annonce-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './annonce-detail.html',
  styleUrl: './annonce-detail.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AnnonceDetailComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private annonceService = inject(AnnonceService);

  annonce = signal<Annonce | null>(null);
  loading = signal(true);
  error = signal<string | null>(null);

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const annonceId = Number(params.get('id'));
      if (annonceId) {
        this.annonceService.getAnnonceById(annonceId).subscribe({
          next: (data) => {
            this.annonce.set(data);
            this.loading.set(false);
          },
          error: (err) => {
            console.error('Error fetching annonce:', err);
            this.error.set('Failed to load annonce. It might not exist.');
            this.loading.set(false);
          }
        });
      } else {
        this.error.set('Annonce ID not provided in URL.');
        this.loading.set(false);
      }
    });
  }
}