/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { VilleComponentsPage, VilleDeleteDialog, VilleUpdatePage } from './ville.page-object';

const expect = chai.expect;

describe('Ville e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let villeUpdatePage: VilleUpdatePage;
    let villeComponentsPage: VilleComponentsPage;
    let villeDeleteDialog: VilleDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Villes', async () => {
        await navBarPage.goToEntity('ville');
        villeComponentsPage = new VilleComponentsPage();
        await browser.wait(ec.visibilityOf(villeComponentsPage.title), 5000);
        expect(await villeComponentsPage.getTitle()).to.eq('kouponaApp.ville.home.title');
    });

    it('should load create Ville page', async () => {
        await villeComponentsPage.clickOnCreateButton();
        villeUpdatePage = new VilleUpdatePage();
        expect(await villeUpdatePage.getPageTitle()).to.eq('kouponaApp.ville.home.createOrEditLabel');
        await villeUpdatePage.cancel();
    });

    it('should create and save Villes', async () => {
        const nbButtonsBeforeCreate = await villeComponentsPage.countDeleteButtons();

        await villeComponentsPage.clickOnCreateButton();
        await promise.all([villeUpdatePage.setNomVilleInput('nomVille'), villeUpdatePage.paysSelectLastOption()]);
        expect(await villeUpdatePage.getNomVilleInput()).to.eq('nomVille');
        await villeUpdatePage.save();
        expect(await villeUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await villeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Ville', async () => {
        const nbButtonsBeforeDelete = await villeComponentsPage.countDeleteButtons();
        await villeComponentsPage.clickOnLastDeleteButton();

        villeDeleteDialog = new VilleDeleteDialog();
        expect(await villeDeleteDialog.getDialogTitle()).to.eq('kouponaApp.ville.delete.question');
        await villeDeleteDialog.clickOnConfirmButton();

        expect(await villeComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
