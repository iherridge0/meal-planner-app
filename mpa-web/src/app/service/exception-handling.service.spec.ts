import { TestBed } from '@angular/core/testing';

import { ExceptionHandlingService } from './exception-handling.service';

describe('ExceptionServiceService', () => {
  let service: ExceptionHandlingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ExceptionHandlingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
