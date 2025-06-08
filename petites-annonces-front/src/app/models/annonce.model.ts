export interface Annonce {
    id: number;
    title: string;
    content: string;
    posterName: string;
    departementCode: string;
    departementName: string; // To easily display the name
    createdAt: string; // Use string for date from backend, format in template
  }