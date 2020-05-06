import { browser, ExpectedConditions as ec /* , protractor, promise */ } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  GamesComponentsPage,
  /* GamesDeleteDialog, */
  GamesUpdatePage
} from './games.page-object';

const expect = chai.expect;

describe('Games e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let gamesComponentsPage: GamesComponentsPage;
  let gamesUpdatePage: GamesUpdatePage;
  /* let gamesDeleteDialog: GamesDeleteDialog; */

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Games', async () => {
    await navBarPage.goToEntity('games');
    gamesComponentsPage = new GamesComponentsPage();
    await browser.wait(ec.visibilityOf(gamesComponentsPage.title), 5000);
    expect(await gamesComponentsPage.getTitle()).to.eq('equityPursuitApp.games.home.title');
    await browser.wait(ec.or(ec.visibilityOf(gamesComponentsPage.entities), ec.visibilityOf(gamesComponentsPage.noResult)), 1000);
  });

  it('should load create Games page', async () => {
    await gamesComponentsPage.clickOnCreateButton();
    gamesUpdatePage = new GamesUpdatePage();
    expect(await gamesUpdatePage.getPageTitle()).to.eq('equityPursuitApp.games.home.createOrEditLabel');
    await gamesUpdatePage.cancel();
  });

  /* it('should create and save Games', async () => {
        const nbButtonsBeforeCreate = await gamesComponentsPage.countDeleteButtons();

        await gamesComponentsPage.clickOnCreateButton();

        await promise.all([
            gamesUpdatePage.setPlaytimeInput('playtime'),
            gamesUpdatePage.setListingListInput('listingList'),
            gamesUpdatePage.setScoreInput('5'),
            gamesUpdatePage.setTimeInput('01/01/2001' + protractor.Key.TAB + '02:30AM'),
            gamesUpdatePage.userSelectLastOption(),
        ]);

        expect(await gamesUpdatePage.getPlaytimeInput()).to.eq('playtime', 'Expected Playtime value to be equals to playtime');
        expect(await gamesUpdatePage.getListingListInput()).to.eq('listingList', 'Expected ListingList value to be equals to listingList');
        expect(await gamesUpdatePage.getScoreInput()).to.eq('5', 'Expected score value to be equals to 5');
        expect(await gamesUpdatePage.getTimeInput()).to.contain('2001-01-01T02:30', 'Expected time value to be equals to 2000-12-31');

        await gamesUpdatePage.save();
        expect(await gamesUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

        expect(await gamesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
    }); */

  /* it('should delete last Games', async () => {
        const nbButtonsBeforeDelete = await gamesComponentsPage.countDeleteButtons();
        await gamesComponentsPage.clickOnLastDeleteButton();

        gamesDeleteDialog = new GamesDeleteDialog();
        expect(await gamesDeleteDialog.getDialogTitle())
            .to.eq('equityPursuitApp.games.delete.question');
        await gamesDeleteDialog.clickOnConfirmButton();

        expect(await gamesComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
