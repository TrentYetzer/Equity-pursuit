import { browser, ExpectedConditions as ec /* , promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  ScoresComponentsPage,
  /* ScoresDeleteDialog, */
  ScoresUpdatePage
} from './scores.page-object';

const expect = chai.expect;

describe('Scores e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let scoresComponentsPage: ScoresComponentsPage;
  let scoresUpdatePage: ScoresUpdatePage;
  /* let scoresDeleteDialog: ScoresDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Scores', async () => {
    await navBarPage.goToEntity('scores');
    scoresComponentsPage = new ScoresComponentsPage();
    await browser.wait(ec.visibilityOf(scoresComponentsPage.title), 5000);
    expect(await scoresComponentsPage.getTitle()).to.eq('equityPursuitApp.scores.home.title');
    await browser.wait(ec.or(ec.visibilityOf(scoresComponentsPage.entities), ec.visibilityOf(scoresComponentsPage.noResult)), 1000);
  });

  it('should load create Scores page', async () => {
    await scoresComponentsPage.clickOnCreateButton();
    scoresUpdatePage = new ScoresUpdatePage();
    expect(await scoresUpdatePage.getPageTitle()).to.eq('equityPursuitApp.scores.home.createOrEditLabel');
    await scoresUpdatePage.cancel();
  });

  /* it('should create and save Scores', async () => {
        const nbButtonsBeforeCreate = await scoresComponentsPage.countDeleteButtons();

        await scoresComponentsPage.clickOnCreateButton();

        await promise.all([
            scoresUpdatePage.setScoreInput('5'),
            scoresUpdatePage.gamesSelectLastOption(),
        ]);

        expect(await scoresUpdatePage.getScoreInput()).to.eq('5', 'Expected score value to be equals to 5');

        await scoresUpdatePage.save();
        expect(await scoresUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await scoresComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Scores', async () => {
        const nbButtonsBeforeDelete = await scoresComponentsPage.countDeleteButtons();
        await scoresComponentsPage.clickOnLastDeleteButton();

        scoresDeleteDialog = new ScoresDeleteDialog();
        expect(await scoresDeleteDialog.getDialogTitle())
            .to.eq('equityPursuitApp.scores.delete.question');
        await scoresDeleteDialog.clickOnConfirmButton();

        expect(await scoresComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
