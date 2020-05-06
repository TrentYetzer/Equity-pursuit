import { element, by, ElementFinder } from 'protractor';

export class GamesComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-games div table .btn-danger'));
  title = element.all(by.css('jhi-games div h2#page-heading span')).first();
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

export class GamesUpdatePage {
  pageTitle = element(by.id('jhi-games-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  playtimeInput = element(by.id('field_playtime'));
  listingListInput = element(by.id('field_listingList'));
  scoreInput = element(by.id('field_score'));
  timeInput = element(by.id('field_time'));

  userSelect = element(by.id('field_user'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setPlaytimeInput(playtime: string): Promise<void> {
    await this.playtimeInput.sendKeys(playtime);
  }

  async getPlaytimeInput(): Promise<string> {
    return await this.playtimeInput.getAttribute('value');
  }

  async setListingListInput(listingList: string): Promise<void> {
    await this.listingListInput.sendKeys(listingList);
  }

  async getListingListInput(): Promise<string> {
    return await this.listingListInput.getAttribute('value');
  }

  async setScoreInput(score: string): Promise<void> {
    await this.scoreInput.sendKeys(score);
  }

  async getScoreInput(): Promise<string> {
    return await this.scoreInput.getAttribute('value');
  }

  async setTimeInput(time: string): Promise<void> {
    await this.timeInput.sendKeys(time);
  }

  async getTimeInput(): Promise<string> {
    return await this.timeInput.getAttribute('value');
  }

  async userSelectLastOption(): Promise<void> {
    await this.userSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async userSelectOption(option: string): Promise<void> {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect(): ElementFinder {
    return this.userSelect;
  }

  async getUserSelectedOption(): Promise<string> {
    return await this.userSelect.element(by.css('option:checked')).getText();
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

export class GamesDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-games-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-games'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
