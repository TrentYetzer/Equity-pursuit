import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  HintsComponentsPage,
  /* HintsDeleteDialog, */
  HintsUpdatePage
} from './hints.page-object';

const expect = chai.expect;

describe('Hints e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let hintsComponentsPage: HintsComponentsPage;
  let hintsUpdatePage: HintsUpdatePage;
  /* let hintsDeleteDialog: HintsDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Hints', async () => {
    await navBarPage.goToEntity('hints');
    hintsComponentsPage = new HintsComponentsPage();
    await browser.wait(ec.visibilityOf(hintsComponentsPage.title), 5000);
    expect(await hintsComponentsPage.getTitle()).to.eq('equityPursuitApp.hints.home.title');
    await browser.wait(ec.or(ec.visibilityOf(hintsComponentsPage.entities), ec.visibilityOf(hintsComponentsPage.noResult)), 1000);
  });

  it('should load create Hints page', async () => {
    await hintsComponentsPage.clickOnCreateButton();
    hintsUpdatePage = new HintsUpdatePage();
    expect(await hintsUpdatePage.getPageTitle()).to.eq('equityPursuitApp.hints.home.createOrEditLabel');
    await hintsUpdatePage.cancel();
  });

  /* it('should create and save Hints', async () => {
        const nbButtonsBeforeCreate = await hintsComponentsPage.countDeleteButtons();

        await hintsComponentsPage.clickOnCreateButton();

        await promise.all([
            hintsUpdatePage.setHintInput('hint'),
            hintsUpdatePage.setModifierInput('5'),
            hintsUpdatePage.listingsSelectLastOption(),
        ]);

        expect(await hintsUpdatePage.getHintInput()).to.eq('hint', 'Expected Hint value to be equals to hint');
        expect(await hintsUpdatePage.getModifierInput()).to.eq('5', 'Expected modifier value to be equals to 5');

        await hintsUpdatePage.save();
        expect(await hintsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await hintsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Hints', async () => {
        const nbButtonsBeforeDelete = await hintsComponentsPage.countDeleteButtons();
        await hintsComponentsPage.clickOnLastDeleteButton();

        hintsDeleteDialog = new HintsDeleteDialog();
        expect(await hintsDeleteDialog.getDialogTitle())
            .to.eq('equityPursuitApp.hints.delete.question');
        await hintsDeleteDialog.clickOnConfirmButton();

        expect(await hintsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
