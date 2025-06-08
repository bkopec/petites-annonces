import { Component, OnInit, inject, signal, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink, Router } from '@angular/router';
import { AnnonceService } from '../services/annonce.service';
import { Annonce } from '../models/annonce.model';
import { Page } from '../models/page.model';
import { Departement } from '../models/departement.model';

@Component({
  selector: 'app-annonces-departement',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './annonces-departement.html',
  styleUrl: './annonces-departement.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AnnoncesDepartementComponent implements OnInit {
  private route = inject(ActivatedRoute);
  private annonceService = inject(AnnonceService);
  private router = inject(Router);

  departementCode = signal<string | null>(null);
  departementInfo = signal<Departement | null>(null);
  annoncesPage = signal<Page<Annonce>>({
    content: [],
    pageable: { pageNumber: 0, pageSize: 0, sort: { empty: true, sorted: false, unsorted: true }, offset: 0, paged: false, unpaged: true },
    last: true, totalPages: 0, totalElements: 0, size: 0, number: 0, sort: { empty: true, sorted: false, unsorted: true }, first: true, numberOfElements: 0, empty: true
  });
  loadingAnnonces = signal(true);
  error = signal<string | null>(null);

  currentPage = signal(0);
  pageSize = signal(10); // Default page size

  ngOnInit(): void {
    // Get departement code from route parameters
    this.route.paramMap.subscribe(params => {
      const code = params.get('code');
      if (code) {
        this.departementCode.set(code);
        this.loadDepartementInfo(code); // Load info for header display
        this.loadAnnonces(this.currentPage(), this.pageSize(), code);
      } else {
        this.error.set('Department code not provided in URL.');
        this.loadingAnnonces.set(false);
      }
    });
  }

  loadDepartementInfo(code: string): void {
    this.annonceService.getAllDepartements().subscribe({
      next: (deps) => {
        const foundDep = deps.find(d => d.code === code);
        this.departementInfo.set(foundDep || null);
      },
      error: (err) => {
        console.error('Error fetching departement info:', err);
        // This error might not block Annonces loading, just means info is missing
      }
    });
  }

  loadAnnonces(page: number, size: number, departementCode: string): void {
    this.loadingAnnonces.set(true);
    this.error.set(null);
    this.annonceService.getAnnonces(page, size, departementCode).subscribe({
      next: (data) => {
        this.annoncesPage.set(data);
        this.currentPage.set(data.number); // Update current page based on response
        this.loadingAnnonces.set(false);
      },
      error: (err) => {
        console.error('Error fetching annonces:', err);
        this.error.set('Failed to load annonces for this department.');
        this.loadingAnnonces.set(false);
      }
    });
  }

  goToPage(page: number): void {
    if (this.departementCode()) {
      this.loadAnnonces(page, this.pageSize(), this.departementCode()!);
    }
  }

  goBack(): void {
    this.router.navigate(['/']); 
  }
}