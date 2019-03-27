/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { SouscategorieComponentsPage, SouscategorieDeleteDialog, SouscategorieUpdatePage } from './souscategorie.page-object';

const expect = chai.expect;

describe('Souscategorie e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let souscategorieUpdatePage: SouscategorieUpdatePage;
    let souscategorieComponentsPage: SouscategorieComponentsPage;
    let souscategorieDeleteDialog: SouscategorieDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Souscategories', async () => {
        await navBarPage.goToEntity('souscategorie');
        souscategorieComponentsPage = new SouscategorieComponentsPage();
        await browser.wait(ec.visibilityOf(souscategorieComponentsPage.title), 5000);
        expect(await souscategorieComponentsPage.getTitle()).to.eq('kouponaApp.souscategorie.home.title');
    });

    it('should load create Souscategorie page', async () => {
        await souscategorieComponentsPage.clickOnCreateButton();
        souscategorieUpdatePage = new SouscategorieUpdatePage();
        expect(await souscategorieUpdatePage.getPageTitle()).to.eq('kouponaApp.souscategorie.home.createOrEditLabel');
        await souscategorieUpdatePage.cancel();
    });

    it('should create and save Souscategories', async () => {
        const nbButtonsBeforeCreate = await souscategorieComponentsPage.countDeleteButtons();

        await souscategorieComponentsPage.clickOnCreateButton();
        await promise.all([
            souscategorieUpdatePage.setNomSousCategorieInput('nomSousCategorie'),
            souscategorieUpdatePage.categorieSelectLastOption()
        ]);
        expect(await souscategorieUpdatePage.getNomSousCategorieInput()).to.eq('nomSousCategorie');
        await souscategorieUpdatePage.save();
        expect(await souscategorieUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await souscategorieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Souscategorie', async () => {
        const nbButtonsBeforeDelete = await souscategorieComponentsPage.countDeleteButtons();
        await souscategorieComponentsPage.clickOnLastDeleteButton();

        souscategorieDeleteDialog = new SouscategorieDeleteDialog();
        expect(await souscategorieDeleteDialog.getDialogTitle()).to.eq('kouponaApp.souscategorie.delete.question');
        await souscategorieDeleteDialog.clickOnConfirmButton();

        expect(await souscategorieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
