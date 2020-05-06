import { element, by, ElementFinder } from 'protractor';

export class ListingsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-listings div table .btn-danger'));
  title = element.all(by.css('jhi-listings div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class ListingsUpdatePage {
  pageTitle = element(by.id('jhi-listings-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  zpidInput = element(by.id('field_zpid'));
  streetInput = element(by.id('field_street'));
  cityInput = element(by.id('field_city'));
  stateInput = element(by.id('field_state'));
  zipCodeInput = element(by.id('field_zipCode'));
  zestimateInput = element(by.id('field_zestimate'));
  addressInput = element(by.id('field_address'));
  longitudeInput = element(by.id('field_longitude'));
  latitudeInput = element(by.id('field_latitude'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setZpidInput(zpid: string): Promise<void> {
    await this.zpidInput.sendKeys(zpid);
  }

  async getZpidInput(): Promise<string> {
    return await this.zpidInput.getAttribute('value');
  }

  async setStreetInput(street: string): Promise<void> {
    await this.streetInput.sendKeys(street);
  }

  async getStreetInput(): Promise<string> {
    return await this.streetInput.getAttribute('value');
  }

  async setCityInput(city: string): Promise<void> {
    await this.cityInput.sendKeys(city);
  }

  async getCityInput(): Promise<string> {
    return await this.cityInput.getAttribute('value');
  }

  async setStateInput(state: string): Promise<void> {
    await this.stateInput.sendKeys(state);
  }

  async getStateInput(): Promise<string> {
    return await this.stateInput.getAttribute('value');
  }

  async setZipCodeInput(zipCode: string): Promise<void> {
    await this.zipCodeInput.sendKeys(zipCode);
  }

  async getZipCodeInput(): Promise<string> {
    return await this.zipCodeInput.getAttribute('value');
  }

  async setZestimateInput(zestimate: string): Promise<void> {
    await this.zestimateInput.sendKeys(zestimate);
  }

  async getZestimateInput(): Promise<string> {
    return await this.zestimateInput.getAttribute('value');
  }

  async setAddressInput(address: string): Promise<void> {
    await this.addressInput.sendKeys(address);
  }

  async getAddressInput(): Promise<string> {
    return await this.addressInput.getAttribute('value');
  }

  async setLongitudeInput(longitude: string): Promise<void> {
    await this.longitudeInput.sendKeys(longitude);
  }

  async getLongitudeInput(): Promise<string> {
    return await this.longitudeInput.getAttribute('value');
  }

  async setLatitudeInput(latitude: string): Promise<void> {
    await this.latitudeInput.sendKeys(latitude);
  }

  async getLatitudeInput(): Promise<string> {
    return await this.latitudeInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class ListingsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-listings-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-listings'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
