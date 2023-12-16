import { Component, OnInit } from '@angular/core';
import { QuotationService } from './quotation.service';
import { Documents, Quotations } from './quotation.model';

@Component({
  selector: 'app-quotation',
  templateUrl: './quotation.component.html',
  styleUrls: ['./quotation.component.css'],
})
export class QuotationComponent implements OnInit {
  dates: string[] = [];
  codes: string[] = [];
  selectData: string = '';
  selectCode: string = '';

  documents: Documents[] = [];

  constructor(
    private service: QuotationService,
  ) { }

  ngOnInit(): void {
    this.service.getListCurrencyCode().subscribe((code) => {
      this.codes = code;
    });

    this.service.getListDates().subscribe((date) => {
      this.dates = date;
    });
  }

  onGetDocuments(date: string, code: string) {
    this.service.getDocuments(this.formatterData(date), code).subscribe((data: any) => {
      this.documents = this.mapDataToDocuments(data);
    });
  }

  private mapDataToDocuments(data: any[]): Documents[] {
    return data.map((item) => {
      const document = new Documents();
      document.documentNumber = item.documentNumber;
      document.invoice = item.invoice;
      document.docValue = item.documentValue.toString();
      document.quotations = this.mapQuotations(item.quotations);
      return document;
    });
  }

  private mapQuotations(quotationsData: any[]): Quotations[] {
    return quotationsData.map((quotationItem) => {
      const quotation = new Quotations();
      quotation.quotationDate = quotationItem.dataHoraCotacao;
      quotation.currencyCode = quotationItem.toCurrencyCode;
      quotation.currencyDescription = quotationItem.exchangeCurrencyDescription;
      quotation.quotationValue = quotationItem.cotacao.toString();
      quotation.totalExchange = quotationItem.exchangeValue.toString();
      return quotation;
    });
  }

  private formatterData(date: string) {
    const formatData = date.split('/');
    return formatData[2] + '-' + formatData[1] + '-' + formatData[0];
  }

}
