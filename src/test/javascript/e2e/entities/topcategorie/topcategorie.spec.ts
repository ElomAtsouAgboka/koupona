/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TopcategorieComponentsPage, TopcategorieDeleteDialog, TopcategorieUpdatePage } from './topcategorie.page-object';

const expect = chai.expect;

describe('Topcategorie e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let topcategorieUpdatePage: TopcategorieUpdatePage;
    let topcategorieComponentsPage: TopcategorieComponentsPage;
    let topcategorieDeleteDialog: TopcategorieDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Topcategories', async () => {
        await navBarPage.goToEntity('topcategorie');
        topcategorieComponentsPage = new TopcategorieComponentsPage();
        await browser.wait(ec.visibilityOf(topcategorieComponentsPage.title), 5000);
        expect(await topcategorieComponentsPage.getTitle()).to.eq('kouponaApp.topcategorie.home.title');
    });

    it('should load create Topcategorie page', async () => {
        await topcategorieComponentsPage.clickOnCreateButton();
        topcategorieUpdatePage = new TopcategorieUpdatePage();
        expect(await topcategorieUpdatePage.getPageTitle()).to.eq('kouponaApp.topcategorie.home.createOrEditLabel');
        await topcategorieUpdatePage.cancel();
    });

    it('should create and save Topcategories', async () => {
        const nbButtonsBeforeCreate = await topcategorieComponentsPage.countDeleteButtons();

        await topcategorieComponentsPage.clickOnCreateButton();
        await promise.all([topcategorieUpdatePage.setNomTopCategorieInput('nomTopCategorie')]);
        expect(await topcategorieUpdatePage.getNomTopCategorieInput()).to.eq('nomTopCategorie');
        await topcategorieUpdatePage.save();
        expect(await topcategorieUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await topcategorieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Topcategorie', async () => {
        const nbButtonsBeforeDelete = await topcategorieComponentsPage.countDeleteButtons();
        await topcategorieComponentsPage.clickOnLastDeleteButton();

        topcategorieDeleteDialog = new TopcategorieDeleteDialog();
        expect(await topcategorieDeleteDialog.getDialogTitle()).to.eq('kouponaApp.topcategorie.delete.question');
        await topcategorieDeleteDialog.clickOnConfirmButton();

        expect(await topcategorieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
