import { Component, OnInit, inject, signal, ChangeDetectionStrategy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { AnnonceService } from '../services/annonce.service';
import { CreateAnnonce } from '../models/create-annonce.model';

@Component({
  selector: 'app-create-annonce',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './create-annonce.html',
  styleUrl: './create-annonce.css',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class CreateAnnonceComponent implements OnInit {
  private fb = inject(FormBuilder);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private annonceService = inject(AnnonceService);

  departementCode = signal<string | null>(null);
  departementName = signal<string | null>(null);
  loadingSubmit = signal(false);
  submitError = signal<string | null>(null);

  annonceForm = this.fb.group({
    title: ['', [Validators.required, Validators.maxLength(255)]],
    content: ['', Validators.required],
    posterName: ['', [Validators.required, Validators.maxLength(100)]]
  });

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const code = params.get('code');
      if (code) {
        this.departementCode.set(code);
        this.annonceService.getAllDepartements().subscribe({
          next: (deps) => {
            const foundDep = deps.find(d => d.code === code);
            this.departementName.set(foundDep ? foundDep.depName : null);
          },
          error: (err) => {
            console.error('Error fetching departement info:', err);
            this.departementName.set('Unknown Department');
          }
        });
      } else {
        this.submitError.set('No department code provided. Cannot create annonce.');
      }
    });
  }

  onSubmit(): void {
    if (this.annonceForm.invalid || !this.departementCode()) {
      this.annonceForm.markAllAsTouched();
      this.submitError.set('Please fill in all required fields.');
      return;
    }

    this.loadingSubmit.set(true);
    this.submitError.set(null);

    const newAnnonce: CreateAnnonce = {
      title: this.annonceForm.value.title!,
      content: this.annonceForm.value.content!,
      posterName: this.annonceForm.value.posterName!,
      departementCode: this.departementCode()!
    };

    this.annonceService.createAnnonce(newAnnonce).subscribe({
      next: (response) => {
        console.log('Annonce created:', response);
        this.loadingSubmit.set(false);
        this.router.navigate(['/departement', this.departementCode()]);
      },
      error: (err) => {
        console.error('Error creating annonce:', err);
        this.submitError.set('Failed to create annonce. Please try again.');
        this.loadingSubmit.set(false);
      }
    });
  }
}