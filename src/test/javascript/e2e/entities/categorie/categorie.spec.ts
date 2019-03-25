/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CategorieComponentsPage, CategorieDeleteDialog, CategorieUpdatePage } from './categorie.page-object';

const expect = chai.expect;

describe('Categorie e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let categorieUpdatePage: CategorieUpdatePage;
    let categorieComponentsPage: CategorieComponentsPage;
    let categorieDeleteDialog: CategorieDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Categories', async () => {
        await navBarPage.goToEntity('categorie');
        categorieComponentsPage = new CategorieComponentsPage();
        await browser.wait(ec.visibilityOf(categorieComponentsPage.title), 5000);
        expect(await categorieComponentsPage.getTitle()).to.eq('kouponaApp.categorie.home.title');
    });

    it('should load create Categorie page', async () => {
        await categorieComponentsPage.clickOnCreateButton();
        categorieUpdatePage = new CategorieUpdatePage();
        expect(await categorieUpdatePage.getPageTitle()).to.eq('kouponaApp.categorie.home.createOrEditLabel');
        await categorieUpdatePage.cancel();
    });

    it('should create and save Categories', async () => {
        const nbButtonsBeforeCreate = await categorieComponentsPage.countDeleteButtons();

        await categorieComponentsPage.clickOnCreateButton();
        await promise.all([categorieUpdatePage.setNomCategorieInput('nomCategorie'), categorieUpdatePage.topcategorieSelectLastOption()]);
        expect(await categorieUpdatePage.getNomCategorieInput()).to.eq('nomCategorie');
        await categorieUpdatePage.save();
        expect(await categorieUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await categorieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Categorie', async () => {
        const nbButtonsBeforeDelete = await categorieComponentsPage.countDeleteButtons();
        await categorieComponentsPage.clickOnLastDeleteButton();

        categorieDeleteDialog = new CategorieDeleteDialog();
        expect(await categorieDeleteDialog.getDialogTitle()).to.eq('kouponaApp.categorie.delete.question');
        await categorieDeleteDialog.clickOnConfirmButton();

        expect(await categorieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
