import { TestBed } from '@angular/core/testing';

import { MealDataService } from './meal-data.service';

describe('MealDataService', () => {
  let service: MealDataService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MealDataService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
