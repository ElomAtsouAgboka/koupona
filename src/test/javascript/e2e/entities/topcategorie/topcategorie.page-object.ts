import { element, by, ElementFinder } from 'protractor';

export class TopcategorieComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-topcategorie div table .btn-danger'));
    title = element.all(by.css('jhi-topcategorie div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class TopcategorieUpdatePage {
    pageTitle = element(by.id('jhi-topcategorie-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    nomTopCategorieInput = element(by.id('field_nomTopCategorie'));

    async getPageTitle() {
        return this.pageTitle.getAttribute('jhiTranslate');
    }

    async setNomTopCategorieInput(nomTopCategorie) {
        await this.nomTopCategorieInput.sendKeys(nomTopCategorie);
    }

    async getNomTopCategorieInput() {
        return this.nomTopCategorieInput.getAttribute('value');
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class TopcategorieDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-topcategorie-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-topcategorie'));

    async getDialogTitle() {
        return this.dialogTitle.getAttribute('jhiTranslate');
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
