import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ListingsComponentsPage, ListingsDeleteDialog, ListingsUpdatePage } from './listings.page-object';

const expect = chai.expect;

describe('Listings e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let listingsComponentsPage: ListingsComponentsPage;
  let listingsUpdatePage: ListingsUpdatePage;
  let listingsDeleteDialog: ListingsDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Listings', async () => {
    await navBarPage.goToEntity('listings');
    listingsComponentsPage = new ListingsComponentsPage();
    await browser.wait(ec.visibilityOf(listingsComponentsPage.title), 5000);
    expect(await listingsComponentsPage.getTitle()).to.eq('equityPursuitApp.listings.home.title');
    await browser.wait(ec.or(ec.visibilityOf(listingsComponentsPage.entities), ec.visibilityOf(listingsComponentsPage.noResult)), 1000);
  });

  it('should load create Listings page', async () => {
    await listingsComponentsPage.clickOnCreateButton();
    listingsUpdatePage = new ListingsUpdatePage();
    expect(await listingsUpdatePage.getPageTitle()).to.eq('equityPursuitApp.listings.home.createOrEditLabel');
    await listingsUpdatePage.cancel();
  });

  it('should create and save Listings', async () => {
    const nbButtonsBeforeCreate = await listingsComponentsPage.countDeleteButtons();

    await listingsComponentsPage.clickOnCreateButton();

    await promise.all([
      listingsUpdatePage.setZpidInput('5'),
      listingsUpdatePage.setStreetInput('street'),
      listingsUpdatePage.setCityInput('city'),
      listingsUpdatePage.setStateInput('state'),
      listingsUpdatePage.setZipCodeInput('5'),
      listingsUpdatePage.setZestimateInput('5'),
      listingsUpdatePage.setAddressInput('5'),
      listingsUpdatePage.setLongitudeInput('5'),
      listingsUpdatePage.setLatitudeInput('5')
    ]);

    expect(await listingsUpdatePage.getZpidInput()).to.eq('5', 'Expected zpid value to be equals to 5');
    expect(await listingsUpdatePage.getStreetInput()).to.eq('street', 'Expected Street value to be equals to street');
    expect(await listingsUpdatePage.getCityInput()).to.eq('city', 'Expected City value to be equals to city');
    expect(await listingsUpdatePage.getStateInput()).to.eq('state', 'Expected State value to be equals to state');
    expect(await listingsUpdatePage.getZipCodeInput()).to.eq('5', 'Expected zipCode value to be equals to 5');
    expect(await listingsUpdatePage.getZestimateInput()).to.eq('5', 'Expected zestimate value to be equals to 5');
    expect(await listingsUpdatePage.getAddressInput()).to.eq('5', 'Expected address value to be equals to 5');
    expect(await listingsUpdatePage.getLongitudeInput()).to.eq('5', 'Expected longitude value to be equals to 5');
    expect(await listingsUpdatePage.getLatitudeInput()).to.eq('5', 'Expected latitude value to be equals to 5');

    await listingsUpdatePage.save();
    expect(await listingsUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await listingsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Listings', async () => {
    const nbButtonsBeforeDelete = await listingsComponentsPage.countDeleteButtons();
    await listingsComponentsPage.clickOnLastDeleteButton();

    listingsDeleteDialog = new ListingsDeleteDialog();
    expect(await listingsDeleteDialog.getDialogTitle()).to.eq('equityPursuitApp.listings.delete.question');
    await listingsDeleteDialog.clickOnConfirmButton();

    expect(await listingsComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
