export class Documents {
  documentNumber?: string;
  invoice?: string;
  docValue?: string;
  quotations?: Quotations[];
}

export class Quotations {
  quotationDate?: string;
  currencyCode?: string;
  currencyDescription?: string;
  quotationValue?: string;
  totalExchange?: string;
}
