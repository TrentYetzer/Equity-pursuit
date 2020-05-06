import { element, by, ElementFinder } from 'protractor';

export class ScoresComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-scores div table .btn-danger'));
  title = element.all(by.css('jhi-scores div h2#page-heading span')).first();
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

export class ScoresUpdatePage {
  pageTitle = element(by.id('jhi-scores-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  scoreInput = element(by.id('field_score'));

  gamesSelect = element(by.id('field_games'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setScoreInput(score: string): Promise<void> {
    await this.scoreInput.sendKeys(score);
  }

  async getScoreInput(): Promise<string> {
    return await this.scoreInput.getAttribute('value');
  }

  async gamesSelectLastOption(): Promise<void> {
    await this.gamesSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async gamesSelectOption(option: string): Promise<void> {
    await this.gamesSelect.sendKeys(option);
  }

  getGamesSelect(): ElementFinder {
    return this.gamesSelect;
  }

  async getGamesSelectedOption(): Promise<string> {
    return await this.gamesSelect.element(by.css('option:checked')).getText();
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

export class ScoresDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-scores-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-scores'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
