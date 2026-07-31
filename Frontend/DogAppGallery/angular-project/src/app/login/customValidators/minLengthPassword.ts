import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export class MinLengthPassword {
  static validate(minLength: number): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (control.value == null || control.value === '') {
        return null;
      }
      const value = String(control.value);
      if (value.length < minLength) {
        return { minLengthPassword: { requiredLength: minLength, actualLength: value.length } };
      }
      return null;
    };
  }
}
