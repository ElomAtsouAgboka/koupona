/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SousmenuComponentsPage, SousmenuDeleteDialog, SousmenuUpdatePage } from './sousmenu.page-object';

const expect = chai.expect;

describe('Sousmenu e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let sousmenuUpdatePage: SousmenuUpdatePage;
    let sousmenuComponentsPage: SousmenuComponentsPage;
    let sousmenuDeleteDialog: SousmenuDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Sousmenus', async () => {
        await navBarPage.goToEntity('sousmenu');
        sousmenuComponentsPage = new SousmenuComponentsPage();
        await browser.wait(ec.visibilityOf(sousmenuComponentsPage.title), 5000);
        expect(await sousmenuComponentsPage.getTitle()).to.eq('kouponaApp.sousmenu.home.title');
    });

    it('should load create Sousmenu page', async () => {
        await sousmenuComponentsPage.clickOnCreateButton();
        sousmenuUpdatePage = new SousmenuUpdatePage();
        expect(await sousmenuUpdatePage.getPageTitle()).to.eq('kouponaApp.sousmenu.home.createOrEditLabel');
        await sousmenuUpdatePage.cancel();
    });

    it('should create and save Sousmenus', async () => {
        const nbButtonsBeforeCreate = await sousmenuComponentsPage.countDeleteButtons();

        await sousmenuComponentsPage.clickOnCreateButton();
        await promise.all([
            sousmenuUpdatePage.setSousMenuItemInput('sousMenuItem'),
            sousmenuUpdatePage.setSousMenuItemImgInput('sousMenuItemImg'),
            sousmenuUpdatePage.menuSelectLastOption()
        ]);
        expect(await sousmenuUpdatePage.getSousMenuItemInput()).to.eq('sousMenuItem');
        expect(await sousmenuUpdatePage.getSousMenuItemImgInput()).to.eq('sousMenuItemImg');
        await sousmenuUpdatePage.save();
        expect(await sousmenuUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await sousmenuComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Sousmenu', async () => {
        const nbButtonsBeforeDelete = await sousmenuComponentsPage.countDeleteButtons();
        await sousmenuComponentsPage.clickOnLastDeleteButton();

        sousmenuDeleteDialog = new SousmenuDeleteDialog();
        expect(await sousmenuDeleteDialog.getDialogTitle()).to.eq('kouponaApp.sousmenu.delete.question');
        await sousmenuDeleteDialog.clickOnConfirmButton();

        expect(await sousmenuComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
