/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { QuartierComponentsPage, QuartierDeleteDialog, QuartierUpdatePage } from './quartier.page-object';

const expect = chai.expect;

describe('Quartier e2e test', () => {
    let navBarPage: NavBarPage;
    let signInPage: SignInPage;
    let quartierUpdatePage: QuartierUpdatePage;
    let quartierComponentsPage: QuartierComponentsPage;
    let quartierDeleteDialog: QuartierDeleteDialog;

    before(async () => {
        await browser.get('/');
        navBarPage = new NavBarPage();
        signInPage = await navBarPage.getSignInPage();
        await signInPage.autoSignInUsing('admin', 'admin');
        await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
    });

    it('should load Quartiers', async () => {
        await navBarPage.goToEntity('quartier');
        quartierComponentsPage = new QuartierComponentsPage();
        await browser.wait(ec.visibilityOf(quartierComponentsPage.title), 5000);
        expect(await quartierComponentsPage.getTitle()).to.eq('kouponaApp.quartier.home.title');
    });

    it('should load create Quartier page', async () => {
        await quartierComponentsPage.clickOnCreateButton();
        quartierUpdatePage = new QuartierUpdatePage();
        expect(await quartierUpdatePage.getPageTitle()).to.eq('kouponaApp.quartier.home.createOrEditLabel');
        await quartierUpdatePage.cancel();
    });

    it('should create and save Quartiers', async () => {
        const nbButtonsBeforeCreate = await quartierComponentsPage.countDeleteButtons();

        await quartierComponentsPage.clickOnCreateButton();
        await promise.all([quartierUpdatePage.setNomQuartierInput('nomQuartier'), quartierUpdatePage.villeSelectLastOption()]);
        expect(await quartierUpdatePage.getNomQuartierInput()).to.eq('nomQuartier');
        await quartierUpdatePage.save();
        expect(await quartierUpdatePage.getSaveButton().isPresent()).to.be.false;

        expect(await quartierComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1);
    });

    it('should delete last Quartier', async () => {
        const nbButtonsBeforeDelete = await quartierComponentsPage.countDeleteButtons();
        await quartierComponentsPage.clickOnLastDeleteButton();

        quartierDeleteDialog = new QuartierDeleteDialog();
        expect(await quartierDeleteDialog.getDialogTitle()).to.eq('kouponaApp.quartier.delete.question');
        await quartierDeleteDialog.clickOnConfirmButton();

        expect(await quartierComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
    });

    after(async () => {
        await navBarPage.autoSignOut();
    });
});
