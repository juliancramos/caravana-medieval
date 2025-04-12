import { TestBed } from '@angular/core/testing';

import { ServicePopupService } from './service-popup.service';

describe('ServicePopupService', () => {
  let service: ServicePopupService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServicePopupService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
