import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AnnoncesDepartement } from './annonces-departement';

describe('AnnoncesDepartement', () => {
  let component: AnnoncesDepartement;
  let fixture: ComponentFixture<AnnoncesDepartement>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AnnoncesDepartement]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AnnoncesDepartement);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
