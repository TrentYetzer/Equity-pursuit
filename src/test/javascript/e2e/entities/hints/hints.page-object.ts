import { element, by, ElementFinder } from 'protractor';

export class HintsComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-hints div table .btn-danger'));
  title = element.all(by.css('jhi-hints div h2#page-heading span')).first();
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

export class HintsUpdatePage {
  pageTitle = element(by.id('jhi-hints-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  hintInput = element(by.id('field_hint'));
  modifierInput = element(by.id('field_modifier'));

  listingsSelect = element(by.id('field_listings'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setHintInput(hint: string): Promise<void> {
    await this.hintInput.sendKeys(hint);
  }

  async getHintInput(): Promise<string> {
    return await this.hintInput.getAttribute('value');
  }

  async setModifierInput(modifier: string): Promise<void> {
    await this.modifierInput.sendKeys(modifier);
  }

  async getModifierInput(): Promise<string> {
    return await this.modifierInput.getAttribute('value');
  }

  async listingsSelectLastOption(): Promise<void> {
    await this.listingsSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async listingsSelectOption(option: string): Promise<void> {
    await this.listingsSelect.sendKeys(option);
  }

  getListingsSelect(): ElementFinder {
    return this.listingsSelect;
  }

  async getListingsSelectedOption(): Promise<string> {
    return await this.listingsSelect.element(by.css('option:checked')).getText();
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

export class HintsDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-hints-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-hints'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
