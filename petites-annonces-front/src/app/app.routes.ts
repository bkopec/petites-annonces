import { Routes } from '@angular/router';
import { HomeComponent } from './home/home';
import { AnnoncesDepartementComponent } from './annonces-departement/annonces-departement';
import { CreateAnnonceComponent } from './create-annonce/create-annonce';
import { AnnonceDetailComponent } from './annonce-detail/annonce-detail';

export const routes: Routes = [
  { path: '', component: HomeComponent, title: 'Petites Annonces - Departements' },
  { path: 'departement/:code', component: AnnoncesDepartementComponent, title: 'Annonces by Departement' },
  { path: 'departement/:code/create-annonce', component: CreateAnnonceComponent, title: 'Create Annonce' },
  { path: 'annonce/:id', component: AnnonceDetailComponent, title: 'Annonce Details' },
  { path: '**', redirectTo: '', pathMatch: 'full' } // Redirect any unknown paths to home
];