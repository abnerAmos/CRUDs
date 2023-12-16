import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class QuotationService {

  constructor(private http: HttpClient) { }

  getDocuments(date: string, currencyCode: string) {
    let p = new HttpParams().set('date', date).set('currencyCode', currencyCode);
    return this.http.get('http://localhost:8080/samsung/document', { params: p });
  }

  getListDates(): Observable<any> {
    return this.http.get('http://localhost:8080/samsung/date');
  }

  getListCurrencyCode(): Observable<any> {
    return this.http.get('http://localhost:8080/samsung/code')
  }
}
