import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Departement } from '../models/departement.model';
import { Page } from '../models/page.model';
import { Annonce } from '../models/annonce.model';
import { CreateAnnonce } from '../models/create-annonce.model';

@Injectable({
  providedIn: 'root'
})
export class AnnonceService {
  private apiUrl = 'https://petites-annonces.s2.bkopec.com/api/annonces';
  private http = inject(HttpClient);

  getAllDepartements(): Observable<Departement[]> {
    return this.http.get<Departement[]>(`${this.apiUrl}/departements`);
  }

  getAnnonces(page: number, size: number, departementCode?: string): Observable<Page<Annonce>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    if (departementCode) {
      params = params.set('departementCode', departementCode);
    }

    return this.http.get<Page<Annonce>>(this.apiUrl, { params });
  }

  getAnnonceById(id: number): Observable<Annonce> {
    return this.http.get<Annonce>(`${this.apiUrl}/${id}`);
  }

  createAnnonce(annonce: CreateAnnonce): Observable<Annonce> {
    return this.http.post<Annonce>(this.apiUrl, annonce);
  }
}
